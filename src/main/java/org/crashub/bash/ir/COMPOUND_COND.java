package org.crashub.bash.ir;

import org.crashub.bash.spi.Context;
import org.crashub.bash.spi.Scope;

/**
 * @author Julien Viet
 */
public class COMPOUND_COND extends Node {

  final Comparator comparator;
  final STRING left;
  final STRING right;

  public COMPOUND_COND(Comparator comparator, STRING left, STRING right) {
    this.comparator = comparator;
    this.left = left;
    this.right = right;
  }

  @Override
  public Object eval(Scope bindings, Context context) {
    Object leftValue = left.eval(bindings, context);
    Object rightValue = right.eval(bindings, context);
    int leftV = toInt(leftValue);
    int rightV = toInt(rightValue);
    return comparator.compare(leftV, rightV);
  }
}
