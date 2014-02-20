package org.crashub.bash.ir;

import org.crashub.bash.spi.Context;

/**
 * @author Julien Viet
 */
public class LIST extends Node {

  final Node[] nodes;

  public LIST(Node[] nodes) {
    this.nodes = nodes;
  }

  @Override
  public Object eval(Context context) {
    Object last = null;
    for (Node node : nodes) {
      last = node.eval(context);
    }
    return last;
  }
}
