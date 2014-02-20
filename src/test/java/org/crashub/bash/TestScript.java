package org.crashub.bash;

import junit.framework.TestCase;
import org.antlr.runtime.RecognitionException;
import org.crashub.bash.spi.BaseContext;
import org.crashub.bash.spi.Context;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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
    script.eval(context);
    return context.getBinding("a");
  }

  public void testCommand() throws Exception {
    final AtomicInteger count = new AtomicInteger();
    Object ret = new Script("foo").eval(new TestableContext(new BaseContext.CommandResolver() {
      @Override
      public Callable<?> resolveCommand(String command, List<String> parameters, InputStream standardInput, OutputStream standardOutput) {
        if ("foo".equals(command) && parameters.isEmpty()) {
          return new Callable<Object>() {
            @Override
            public Object call() throws Exception {
              count.incrementAndGet();
              return "foo_value";
            }
          };
        }
        else {
          return null;
        }
      }
    }));
    assertEquals("foo_value", ret);
  }

  public void testCommandWithArgument() throws Exception {
    final AtomicInteger count = new AtomicInteger();
    Object ret = new Script("foo bar").eval(new TestableContext(new BaseContext.CommandResolver() {
      @Override
      public Callable<?> resolveCommand(String command, List<String> parameters, InputStream standardInput, OutputStream standardOutput) {
        if ("foo".equals(command) && Arrays.asList("bar").equals(parameters)) {
          return new Callable<Object>() {
            @Override
            public Object call() throws Exception {
              count.incrementAndGet();
              return "foo_value";
            }
          };
        }
        else {
          return null;
        }
      }
    }));
    assertEquals("foo_value", ret);
  }

  public void testPipeline() throws Exception {
    final ArrayList<String> list = new ArrayList<String>();
    BaseContext.CommandResolver resolver = new BaseContext.CommandResolver() {
      @Override
      public Callable<?> resolveCommand(final String command, List<String> parameters, InputStream standardInput, OutputStream standardOutput) {
        return new Callable<Object>() {
          @Override
          public Object call() throws Exception {
            list.add(command);
            return null;
          }
        };
      }
    };
    new Script("foo | bar").eval(new TestableContext(resolver));
    assertEquals(Arrays.asList("foo", "bar"), list);
    list.clear();
    new Script("foo | bar | juu").eval(new TestableContext(resolver));
    assertEquals(Arrays.asList("foo", "bar", "juu"), list);
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
    script.eval(context);
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
    script.eval(context);
    assertEquals("3", context.getBinding("a"));
  }

  public void testWhile() throws Exception {
    Script script = new Script(
        "i=0\n" +
        "while (( $i <= 5 ))\n" +
        "do\n" +
        "i=$((i+1))\n" +
        "done");
    script.prettyPrint();
    Context context = new TestableContext();
    script.eval(context);
    Object i = context.getBinding("i");
    assertEquals("6", i);
  }

  public void testCFOR() throws Exception {
    Script script = new Script(
        "for((i=1;i<=5;i++))\n" +
        "do\n" +
        "j=$((i))\n" +
        "done");
    Context context = new TestableContext();
    script.eval(context);
    assertEquals("5", context.getBinding("j"));
    assertEquals(6, context.getBinding("i"));
    script.eval(context);
    assertEquals("5", context.getBinding("j"));
    assertEquals(6, context.getBinding("i"));
  }

  public void testIF_STATEMENT() throws Exception {
    String[] scripts = {
        "if [ $t -gt 0 ]\n" +
        "then\n" +
        "a=was_if\n" +
        "fi",
        "if test $t -gt 0\n" +
        "then\n" +
        "a=was_if\n" +
        "fi"
    };
    for (String script : scripts) {
      Context context1 = new TestableContext();
      context1.setBinding("t", 1);
      new Script(script).eval(context1);
      assertEquals("was_if", context1.getBinding("a"));
      Context context2 = new TestableContext();
      context2.setBinding("t", -1);
      new Script(script).eval(context2);
      assertEquals(null, context2.getBinding("a"));
    }
  }

  public void testIF_STATEMENT_ELSE() throws Exception {
    String[] scripts = {
        "if [ $t -gt 0 ]\n" +
        "then\n" +
        "a=was_if\n" +
        "else\n" +
        "a=was_else\n" +
        "fi",
        "if test $t -gt 0\n" +
        "then\n" +
        "a=was_if\n" +
        "else\n" +
        "a=was_else\n" +
        "fi",
    };
    for (String script : scripts) {
      Context context1 = new TestableContext();
      context1.setBinding("t", 1);
      new Script(script).eval(context1);
      assertEquals("was_if", context1.getBinding("a"));
      Context context2 = new TestableContext();
      context2.setBinding("t", -1);
      new Script(script).eval(context2);
      assertEquals("was_else", context2.getBinding("a"));
    }
  }

  public void testNesting() throws Exception {
    Script script = new Script(
        "for((i=1;i<=5;i++))\n" +
        "do\n" +
        "echo $i\n" +
        "done");
    TestableContext context = new TestableContext(new BaseContext.CommandResolver() {
      @Override
      public Callable<?> resolveCommand(String command, final List<String> parameters, InputStream standardInput, final OutputStream standardOutput) {
        if ("echo".equals(command)) {
          return new Callable<Object>() {
            @Override
            public Object call() throws Exception {
              PrintWriter writer = new PrintWriter(standardOutput, true);
              for (String parameter : parameters) {
                writer.print(parameter);
              }
              writer.flush();
              return null;
            }
          };
        } else {
          return null;
        }
      }
    });
    script.eval(context);
    assertEquals("12345", context.getStandardOutput());
  }
}
