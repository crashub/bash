package org.crashub.bash.spi;

/**
 * A process.
 *
 * @author Julien Viet
 */
public interface Process {

  /**
   * Execute the process.
   *
   * @param context the context of execution for this process
   * @return the process result
   */
  Object execute(Context context);

}
