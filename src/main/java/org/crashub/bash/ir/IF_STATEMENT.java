package org.crashub.bash.ir;

import org.crashub.bash.spi.Context;

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
  public Object eval(Context context) {
    Object b = test.eval(context);
    int v = toInt(b);
    if (v != 0) {
      return then.eval(context);
    } else {
      if (else_ != null) {
        return else_.eval(context);
      } else {
        return null;
      }
    }
  }
}
