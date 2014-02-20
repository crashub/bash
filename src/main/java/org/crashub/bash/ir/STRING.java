package org.crashub.bash.ir;

import org.crashub.bash.spi.Context;

/**
 * @author Julien Viet
 */
public class STRING extends Expression<String> {

  final Object[] chunks;

  public STRING(Object[] chunks) {
    this.chunks = chunks;
  }

  @Override
  public String eval(Context context) {
    StringBuilder sb = new StringBuilder();
    for (Object chunk : chunks) {
      if (chunk instanceof Node) {
        sb.append(((Node)chunk).eval(context));
      } else {
        sb.append(chunk);
      }
    }
    return sb.toString();
  }
}
