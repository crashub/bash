package org.crashub.bash.ir;

import org.crashub.bash.spi.Context;

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
  protected Object test(Context context, boolean initialize) {
    if (initialize) {
      init.eval(context);
    } else {
      mod.eval(context);
    }
    return test.eval(context);
  }
}
