package org.crashub.bash.ir;

import org.crashub.bash.spi.Scope;
import org.crashub.bash.spi.SimpleScope;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Julien Viet
 */
public class LocalScope extends SimpleScope {

  private final Scope global;
  private Set<String> locals; // The local declarations

  public LocalScope(Scope global) {
    this.global = global;
    this.locals = Collections.emptySet();
  }

  void declareLocal(String name) {
    if (locals.isEmpty()) {
      locals = new HashSet<String>();
    }
    locals.add(name);
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
