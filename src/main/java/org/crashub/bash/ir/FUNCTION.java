package org.crashub.bash.ir;

import org.crashub.bash.spi.Context;

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
  public Object eval(Context context) {
    context.setFunction(name, body);
    return null;
  }
}
