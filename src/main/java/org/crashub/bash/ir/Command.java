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
  public Object eval(Context context) {
    List<String> parameterValues = parameters.isEmpty() ? Collections.<String>emptyList() : new ArrayList<String>(parameters.size());
    for (STRING parameter : parameters) {
      parameterValues.add(parameter.eval(context));
    }
    String nameValue = name.eval(context);
    return context.execute(context.createCommand(nameValue, parameterValues));
  }
}
