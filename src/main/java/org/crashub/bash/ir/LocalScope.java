package org.crashub.bash.ir;

import org.crashub.bash.spi.Scope;
import org.crashub.bash.spi.SimpleScope;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Julien Viet
 */
public class LocalScope extends SimpleScope {

  private final Scope global;
  private Set<String> locals; // The local declarations

  public LocalScope(Scope global, List<String> parameters) {
    this.global = global;
    this.locals = Collections.emptySet();

    // Should we declare them local too ?
    int size = parameters.size();
    if (size > 0) {
      for (int i = 0;i < size;i++) {
        String name = Integer.toString(i + 1);
        super.setValue(name, parameters.get(i));
      }
    }
    super.setValue("#", parameters.size());
  }

  void declareLocal(String name, Object value) {
    if (locals.isEmpty()) {
      locals = new HashSet<String>();
    }
    locals.add(name);
    super.setValue(name, value);
  }

  @Override
  public Object getValue(String name) {
    Object value = super.getValue(name);
    if (value == null) {
      value = global.getValue(name);
    }
    return value;
  }

  @Override
  public void setValue(String name, Object value) {
    if (locals.contains(name)) {
      super.setValue(name, value);
    } else {
      global.setValue(name, value);
    }
  }
}
