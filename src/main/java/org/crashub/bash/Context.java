package org.crashub.bash;

import java.util.HashMap;
import java.util.List;

/**
 * The context of execution.
 *
 * @author Julien Viet
 */
public abstract class Context {

  final HashMap<String, Object> bindings = new HashMap<String, Object>();

  public Object getBinding(String name) {
    return bindings.get(name);
  }

  public Context setBinding(String name, Object value) {
    if (value != null) {
      bindings.put(name, value);
    } else {
      bindings.remove(name);
    }
    return this;
  }

  /**
   * Create a process for the command.
   *
   * @param command the command name to execute
   * @param parameters the command parameters
   * @return the command process
   */
  protected abstract Process createCommand(String command, List<String> parameters);

  /**
   * Execute a process pipeline.
   *
   * @param pipeline the pipeline
   * @return the pipeline result
   */
  protected abstract Object execute(Process[] pipeline);

  @Override
  public String toString() {
    return "bindings: " + bindings;
  }
}
