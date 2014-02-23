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
   *
   * @param name the function name
   * @param function the function node
   */
  public abstract void setFunction(String name, Function function);

  /**
   * Resolve a named function.
   *
   * @param name the function name
   * @return the function node
   */
  public abstract Function resolveFunction(String name);

  /**
   * Execute a process pipeline.
   *
   * @param bindings the bindings for the execution
   * @param pipeline the pipeline to execute
   * @return the pipeline result
   */
  public abstract Object execute(Scope bindings, Node... pipeline);

  public abstract Object getValue(Scope scope, String name);

  public abstract void setValue(Scope scope, String name, Object value);
}
