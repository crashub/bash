package org.crashub.bash.ir;

/**
 * @author Julien Viet
 */
public enum Comparator {

  gt() {
    @Override
    public int compare(int left, int right) {
      return left > right ? 1 : 0;
    }
  }

  ;

  public abstract int compare(int left, int right);

}
