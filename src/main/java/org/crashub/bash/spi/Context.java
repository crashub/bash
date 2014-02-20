package org.crashub.bash.spi;

import org.crashub.bash.ir.Node;

import java.lang.*;
import java.util.List;

/**
 * The context of execution provided by the runtime.
 *
 * @author Julien Viet
 */
public interface Context {

  /**
   * Returns a binding.
   *
   * @param name the binding name
   * @return the binding valuee
   */
  Object getBinding(String name);

  /**
   * Set a binding.
   *
   * @param name the binding name
   * @param value the binding value
   */
  void setBinding(String name, Object value);

  /**
   * Set a function.
   * @param name the function name
   * @param function the function node
   */
  void setFunction(String name, Node function);

  /**
   * Return a function.
   *
   * @param name the function name
   * @return the function node
   */
  Node getFunction(String name);

  /**
   * Create a process for the command.
   *
   * @param command the command name to execute
   * @param parameters the command parameters
   * @return the command process
   */
  Node createCommand(String command, List<String> parameters);

  /**
   * Execute a process pipeline.
   *
   * @param pipeline the pipeline
   * @return the pipeline result
   */
  Object execute(Node... pipeline);

}
