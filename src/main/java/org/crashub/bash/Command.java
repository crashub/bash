package org.crashub.bash;

import org.crashub.bash.spi.*;

import java.util.List;

/**
 * A command.
 *
 * @author Julien Viet
 */
public class Command extends Block {

  final String name;
  final List<String> parameters;

  public Command(String name, List<String> parameters) {
    this.name = name;
    this.parameters = parameters;
  }

  @Override
  public org.crashub.bash.spi.Process createProcess(Context context) {
    return context.createCommand(name, parameters);
  }
}
