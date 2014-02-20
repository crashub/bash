package org.crashub.bash.ir;

import org.crashub.bash.spi.Context;
import org.crashub.bash.spi.Scope;

/**
 * @author Julien Viet
 */
public class CFOR extends Loop {

  final Expression<?> init;
  final Expression<?> mod;
  final Expression<?> test;

  public CFOR(LIST body, Expression<?> init, Expression<?> mod, Expression<?> test) {
    super(body);
    this.init = init;
    this.mod = mod;
    this.test = test;
  }

  @Override
  protected Object test(Scope bindings, Context context, boolean initialize) {
    if (initialize) {
      init.eval(bindings, context);
    } else {
      mod.eval(bindings, context);
    }
    return test.eval(bindings, context);
  }
}
