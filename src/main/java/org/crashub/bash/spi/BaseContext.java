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
      public Object execute(ReadStream standardInput, WriteStream standardOutput) {
        Callable<?> impl = createCommand(
            command,
            parameters,
            ((BinaryReadStream)standardInput).in,
            ((BinaryWriteStream)standardOutput).out);
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
  public final Object execute(Process[] pipeline) {
    Object last = null;
    BinaryReadStream readStream = new BinaryReadStream(new ByteArrayInputStream(new byte[0]));
    for (int i = 0;i < pipeline.length;i++) {
      Process process = pipeline[i];
      if (i == pipeline.length - 1) {
        BinaryWriteStream writeStream = new BinaryWriteStream(out);
        last = process.execute(readStream, writeStream);
        try {
          writeStream.out.flush();
        }
        catch (IOException e) {
          throw new UndeclaredThrowableException(e, "Handle me gracefully");
        }
      } else {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        BinaryWriteStream writeStream = new BinaryWriteStream(buffer);
        last = process.execute(readStream, writeStream);
        try {
          writeStream.out.close();
        }
        catch (IOException e) {
          throw new UndeclaredThrowableException(e, "Handle me gracefully");
        }
        readStream = new BinaryReadStream(new ByteArrayInputStream(buffer.toByteArray()));
      }
    }
    return last;
  }

  private static class BinaryReadStream implements ReadStream {
    final InputStream in;
    protected BinaryReadStream(InputStream in) {
      this.in = in;
    }
  }

  private static class BinaryWriteStream implements WriteStream {
    final OutputStream out;
    protected BinaryWriteStream(OutputStream out) {
      this.out = out;
    }
  }

  @Override
  public String toString() {
    return "bindings: " + bindings;
  }
}
