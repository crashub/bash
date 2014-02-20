package org.crashub.bash.ir;

import org.crashub.bash.spi.Context;
import org.crashub.bash.spi.Scope;

/**
 * @author Julien Viet
 */
public class LIST extends Node {

  final Node[] nodes;

  public LIST(Node[] nodes) {
    this.nodes = nodes;
  }

  @Override
  public Object eval(Scope bindings, Context context) {
    Object last = null;
    for (Node node : nodes) {
      last = node.eval(bindings, context);
    }
    return last;
  }
}
