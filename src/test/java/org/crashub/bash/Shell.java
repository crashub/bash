package org.crashub.bash;

import org.crashub.bash.spi.BaseContext;
import org.crashub.bash.spi.SimpleScope;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * @author Julien Viet
 */
public class Shell {

  final ByteArrayOutputStream standardOutput = new ByteArrayOutputStream();
  final SimpleScope bindings = new SimpleScope();
  final HashMap<String, BaseContext.Command> commands = new HashMap<String, BaseContext.Command>();
  final BaseContext context = new BaseContext(new BaseContext.CommandResolver() {
    @Override
    public BaseContext.Command resolveCommand(String command) {
      return commands.get(command);
    }
  }, standardOutput);

  public String getStandardOutput() {
    return standardOutput.toString();
  }

  public Shell command(String name, BaseContext.Command command) {
    commands.put(name, command);
    return this;
  }

  /**
   * Returns a binding.
   *
   * @param name the binding name
   * @return the binding valuee
   */
  public final Object getBinding(String name) {
    return context.getValue(bindings, name);
  }

  /**
   * Set a binding.
   *
   * @param name the binding name
   * @param value the binding value
   */
  public final void setBinding(String name, Object value) {
    context.setValue(bindings, name, value);
  }

  public Shell bind(String name, Object value) {
    setBinding(name, value);
    return this;
  }

  public Object eval(Script script) {
    return script.eval(bindings, context);
  }
}
