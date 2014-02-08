package org.crashub.bash;

import java.util.HashMap;

/**
 * @author Julien Viet
 */
public class Context {

  final HashMap<String, Object> bindings = new HashMap<String, Object>();

  public Object getBinding(String name) {
    return bindings.get(name);
  }

  @Override
  public String toString() {
    return "bindings: " + bindings;
  }
}
