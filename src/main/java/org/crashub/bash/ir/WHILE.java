package org.crashub.bash.ir;

import org.crashub.bash.spi.Context;
import org.crashub.bash.spi.Scope;

/**
 * @author Julien Viet
 */
public class WHILE extends Loop {

  final LIST test;

  public WHILE(LIST body, LIST test) {
    super(body);
    this.test = test;
  }

  @Override
  protected Object test(Scope bindings, Context context, boolean initialize) {
    return test.eval(bindings, context);
  }
}
