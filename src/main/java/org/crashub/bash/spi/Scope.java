package org.crashub.bash.spi;

/**
 * A scope for things.
 *
 * @author Julien Viet
 */
public interface Scope {

  /**
   * Returns a value for a name.
   *
   * @param name the name to look for
   * @return the value for the name
   */
  Object getValue(String name);

  /**
   * Set a name/value pair.
   *
   * @param name the name to set
   * @param value the value to set for the name
   */
  void setValue(String name, Object value);

}
