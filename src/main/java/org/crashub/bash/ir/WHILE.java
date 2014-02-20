package org.crashub.bash.ir;

import org.crashub.bash.spi.Context;

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
  protected Object test(Context context, boolean initialize) {
    return test.eval(context);
  }
}
