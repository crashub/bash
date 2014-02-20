package org.crashub.bash.ir;

import org.crashub.bash.spi.Context;
import org.crashub.bash.spi.Scope;

/**
 * @author Julien Viet
 */
public class Function extends Node {

  final String name;
  final LIST body;

  public Function(String name, LIST body) {
    this.name = name;
    this.body = body;
  }

  @Override
  public Object eval(Scope bindings, Context context) {
    context.setFunction(name, new Node() {
      @Override
      public Object eval(Scope bindings, Context context) {
        return body.eval(new LocalScope(bindings), context);
      }
    });
    return null;
  }
}
