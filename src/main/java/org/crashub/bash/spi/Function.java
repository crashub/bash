package org.crashub.bash.spi;

import org.crashub.bash.ir.Node;

import java.util.List;

/**
 * A function that can be invoked.
 *
 * @author Julien Viet
 */
public interface Function {

  /**
   * Bind the function to the parameters and returns a node for it.
   *
   * @param parameters the function parameters
   * @return the node applied to the function
   */
  Node bind(List<String> parameters);

}
