package org.crashub.bash.spi;

import org.crashub.bash.ir.Node;

import java.lang.*;
import java.util.List;

/**
 * The context of execution provided by the runtime.
 *
 * @author Julien Viet
 */
public abstract class Context {

  /**
   * Set a function.
   * @param name the function name
   * @param function the function node
   */
  public abstract void setFunction(String name, Function function);

  /**
   * Return a function.
   *
   * @param name the function name
   * @return the function node
   */
  public abstract Function getFunction(String name);

  /**
   * Create a process for the command.
   *
   * @param command the command name to execute
   * @param parameters the command parameters
   * @return the command process
   */
  public abstract Node createCommand(String command, List<String> parameters);

  /**
   * Execute a process pipeline.
   *
   *
   *
   * @param bindings
   * @param pipeline the pipeline
   * @return the pipeline result
   */
  public abstract Object execute(Scope bindings, Node... pipeline);

}
