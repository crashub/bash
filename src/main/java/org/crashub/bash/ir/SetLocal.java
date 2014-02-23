package org.crashub.bash.ir;

import org.crashub.bash.spi.Context;
import org.crashub.bash.spi.Scope;

/**
 * @author Julien Viet
 */
public class SetLocal extends Node {

  final String identifier;
  final Expression.Assign assign; // Optional assign

  public SetLocal(String identifier, Expression.Assign assign) {
    this.identifier = identifier;
    this.assign = assign;
  }

  public SetLocal(String identifier) {
    this.identifier = identifier;
    this.assign = null;
  }

  @Override
  public Object eval(Scope bindings, Context context) {
    if (bindings instanceof LocalScope) {
      LocalScope functionBindings = (LocalScope)bindings;
      functionBindings.declareLocal(identifier);
      context.setValue(functionBindings, identifier, "");
    } else {
      throw new RuntimeException("local: can only be used in a function");
    }
    if (assign != null) {
      assign.eval(bindings, context);
    }
    return null;
  }
}
