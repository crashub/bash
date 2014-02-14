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

  public void setBinding(String name, Object value) {
    if (value != null) {
      bindings.put(name, value);
    } else {
      bindings.remove(name);
    }
  }

  @Override
  public String toString() {
    return "bindings: " + bindings;
  }
}
