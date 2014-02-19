package org.crashub.bash;

import org.crashub.bash.spi.*;

/**
 * A block provides code to be executed in a context managed by the runtime.
 *
 * @author Julien Viet
 */
public abstract class Block {

  /**
   * Create a process for the specified context.
   *
   * @param context the context of execution
   * @return the process
   */
  public abstract org.crashub.bash.spi.Process createProcess(Context context);

}
