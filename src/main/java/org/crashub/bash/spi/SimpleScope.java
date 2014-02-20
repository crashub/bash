package org.crashub.bash.spi;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Julien Viet
 */
public class SimpleScope implements Scope {

  final Map<String, Object> pairs = new HashMap<String, Object>();

  @Override
  public Object getValue(String name) {
    return pairs.get(name);
  }

  @Override
  public void setValue(String name, Object value) {
    if (value != null) {
      pairs.put(name, value);
    } else {
      pairs.remove(name);
    }
  }
}
