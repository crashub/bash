package org.crashub.bash.spi;

import org.crashub.bash.ir.Node;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A base context implementation that uses {@link java.io.InputStream} and {@link java.io.OutputStream} streams. Should
 * be used for implementing runtimes based on <code>java.io</code> package.
 *
 * @author Julien Viet
 */
public class BaseContext extends Context {

  /**
   * Provides command plugability.
   */
  public interface CommandResolver {

    /**
     * Resolve a command, return null if no command can be resolved.
     *
     * @param command the command name
     * @return a command invoking the command or null if the command could not be resolved
     */
    Command resolveCommand(String command);

  }

  /**
   * A command.
   */
  public interface Command {

    /**
     * Execute the command.
     *
     * @param context the context of execution
     * @param bindings the current bindings
     * @param parameters the command parameters
     * @param standardInput the standard input
     * @param standardOutput the standard output
     */
    Object execute(BaseContext context, Scope bindings, List<String> parameters, InputStream standardInput, OutputStream standardOutput);

  }

  private final CommandResolver commandResolver;
  private final Map<String, Function> functions;
  private final InputStream standardInput;
  private final OutputStream standardOutput;
  private final Scope bindingScope;

  public BaseContext(CommandResolver commandResolver, OutputStream standardOutput) {
    this(commandResolver, new ByteArrayInputStream(new byte[0]), standardOutput);
  }

  public BaseContext(CommandResolver commandResolver, InputStream standardInput, OutputStream standardOutput) {
    this(commandResolver, standardInput, standardOutput, new SimpleScope());
  }

  public BaseContext(CommandResolver commandResolver, InputStream standardInput, OutputStream standardOutput, Scope bindingScope) {
    this.commandResolver = commandResolver;
    this.standardInput = standardInput;
    this.standardOutput = standardOutput;
    this.bindingScope = bindingScope;
    this.functions = new HashMap<String, Function>();
  }

  @Override
  public void setFunction(String name, Function function) {
    if (function != null) {
      functions.put(name, function);
    } else {
      functions.remove(name);
    }
  }

  @Override
  public Function resolveFunction(final String name) {
    Function function = functions.get(name);
    if (function == null) {
      final Command command = commandResolver.resolveCommand(name);
      if (command == null) {
        throw new UnsupportedOperationException(name + ": command not found");
      } else {
        return new Function() {
          @Override
          public Node bind(final List<String> parameters) {
            return new Node() {
              @Override
              public Object eval(Scope bindings, Context context) {
                BaseContext nested = (BaseContext)context;
                return command.execute(nested, bindings, parameters, nested.standardInput, nested.standardOutput);
              }
            };
          }
        };
      }
    }
    return function;
  }

  @Override
  public final Object execute(Scope bindings, Node[] pipeline) {
    Object last = null;
    InputStream in = standardInput;
    for (int i = 0;i < pipeline.length;i++) {
      Node process = pipeline[i];
      if (i == pipeline.length - 1) {
        last = process.eval(bindings, new BaseContext(commandResolver, in, standardOutput, bindingScope));
        try {
          standardOutput.flush();
        }
        catch (IOException e) {
          throw new UndeclaredThrowableException(e, "Handle me gracefully");
        }
      } else {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        last = process.eval(bindings, new BaseContext(commandResolver, in, buffer, bindingScope));
        try {
          buffer.close();
        }
        catch (IOException e) {
          throw new UndeclaredThrowableException(e, "Handle me gracefully");
        }
        in = new ByteArrayInputStream(buffer.toByteArray());
      }
    }
    return last;
  }

  private static class Value {
    Object o;
    boolean readOnly = false;
    boolean integer = false;
  }

  @Override
  public Object getValue(Scope scope, String name) {
    Value value = (Value)scope.getValue(name);
    if (value != null) {
      Object ret = value.o;
      if (ret == null) {
        if (value.integer || value.readOnly) {
          return "";
        } else {
          throw new AssertionError("Not covered/expected");
        }
      } else {
        return ret;
      }
    } else {
      return null;
    }
  }

  @Override
  public void setValue(Scope scope, String name, Object value) {
    Value wrapper = (Value)scope.getValue(name);
    if (wrapper == null) {
      scope.setValue(name, wrapper = new Value());
    }
    if (wrapper.readOnly) {
      throw new RuntimeException(name + ": readonly variable");
    } else {
      if (value == null) {
        if (!wrapper.integer) {
          throw new AssertionError("Not covered/expected");
        }
      }
      if (wrapper.integer) {
        value = toInteger(value);
      }
      wrapper.o = value;
    }
  }

  /**
   * Set a binding as read only.
   *
   * @param bindings  the bindings
   * @param name the name
   * @param value the value
   */
  public void setReadOnly(Scope bindings, String name, Object value) {
    Value wrapper = (Value)bindings.getValue(name);
    if (wrapper == null) {
      bindings.setValue(name, wrapper = new Value());
    } else {
      if (wrapper.readOnly) {
        return;
      }
    }
    if (value != null) {
      wrapper.o = value;
    }
    wrapper.readOnly = true;
  }

  /**
   * Set a binding as integer.
   *
   * @param bindings the bindings
   * @param name the name
   * @param value the optional value
   */
  public void setInteger(Scope bindings, String name, Object value) {
    Value wrapper = (Value)bindings.getValue(name);
    if (wrapper == null) {
      bindings.setValue(name, wrapper = new Value());
    } else {
      if (wrapper.readOnly) {
        return;
      }
    }
    if (value != null) {
      wrapper.o = toInteger(value);
    }
    wrapper.integer = true;
  }

  private static Integer toInteger(Object o) {
    if (o instanceof Number) {
      return ((Number)o).intValue();
    } else {
      try {
        return Integer.parseInt(o.toString());
      }
      catch (NumberFormatException e) {
        return 0;
      }
    }
  }
}
