package org.crashub.bash;

import junit.framework.TestCase;
import org.antlr.runtime.RecognitionException;
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

  public void testARITHMETIC_EXPRESSION_LESS_THAN() throws Exception {
    assertEquals("0", eval("$((2<2))\n"));
    assertEquals("1", eval("$((2<3))\n"));
    assertEquals("0", eval("$((3<2))\n"));
  }

  public void testARITHMETIC_EXPRESSION_LEQ() throws Exception {
    assertEquals("1", eval("$((2<=2))\n"));
    assertEquals("1", eval("$((2<=3))\n"));
    assertEquals("0", eval("$((3<=2))\n"));
  }

  public void testARITHMETIC_EXPRESSION_EQUALS_TO() throws Exception {
    assertEquals("1", eval("$((2==2))\n"));
    assertEquals("0", eval("$((2==3))\n"));
    assertEquals("0", eval("$((3==2))\n"));
  }

  public void testARITHMETIC_EXPRESSION_EQUALS_NOT_EQUALS() throws Exception {
    assertEquals("0", eval("$((2!=2))\n"));
    assertEquals("1", eval("$((2!=3))\n"));
    assertEquals("1", eval("$((3!=2))\n"));
  }

  public void testARITHMETIC_EXPRESSION_GREATER_THAN() throws Exception {
    assertEquals("0", eval("$((2>2))\n"));
    assertEquals("0", eval("$((2>3))\n"));
    assertEquals("1", eval("$((3>2))\n"));
  }

  public void testARITHMETIC_EXPRESSION_GEQ() throws Exception {
    assertEquals("1", eval("$((2>=2))\n"));
    assertEquals("0", eval("$((2>=3))\n"));
    assertEquals("1", eval("$((3>=2))\n"));
  }

  private Object eval(String s) throws RecognitionException {
    return new Script(s).execute();
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

  public void testARITHMETIC_EXPRESSION_TIMES() throws Exception {
    Script script = new Script("$((3*2))\n");
    Object i = script.execute();
    assertEquals("6", i);
  }

  public void testARITHMETIC_EXPRESSION_SLASH() throws Exception {
    Script script = new Script("$((4/2))\n");
    Object i = script.execute();
    assertEquals("2", i);
  }

  public void testSTRING() throws Exception {
    Script script = new Script("2+3\n");
    Object i = script.execute();
    assertEquals("2+3", i);
  }

  public void testVAR_REF() throws Exception {
    Script script = new Script("$((i))");
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
