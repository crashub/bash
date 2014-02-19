package org.crashub.bash;

import org.crashub.bash.spi.BaseContext;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Julien Viet
 */
public class TestableContext extends BaseContext {

  public TestableContext() {
    this(new CommandResolver() {
      @Override
      public Callable<?> resolveCommand(String command, List<String> parameters, InputStream standardInput, OutputStream standardOutput) {
        return null;
      }
    });
  }

  public TestableContext(CommandResolver commandResolver) {
    super(commandResolver, new ByteArrayOutputStream());
  }

  public String getStandardOutput() {
    return new String(((ByteArrayOutputStream)standardOutput).toByteArray());
  }
}
