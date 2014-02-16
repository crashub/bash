package org.crashub.bash;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import org.antlr.runtime.RecognitionException;
import org.crashub.bash.Context;
import org.crashub.bash.Script;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Julien Viet
 */
public class TestScript extends TestCase {

  private Object eval(String s) throws RecognitionException {
    return eval(new Context(), s);
  }

  private Object eval(Context context, String s) throws RecognitionException {
    Script script = new Script("a=" + s);
    script.execute(context);
    return context.getBinding("a");
  }

  public void testCommand() throws Exception {
    final AtomicInteger count = new AtomicInteger();
    Object ret = new Script("foo").execute(new Context(new CommandInvoker() {
      @Override
      public Object invoke(String command, List<String> parameters) {
        if ("foo".equals(command) && parameters.isEmpty()) {
          count.incrementAndGet();
          return "foo_value";
        } else {
          throw new AssertionFailedError();
        }
      }
    }));
    assertEquals("foo_value", ret);
  }

  public void testCommandWithArgument() throws Exception {
    final AtomicInteger count = new AtomicInteger();
    new Script("foo bar").prettyPrint();
    Object ret = new Script("foo bar").execute(new Context(new CommandInvoker() {
      @Override
      public Object invoke(String command, List<String> parameters) {
        if ("foo".equals(command) && Arrays.asList("bar").equals(parameters)) {
          count.incrementAndGet();
          return "foo_value";
        } else {
          throw new AssertionFailedError();
        }
      }
    }));
    assertEquals("foo_value", ret);
  }

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

  public void testARITHMETIC_EXPRESSION_PLUS() throws Exception {
    assertEquals("5", eval("$((2+3))\n"));
  }

  public void testARITHMETIC_EXPRESSION_MINUS() throws Exception {
    assertEquals("1", eval("$((3-2))\n"));
  }

  public void testARITHMETIC_EXPRESSION_TIMES() throws Exception {
    assertEquals("6", eval("$((3*2))\n"));
  }

  public void testARITHMETIC_EXPRESSION_SLASH() throws Exception {
    assertEquals("2", eval("$((4/2))\n"));
  }

  public void testARITHMETIC_EXPRESSION_PCT() throws Exception {
    assertEquals("1", eval("$((5%2))\n"));
  }

  public void testARITHMETIC_EXPRESSION_PRE_INCR() throws Exception {
    Context context1 = new Context().setBinding("x", "5");
    assertEquals("6", eval(context1, "$((++x))\n"));
    assertEquals(6, context1.getBinding("x"));
    Context context2 = new Context();
    assertEquals("1", eval(context2, "$((++x))\n"));
    assertEquals(1, context2.getBinding("x"));
  }

  public void testARITHMETIC_EXPRESSION_PRE_DECR() throws Exception {
    Context context1 = new Context().setBinding("x", "5");
    assertEquals("4", eval(context1, "$((--x))\n"));
    assertEquals(4, context1.getBinding("x"));
    Context context2 = new Context();
    assertEquals("-1", eval(context2, "$((--x))\n"));
    assertEquals(-1, context2.getBinding("x"));
  }

  public void testARITHMETIC_EXPRESSION_POST_INCR() throws Exception {
    Context context1 = new Context().setBinding("x", "5");
    assertEquals("5", eval(context1, "$((x++))\n"));
    assertEquals(6, context1.getBinding("x"));
    Context context2 = new Context();
    assertEquals("0", eval(context2, "$((x++))\n"));
    assertEquals(1, context2.getBinding("x"));
  }

  public void testARITHMETIC_EXPRESSION_POST_DECR() throws Exception {
    Context context1 = new Context().setBinding("x", "5");
    assertEquals("5", eval(context1, "$((x--))\n"));
    assertEquals(4, context1.getBinding("x"));
    Context context2 = new Context();
    assertEquals("0", eval(context2, "$((x--))\n"));
    assertEquals(-1, context2.getBinding("x"));
  }

  public void testSTRING() throws Exception {
    assertEquals("2+3", eval("2+3\n"));
    assertEquals("def", eval(new Context().setBinding("abc", "def"), "${abc}"));
  }

  public void testDISPLAY_ERROR_WHEN_UNSET() throws Exception {
    assertEquals("def", eval(new Context().setBinding("abc", "def"), "${abc?does not exist}"));
    assertEquals("", eval(new Context().setBinding("abc", ""), "${abc?does not exist}"));
    try {
      eval("${abc?does not exist}");
      fail();
    }
    catch (RuntimeException expected) {
      assertEquals("does not exist", expected.getMessage());
    }
  }

  public void testDISPLAY_ERROR_WHEN_UNSET_OR_NULL() throws Exception {
    assertEquals("def", eval(new Context().setBinding("abc", "def"), "${abc:?does not exist}"));
    try {
      eval(new Context().setBinding("abc", ""), "${abc:?does not exist}");
      fail();
    }
    catch (RuntimeException expected) {
      assertEquals("does not exist", expected.getMessage());
    }
    try {
      eval("${abc:?does not exist}");
      fail();
    }
    catch (RuntimeException expected) {
      assertEquals("does not exist", expected.getMessage());
    }
  }

  public void testASSIGN_DEFAULT_WHEN_UNSET() throws Exception {
    Context context1 = new Context();
    assertEquals("ghi", eval(context1, "${abc=ghi}"));
    assertEquals("ghi", context1.getBinding("abc"));
    Context context2 = new Context().setBinding("abc", "def");
    assertEquals("def", eval(context2, "${abc=ghi}"));
    assertEquals("def", context2.getBinding("abc"));
    Context context3 = new Context().setBinding("abc", "");
    assertEquals("", eval(context3, "${abc=ghi}"));
    assertEquals("", context3.getBinding("abc"));
  }

  public void testUSE_DEFAULT_WHEN_UNSET_OR_NULL() throws Exception {
    Context context1 = new Context();
    assertEquals("ghi", eval(context1, "${abc:-ghi}"));
    assertEquals(null, context1.getBinding("abc"));
    Context context2 = new Context().setBinding("abc", "def");
    assertEquals("def", eval(context2, "${abc:-ghi}"));
    assertEquals("def", context2.getBinding("abc"));
    Context context3 = new Context().setBinding("abc", "");
    assertEquals("ghi", eval(context3, "${abc:-ghi}"));
    assertEquals("", context3.getBinding("abc"));
  }

  public void testASSIGN_DEFAULT_WHEN_UNSET_OR_NULL() throws Exception {
    Context context1 = new Context();
    assertEquals("ghi", eval(context1, "${abc:=ghi}"));
    assertEquals("ghi", context1.getBinding("abc"));
    Context context2 = new Context().setBinding("abc", "def");
    assertEquals("def", eval(context2, "${abc:=ghi}"));
    assertEquals("def", context2.getBinding("abc"));
    Context context3 = new Context().setBinding("abc", "");
    assertEquals("ghi", eval(context3, "${abc:=ghi}"));
    assertEquals("ghi", context3.getBinding("abc"));
  }

  public void testVAR_REF() throws Exception {
    Script script = new Script("a=$((i))");
    Context context = new Context();
    context.setBinding("i", "3");
    script.execute(context);
    assertEquals("3", context.getBinding("a"));
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
