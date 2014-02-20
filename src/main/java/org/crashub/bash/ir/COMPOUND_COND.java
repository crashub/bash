package org.crashub.bash.ir;

import org.crashub.bash.spi.Context;

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
  public Object eval(Context context) {
    Object leftValue = left.eval(context);
    Object rightValue = right.eval(context);
    int leftV = toInt(leftValue);
    int rightV = toInt(rightValue);
    return comparator.compare(leftV, rightV);
  }
}
