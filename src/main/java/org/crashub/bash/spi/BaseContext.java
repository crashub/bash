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
import java.util.concurrent.Callable;

/**
 * A base context implementation that uses {@link java.io.InputStream} and {@link java.io.OutputStream} streams. Should
 * be used for implementing runtimes based on <code>java.io</code> package.
 *
 * @author Julien Viet
 */
public class BaseContext implements Context {

  /**
   * Provides command plugability.
   */
  public interface CommandResolver {

    /**
     * Resolve a command implementation.
     *
     * @param command the command name
     * @param parameters the command parameters
     * @param standardInput the standard input
     * @param standardOutput the standard output
     * @return a callable invoking the command or null if the command could not be resolved
     */
    Callable<?> resolveCommand(String command, List<String> parameters, InputStream standardInput, OutputStream standardOutput);

  }

  /** . */
  final CommandResolver commandResolver;

  /** . */
  final Map<String, Object> bindings;

  /** . */
  protected final InputStream standardInput;

  /** . */
  protected final OutputStream standardOutput;

  public BaseContext(CommandResolver commandResolver, OutputStream standardOutput) {
    this(commandResolver, new ByteArrayInputStream(new byte[0]), standardOutput);
  }

  public BaseContext(CommandResolver commandResolver, InputStream standardInput, OutputStream standardOutput) {
    this(commandResolver, standardInput, standardOutput, new HashMap<String, Object>());
  }

  public BaseContext(CommandResolver commandResolver, InputStream standardInput, OutputStream standardOutput, Map<String, Object> bindings) {
    this.commandResolver = commandResolver;
    this.standardInput = standardInput;
    this.standardOutput = standardOutput;
    this.bindings = bindings;
  }

  // Fluent method
  public BaseContext bind(String name, Object value) {
    setBinding(name, value);
    return this;
  }

  public Object getBinding(String name) {
    return bindings.get(name);
  }

  public void setBinding(String name, Object value) {
    if (value != null) {
      bindings.put(name, value);
    } else {
      bindings.remove(name);
    }
  }


  @Override
  public final Node createCommand(final String command, final List<String> parameters) {
    return new Node() {
      @Override
      public Object eval(Context context) {
        BaseContext nested = (BaseContext)context;
        Callable<?> impl = commandResolver.resolveCommand(
            command,
            parameters,
            nested.standardInput,
            nested.standardOutput);
        if (impl == null) {
          throw new UnsupportedOperationException("Command " + command + " not implemented");
        }
        try {
          return impl.call();
        }
        catch (Exception e) {
          // Should be done better later when we take care of exception handling
          throw new UndeclaredThrowableException(e);
        }
      }
    };
  }

  @Override
  public final Object execute(Node[] pipeline) {
    Object last = null;
    InputStream in = standardInput;
    for (int i = 0;i < pipeline.length;i++) {
      Node process = pipeline[i];
      if (i == pipeline.length - 1) {
        last = process.eval(new BaseContext(commandResolver, in, standardOutput, bindings));
        try {
          standardOutput.flush();
        }
        catch (IOException e) {
          throw new UndeclaredThrowableException(e, "Handle me gracefully");
        }
      } else {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        last = process.eval(new BaseContext(commandResolver, in, buffer, bindings));
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

  @Override
  public String toString() {
    return "bindings: " + bindings;
  }
}
