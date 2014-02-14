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

  public void testVARIABLE_DEFINITIONS() throws Exception {
    Script script = new Script("i=3\n");
    Context context = new Context();
    script.execute(context);
    Object i = context.getBinding("i");
    assertEquals("3", i);
  }

  public void testARITHMETIC_EXPRESSION_PLUS() throws Exception {
    Script script = new Script("$((2+3))\n");
    Object i = script.execute();
    assertEquals("5", i);
  }

  public void testARITHMETIC_EXPRESSION_MINUS() throws Exception {
    Script script = new Script("$((3-2))\n");
    Object i = script.execute();
    assertEquals("1", i);
  }

  public void testSTRING() throws Exception {
    Script script = new Script("2+3\n");
    Object i = script.execute();
    assertEquals("2+3", i);
  }

  public void testVAR_REF() throws Exception {
    System.out.println("Arithmetic:");
    Script script = new Script("$((i))");
    script.prettyPrint();
    Context context = new Context();
    context.setBinding("i", "3");
    Object i = script.execute(context);
    assertEquals("3", i);
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
    Context context = new Context();
    script.execute(context);
    Object i = context.getBinding("i");
    assertEquals("6", i);
  }
}
