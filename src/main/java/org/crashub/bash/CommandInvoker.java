package org.crashub.bash;

import java.util.List;

/**
 * @author Julien Viet
 */
public interface CommandInvoker {

  /**
   * Execute the specified command.
   *
   * @param command the command name to execute
   */
  Object invoke(String command, List<String> parameters);

}
