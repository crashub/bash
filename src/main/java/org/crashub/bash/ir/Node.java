package org.crashub.bash.ir;

import org.crashub.bash.spi.*;

/**
 * The base intermediate representation node.
 *
 * @param <R> the result generic type
 * @author Julien Viet
 */
public abstract class Node<R> {

  static int toInt(Object o) {
    if (o instanceof Integer) {
      return (Integer)o;
    } else if (o instanceof String) {
      return Integer.parseInt((String)o);
    } else {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * Eval the node and return a result.
   *
   * @param context the evaluation context
   * @return the evalutation result
   */
  public abstract R eval(Context context);


}
