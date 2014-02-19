package org.crashub.bash;

import junit.framework.TestCase;
import org.antlr.runtime.RecognitionException;
import org.crashub.bash.spi.BaseContext;
import org.crashub.bash.spi.Context;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Julien Viet
 */
public class TestScript extends TestCase {

  private Object eval(String s) throws RecognitionException {
    return eval(new TestableContext(), s);
  }

  private Object eval(Context context, String s) throws RecognitionException {
    Script script = new Script("a=" + s);
    script.execute(context);
    return context.getBinding("a");
  }

  public void testCommand() throws Exception {
    final AtomicInteger count = new AtomicInteger();
    Object ret = new Script("foo").execute(new TestableContext() {
      @Override
      protected Callable<?> createCommand(String command, List<String> parameters, InputStream in, OutputStream out) {
        if ("foo".equals(command) && parameters.isEmpty()) {
          return new Callable<Object>() {
            @Override
            public Object call() throws Exception {
              count.incrementAndGet();
              return "foo_value";
            }
          };
        } else {
          return null;
        }
      }
    });
    assertEquals("foo_value", ret);
  }

  public void testCommandWithArgument() throws Exception {
    final AtomicInteger count = new AtomicInteger();
    Object ret = new Script("foo bar").execute(new TestableContext() {
      @Override
      protected Callable<?> createCommand(String command, List<String> parameters, InputStream in, OutputStream out) {
        if ("foo".equals(command) && Arrays.asList("bar").equals(parameters)) {
          return new Callable<Object>() {
            @Override
            public Object call() throws Exception {
              count.incrementAndGet();
              return "foo_value";
            }
          };
        } else {
          return null;
        }
      }
    });
    assertEquals("foo_value", ret);
  }

  public void testPipeline() throws Exception {
    final ArrayList<String> list = new ArrayList<String>();
    new Script("foo | bar").execute(new TestableContext() {
      @Override
      protected Callable<?> createCommand(final String command, List<String> parameters, InputStream in, OutputStream out) {
        return new Callable<Object>() {
          @Override
          public Object call() throws Exception {
            list.add(command);
            return null;
          }
        };
      }
    });
    assertEquals(Arrays.asList("foo", "bar"), list);
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
    Context context = new TestableContext();
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
    Context context1 = new TestableContext().bind("x", "5");
    assertEquals("6", eval(context1, "$((++x))\n"));
    assertEquals(6, context1.getBinding("x"));
    Context context2 = new TestableContext();
    assertEquals("1", eval(context2, "$((++x))\n"));
    assertEquals(1, context2.getBinding("x"));
  }

  public void testARITHMETIC_EXPRESSION_PRE_DECR() throws Exception {
    Context context1 = new TestableContext().bind("x", "5");
    assertEquals("4", eval(context1, "$((--x))\n"));
    assertEquals(4, context1.getBinding("x"));
    Context context2 = new TestableContext();
    assertEquals("-1", eval(context2, "$((--x))\n"));
    assertEquals(-1, context2.getBinding("x"));
  }

  public void testARITHMETIC_EXPRESSION_POST_INCR() throws Exception {
    Context context1 = new TestableContext().bind("x", "5");
    assertEquals("5", eval(context1, "$((x++))\n"));
    assertEquals(6, context1.getBinding("x"));
    Context context2 = new TestableContext();
    assertEquals("0", eval(context2, "$((x++))\n"));
    assertEquals(1, context2.getBinding("x"));
  }

  public void testARITHMETIC_EXPRESSION_POST_DECR() throws Exception {
    Context context1 = new TestableContext().bind("x", "5");
    assertEquals("5", eval(context1, "$((x--))\n"));
    assertEquals(4, context1.getBinding("x"));
    Context context2 = new TestableContext();
    assertEquals("0", eval(context2, "$((x--))\n"));
    assertEquals(-1, context2.getBinding("x"));
  }

  public void testSTRING() throws Exception {
    assertEquals("2+3", eval("2+3\n"));
    assertEquals("def", eval(new TestableContext().bind("abc", "def"), "${abc}"));
  }

  public void testDISPLAY_ERROR_WHEN_UNSET() throws Exception {
    assertEquals("def", eval(new TestableContext().bind("abc", "def"), "${abc?does not exist}"));
    assertEquals("", eval(new TestableContext().bind("abc", ""), "${abc?does not exist}"));
    try {
      eval("${abc?does not exist}");
      fail();
    }
    catch (RuntimeException expected) {
      assertEquals("does not exist", expected.getMessage());
    }
  }

  public void testDISPLAY_ERROR_WHEN_UNSET_OR_NULL() throws Exception {
    assertEquals("def", eval(new TestableContext().bind("abc", "def"), "${abc:?does not exist}"));
    try {
      eval(new TestableContext().bind("abc", ""), "${abc:?does not exist}");
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
    Context context1 = new TestableContext();
    assertEquals("ghi", eval(context1, "${abc=ghi}"));
    assertEquals("ghi", context1.getBinding("abc"));
    Context context2 = new TestableContext().bind("abc", "def");
    assertEquals("def", eval(context2, "${abc=ghi}"));
    assertEquals("def", context2.getBinding("abc"));
    Context context3 = new TestableContext().bind("abc", "");
    assertEquals("", eval(context3, "${abc=ghi}"));
    assertEquals("", context3.getBinding("abc"));
  }

  public void testUSE_DEFAULT_WHEN_UNSET_OR_NULL() throws Exception {
    Context context1 = new TestableContext();
    assertEquals("ghi", eval(context1, "${abc:-ghi}"));
    assertEquals(null, context1.getBinding("abc"));
    Context context2 = new TestableContext().bind("abc", "def");
    assertEquals("def", eval(context2, "${abc:-ghi}"));
    assertEquals("def", context2.getBinding("abc"));
    Context context3 = new TestableContext().bind("abc", "");
    assertEquals("ghi", eval(context3, "${abc:-ghi}"));
    assertEquals("", context3.getBinding("abc"));
  }

  public void testASSIGN_DEFAULT_WHEN_UNSET_OR_NULL() throws Exception {
    Context context1 = new TestableContext();
    assertEquals("ghi", eval(context1, "${abc:=ghi}"));
    assertEquals("ghi", context1.getBinding("abc"));
    Context context2 = new TestableContext().bind("abc", "def");
    assertEquals("def", eval(context2, "${abc:=ghi}"));
    assertEquals("def", context2.getBinding("abc"));
    Context context3 = new TestableContext().bind("abc", "");
    assertEquals("ghi", eval(context3, "${abc:=ghi}"));
    assertEquals("ghi", context3.getBinding("abc"));
  }

  public void testVAR_REF() throws Exception {
    Script script = new Script("a=$((i))");
    BaseContext context = new TestableContext();
    context.bind("i", "3");
    script.execute(context);
    assertEquals("3", context.getBinding("a"));
  }

  public void testWhile() throws Exception {
    Script script = new Script(
        "i=0\n" +
        "while (( $i <= 5 ))\n" +
        "do\n" +
        "i=$((i+1))\n" +
        "done");
    Context context = new TestableContext();
    script.execute(context);
    Object i = context.getBinding("i");
    assertEquals("6", i);
  }

  public void testCFOR() throws Exception {
    Script script = new Script(
        "j=0\n" +
        "for((i=1;i<=5;i++))\n" +
        "do\n" +
        "j=$((i))\n" +
        "done");
    Context context = new TestableContext();
    script.execute(context);
    Object j = context.getBinding("j");
    assertEquals("5", j);
  }
}
