package org.crashub.bash.ir;

import org.crashub.bash.spi.Context;
import org.crashub.bash.spi.Function;
import org.crashub.bash.spi.Scope;

import java.util.List;

/**
 * @author Julien Viet
 */
public class FunctionNode extends Node  {

  final String name;
  final LIST body;

  public FunctionNode(String name, LIST body) {
    this.name = name;
    this.body = body;
  }

  @Override
  public Object eval(Scope bindings, Context context) {
    context.setFunction(name, new Function() {
      @Override
      public Node bind(final List<String> parameters) {
        return new Node() {
          @Override
          public Object eval(Scope bindings, Context context) {
            LocalScope functionScope = new LocalScope(bindings);
            int size = parameters.size();
            if (size > 0) {
              for (int i = 0;i < size;i++) {
                String name = Integer.toString(i + 1);
                context.setValue(functionScope, name, parameters.get(i));
              }
            }
            context.setValue(functionScope, "#", parameters.size());
            return body.eval(functionScope, context);
          }
        };
      }
    });
    return null;
  }
}
