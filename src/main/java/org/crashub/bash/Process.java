package org.crashub.bash;

/**
 * A process.
 *
 * @author Julien Viet
 */
public interface Process {

  /**
   * Execute the process
   *
   * @param standardInput the input
   * @param standardOutput the output
   * @return the process result
   */
  Object execute(ReadStream standardInput, WriteStream standardOutput);

}
