package org.crashub.bash.ir;

import org.crashub.bash.spi.Context;
import org.crashub.bash.spi.Scope;

import java.util.List;

/**
 * @author Julien Viet
 */
public class PIPE extends Node {

  final List<Node> elements;

  public PIPE(List<Node> elements) {
    this.elements = elements;
  }

  @Override
  public Object eval(Scope bindings, Context context) {
    return context.execute(bindings, elements.toArray(new Node[elements.size()]));
  }
}
