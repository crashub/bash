package org.crashub.bash.ir;

import org.crashub.bash.spi.Context;
import org.crashub.bash.spi.Scope;

/**
 * @author Julien Viet
 */
public class IF_STATEMENT extends Node<Object> {

  final Node test;
  final Node then;
  final Node else_;

  public IF_STATEMENT(Node test, Node then, Node else_) {
    this.test = test;
    this.then = then;
    this.else_ = else_;
  }

  @Override
  public Object eval(Scope bindings, Context context) {
    Object b = test.eval(bindings, context);
    int v = toInt(b);
    if (v != 0) {
      return then.eval(bindings, context);
    } else {
      if (else_ != null) {
        return else_.eval(bindings, context);
      } else {
        return null;
      }
    }
  }
}
