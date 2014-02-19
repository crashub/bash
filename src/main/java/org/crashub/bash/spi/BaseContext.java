package org.crashub.bash.spi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Julien Viet
 */
public class BaseContext implements Context {

  /** . */
  final HashMap<String, Object> bindings = new HashMap<String, Object>();

  /** . */
  protected final OutputStream out;

  public BaseContext(OutputStream out) {
    this.out = out;
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

  protected Callable<?> createCommand(
      String command,
      List<String> parameters,
      InputStream in,
      OutputStream out) {
    return null;
  }

  @Override
  public final org.crashub.bash.spi.Process createCommand(final String command, final List<String> parameters) {
    return new Process() {
      @Override
      public Object execute(Context context) {
        NestedContext nested = (NestedContext)context;
        Callable<?> impl = createCommand(
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

  static class NestedContext implements  Context {

    final Context parent;
    final InputStream standardInput;
    final OutputStream standardOutput;

    NestedContext(Context parent, InputStream standardInput, OutputStream standardOutput) {
      this.parent = parent;
      this.standardInput = standardInput;
      this.standardOutput = standardOutput;
    }

    @Override
    public Object getBinding(String name) {
      return parent.getBinding(name);
    }

    @Override
    public void setBinding(String name, Object value) {
      parent.setBinding(name, value);
    }

    @Override
    public Process createCommand(String command, List<String> parameters) {
      return parent.createCommand(command, parameters);
    }

    @Override
    public Object execute(Process[] pipeline) {
      return parent.execute(pipeline);
    }
  }

  @Override
  public final Object execute(Process[] pipeline) {
    Object last = null;
    InputStream readStream = new ByteArrayInputStream(new byte[0]);
    for (int i = 0;i < pipeline.length;i++) {
      Process process = pipeline[i];
      if (i == pipeline.length - 1) {
        last = process.execute(new NestedContext(this, readStream, out));
        try {
          out.flush();
        }
        catch (IOException e) {
          throw new UndeclaredThrowableException(e, "Handle me gracefully");
        }
      } else {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        last = process.execute(new NestedContext(this, readStream, buffer));
        try {
          buffer.close();
        }
        catch (IOException e) {
          throw new UndeclaredThrowableException(e, "Handle me gracefully");
        }
        readStream = new ByteArrayInputStream(buffer.toByteArray());
      }
    }
    return last;
  }

  @Override
  public String toString() {
    return "bindings: " + bindings;
  }
}
