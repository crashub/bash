package org.crashub.bash;

/**
 * @author Julien Viet
 */
public abstract class Block {

  /**
   * Create a process for the specified context.
   *
   * @param context the context of execution
   * @return the process
   */
  public abstract Process createProcess(Context context);

}
