package org.crashub.bash;

import org.crashub.bash.spi.BaseContext;
import org.crashub.bash.spi.SimpleScope;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Julien Viet
 */
public class TestableContext extends BaseContext {

  final SimpleScope bindings = new SimpleScope();

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

  /**
   * Returns a binding.
   *
   * @param name the binding name
   * @return the binding valuee
   */
  public final Object getBinding(String name) {
    return bindings.getValue(name);
  }

  /**
   * Set a binding.
   *
   * @param name the binding name
   * @param value the binding value
   */
  public final void setBinding(String name, Object value) {
    bindings.setValue(name, value);
  }

  public TestableContext bind(String name, Object value) {
    setBinding(name, value);
    return this;
  }

  public Object eval(Script script) {
    return script.eval(bindings, this);
  }
}
