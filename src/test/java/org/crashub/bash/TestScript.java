package org.crashub.bash;

import junit.framework.TestCase;
import org.crashub.bash.Context;
import org.crashub.bash.Script;

/**
 * @author Julien Viet
 */
public class TestScript extends TestCase {

  public void testPipeline() throws Exception {
    System.out.println("Pipeline:");
    new Script("echo \"abc\" | less").prettyPrint();
  }

  public void testBackquotes() throws Exception {
    System.out.println("Backquotes:");
    new Script("echo `cat foo.txt`").prettyPrint();
  }

  public void testCase() throws Exception {
    System.out.println("Case:");
    new Script("case \"$1\" in\n" +
        "1) echo \"1\"\n" +
        "   ;;\n" +
        "esac").prettyPrint();
  }

  public void testArithmetic() throws Exception {
    System.out.println("Arithmetic:");
    Script script = new Script(
        "i=0\n" +
        "i=$((i+1))");
    script.prettyPrint();
    Context context = script.execute();
    Object i = context.getBinding("i");
    assertEquals(1, i);
  }

  public void testWhile() throws Exception {
    System.out.println("While:");
    Script script = new Script(
        "i=0\n" +
        "while (( $i <= 5 ))\n" +
        "do\n" +
        "   i=$((i+1))\n" +
        "done");
    script.prettyPrint();
    Context context = script.execute();
    Object i = context.getBinding("i");
    assertEquals(6, i);
  }
}
