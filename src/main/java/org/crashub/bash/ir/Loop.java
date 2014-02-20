package org.crashub.bash.ir;

import org.crashub.bash.spi.Context;
import org.crashub.bash.spi.Scope;

/**
* @author Julien Viet
*/ // Base class for loops
abstract class Loop extends Node {

  final LIST body;

  Loop(LIST body) {
    this.body = body;
  }

  protected abstract Object test(Scope bindings, Context context, boolean initialize);

  @Override
  public Object eval(Scope bindings, final Context context) {
    boolean initialize = true;
    while  (true) {
      Object value = test(bindings, context, initialize);
      initialize = false;
      if (value instanceof Integer) {
        int v = (Integer) value;
        if (v == 0) {
          break;
        } else {
          body.eval(bindings, context);
        }
      }
    }
    return null;
  }
}
