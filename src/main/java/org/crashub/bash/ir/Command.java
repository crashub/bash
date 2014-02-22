package org.crashub.bash.ir;

import org.crashub.bash.spi.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A command.
 *
 * @author Julien Viet
 */
public class Command extends Node {

  final STRING name;
  final List<STRING> parameters;

  public Command(STRING name, List<STRING> parameters) {
    this.name = name;
    this.parameters = parameters;
  }

  @Override
  public Object eval(Scope bindings, Context context) {
    List<String> parameterValues = parameters.isEmpty() ? Collections.<String>emptyList() : new ArrayList<String>(parameters.size());
    for (STRING parameter : parameters) {
      parameterValues.add(parameter.eval(bindings, context));
    }
    String nameValue = name.eval(bindings, context);
    Function f = context.resolveFunction(nameValue);
    Node node = f.bind(parameterValues);
    return context.execute(bindings, node);
  }
}
