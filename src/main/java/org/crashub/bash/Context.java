package org.crashub.bash;

import java.util.HashMap;
import java.util.List;

/**
 * @author Julien Viet
 */
public class Context {

  final HashMap<String, Object> bindings = new HashMap<String, Object>();

  final CommandInvoker invoker;

  public Context() {
    this(new CommandInvoker() {
      @Override
      public Object invoke(String command, List<String> parameters) {
        throw new UnsupportedOperationException("Cannot invoke command " + command);
      }
    });
  }

  public Context(CommandInvoker invoker) {
    this.invoker = invoker;
  }

  public Object getBinding(String name) {
    return bindings.get(name);
  }

  public Context setBinding(String name, Object value) {
    if (value != null) {
      bindings.put(name, value);
    } else {
      bindings.remove(name);
    }
    return this;
  }

  @Override
  public String toString() {
    return "bindings: " + bindings;
  }
}
