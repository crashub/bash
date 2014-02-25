package org.crashub.bash;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import org.antlr.runtime.RecognitionException;
import org.crashub.bash.spi.BaseContext;
import org.crashub.bash.spi.Scope;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Julien Viet
 */
public class TestScript extends TestCase {

  private Object expression(String s) throws RecognitionException {
    return new Shell().expression(s);
  }

  public void testCommand() throws Exception {
    final AtomicInteger count = new AtomicInteger();
    Object ret = new Shell().command("foo", new BaseContext.Command() {
      @Override
      public Object execute(BaseContext context, Scope bindings, List<String> parameters, InputStream standardInput, OutputStream standardOutput) {
        count.incrementAndGet();
        return "foo_value";
      }
    }).eval("foo");
    assertEquals("foo_value", ret);
  }

  public void testCommandWithArgument() throws Exception {
    final AtomicInteger count = new AtomicInteger();
    Object ret = new Shell().command("foo", new BaseContext.Command() {
      @Override
      public Object execute(BaseContext context, Scope bindings, List<String> parameters, InputStream standardInput, OutputStream standardOutput) {
        if (Arrays.asList("bar").equals(parameters)) {
          count.incrementAndGet();
          return "foo_value";
        }
        else {
          throw new AssertionFailedError();
        }
      }
    }).eval("foo bar");
    assertEquals("foo_value", ret);
  }

  public void testPipeline() throws Exception {
    final ArrayList<String> list = new ArrayList<String>();
    class CommandImpl implements BaseContext.Command {
      final String name;
      CommandImpl(String name) {
        this.name = name;
      }
      public Object execute(BaseContext context, Scope bindings, List<String> parameters, InputStream standardInput, OutputStream standardOutput) {
        list.add(name);
        return null;
      }
    }
    CommandImpl foo = new CommandImpl("foo");
    CommandImpl bar = new CommandImpl("bar");
    CommandImpl juu = new CommandImpl("juu");
    Shell shell = new Shell().command("foo", foo).command("bar", bar).command("juu", juu);
    shell.eval("foo | bar");
    assertEquals(Arrays.asList("foo", "bar"), list);
    list.clear();
    shell.eval("foo | bar | juu");
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
    Shell shell = new Shell();
    shell.eval("i=3\n");
    Object i = shell.getBinding("i");
    assertEquals("3", i);
  }

  public void testUnsetVariableEvaluation() throws Exception {
    Script script = new Script("i=$does_not_exist\n");
    Shell shell = new Shell();
    shell.eval(script);
    assertEquals("", shell.getBinding("i"));
  }

  public void testARITHMETIC_EXPRESSION_LESS_THAN() throws Exception {
    assertEquals("0", expression("$((2<2))\n"));
    assertEquals("1", expression("$((2<3))\n"));
    assertEquals("0", expression("$((3<2))\n"));
  }

  public void testARITHMETIC_EXPRESSION_LEQ() throws Exception {
    assertEquals("1", expression("$((2<=2))\n"));
    assertEquals("1", expression("$((2<=3))\n"));
    assertEquals("0", expression("$((3<=2))\n"));
  }

  public void testARITHMETIC_EXPRESSION_EQUALS_TO() throws Exception {
    assertEquals("1", expression("$((2==2))\n"));
    assertEquals("0", expression("$((2==3))\n"));
    assertEquals("0", expression("$((3==2))\n"));
  }

  public void testARITHMETIC_EXPRESSION_EQUALS_NOT_EQUALS() throws Exception {
    assertEquals("0", expression("$((2!=2))\n"));
    assertEquals("1", expression("$((2!=3))\n"));
    assertEquals("1", expression("$((3!=2))\n"));
  }

  public void testARITHMETIC_EXPRESSION_GREATER_THAN() throws Exception {
    assertEquals("0", expression("$((2>2))\n"));
    assertEquals("0", expression("$((2>3))\n"));
    assertEquals("1", expression("$((3>2))\n"));
  }

  public void testARITHMETIC_EXPRESSION_GEQ() throws Exception {
    assertEquals("1", expression("$((2>=2))\n"));
    assertEquals("0", expression("$((2>=3))\n"));
    assertEquals("1", expression("$((3>=2))\n"));
  }

  public void testARITHMETIC_EXPRESSION_PLUS() throws Exception {
    assertEquals("5", expression("$((2+3))\n"));
  }

  public void testARITHMETIC_EXPRESSION_MINUS() throws Exception {
    assertEquals("1", expression("$((3-2))\n"));
  }

  public void testARITHMETIC_EXPRESSION_TIMES() throws Exception {
    assertEquals("6", expression("$((3*2))\n"));
  }

  public void testARITHMETIC_EXPRESSION_SLASH() throws Exception {
    assertEquals("2", expression("$((4/2))\n"));
  }

  public void testARITHMETIC_EXPRESSION_PCT() throws Exception {
    assertEquals("1", expression("$((5%2))\n"));
  }

  public void testARITHMETIC_EXPRESSION_PRE_INCR() throws Exception {
    Shell shell1 = new Shell().bind("x", "5");
    assertEquals("6", shell1.expression("$((++x))\n"));
    assertEquals(6, shell1.getBinding("x"));
    Shell shell2 = new Shell();
    assertEquals("1", shell2.expression("$((++x))\n"));
    assertEquals(1, shell2.getBinding("x"));
  }

  public void testARITHMETIC_EXPRESSION_PRE_DECR() throws Exception {
    Shell shell1 = new Shell().bind("x", "5");
    assertEquals("4", shell1.expression("$((--x))\n"));
    assertEquals(4, shell1.getBinding("x"));
    Shell shell2 = new Shell();
    assertEquals("-1", shell2.expression("$((--x))\n"));
    assertEquals(-1, shell2.getBinding("x"));
  }

  public void testARITHMETIC_EXPRESSION_POST_INCR() throws Exception {
    Shell shell1 = new Shell().bind("x", "5");
    assertEquals("5", shell1.expression("$((x++))\n"));
    assertEquals(6, shell1.getBinding("x"));
    Shell shell2 = new Shell();
    assertEquals("0", shell2.expression("$((x++))\n"));
    assertEquals(1, shell2.getBinding("x"));
  }

  public void testARITHMETIC_EXPRESSION_POST_DECR() throws Exception {
    Shell shell1 = new Shell().bind("x", "5");
    assertEquals("5", shell1.expression("$((x--))\n"));
    assertEquals(4, shell1.getBinding("x"));
    Shell shell2 = new Shell();
    assertEquals("0", shell2.expression("$((x--))\n"));
    assertEquals(-1, shell2.getBinding("x"));
  }

  public void testSTRING() throws Exception {
    assertEquals("2+3", expression("2+3\n"));
    assertEquals("foo", expression("'foo'\n"));
    Shell shell = new Shell().bind("abc", "def");
    assertEquals("def", shell.expression("${abc}"));
    assertEquals("${abc}", shell.expression("'${abc}'"));
  }

  public void testDISPLAY_ERROR_WHEN_UNSET() throws Exception {
    assertEquals("def", new Shell().bind("abc", "def").expression("${abc?does not exist}"));
    assertEquals("", new Shell().bind("abc", "").expression("${abc?does not exist}"));
    try {
      expression("${abc?does not exist}");
      fail();
    }
    catch (RuntimeException expected) {
      assertEquals("does not exist", expected.getMessage());
    }
  }

  public void testDISPLAY_ERROR_WHEN_UNSET_OR_NULL() throws Exception {
    assertEquals("def", new Shell().bind("abc", "def").expression("${abc:?does not exist}"));
    try {
      new Shell().bind("abc", "").expression("${abc:?does not exist}");
      fail();
    }
    catch (RuntimeException expected) {
      assertEquals("does not exist", expected.getMessage());
    }
    try {
      expression("${abc:?does not exist}");
      fail();
    }
    catch (RuntimeException expected) {
      assertEquals("does not exist", expected.getMessage());
    }
  }

  public void testASSIGN_DEFAULT_WHEN_UNSET() throws Exception {
    Shell shell1 = new Shell();
    assertEquals("ghi", shell1.expression("${abc=ghi}"));
    assertEquals("ghi", shell1.getBinding("abc"));
    Shell shell2 = new Shell().bind("abc", "def");
    assertEquals("def", shell2.expression("${abc=ghi}"));
    assertEquals("def", shell2.getBinding("abc"));
    Shell shell3 = new Shell().bind("abc", "");
    assertEquals("", shell3.expression("${abc=ghi}"));
    assertEquals("", shell3.getBinding("abc"));
  }

  public void testUSE_DEFAULT_WHEN_UNSET_OR_NULL() throws Exception {
    Shell shell1 = new Shell();
    assertEquals("ghi", shell1.expression("${abc:-ghi}"));
    assertEquals(null, shell1.getBinding("abc"));
    Shell shell2 = new Shell().bind("abc", "def");
    assertEquals("def", shell2.expression("${abc:-ghi}"));
    assertEquals("def", shell2.getBinding("abc"));
    Shell shell3 = new Shell().bind("abc", "");
    assertEquals("ghi", shell3.expression("${abc:-ghi}"));
    assertEquals("", shell3.getBinding("abc"));
  }

  public void testASSIGN_DEFAULT_WHEN_UNSET_OR_NULL() throws Exception {
    Shell shell1 = new Shell();
    assertEquals("ghi", shell1.expression("${abc:=ghi}"));
    assertEquals("ghi", shell1.getBinding("abc"));
    Shell shell2 = new Shell().bind("abc", "def");
    assertEquals("def", shell2.expression("${abc:=ghi}"));
    assertEquals("def", shell2.getBinding("abc"));
    Shell shell3 = new Shell().bind("abc", "");
    assertEquals("ghi", shell3.expression("${abc:=ghi}"));
    assertEquals("ghi", shell3.getBinding("abc"));
  }

  public void testUSE_ALTERNATE_WHEN_UNSET() throws Exception {
    Shell shell1 = new Shell();
    assertEquals("", shell1.expression("${abc+ghi}"));
    assertEquals(null, shell1.getBinding("abc"));
    Shell shell2 = new Shell().bind("abc", "def");
    assertEquals("ghi", shell2.expression("${abc+ghi}"));
    assertEquals("def", shell2.getBinding("abc"));
    Shell shell3 = new Shell().bind("abc", "");
    assertEquals("ghi", shell3.expression("${abc+ghi}"));
    assertEquals("", shell3.getBinding("abc"));
  }

  public void testVAR_REF() throws Exception {
    Shell shell = new Shell();
    shell.bind("i", "3");
    shell.eval("a=$((i))");
    assertEquals("3", shell.getBinding("a"));
  }

  public void testWhile() throws Exception {
    Shell shell = new Shell();
    shell.eval(
        "i=0\n" +
        "while (( $i <= 5 ))\n" +
        "do\n" +
        "i=$((i+1))\n" +
        "done");
    Object i = shell.getBinding("i");
    assertEquals("6", i);
  }

  public void testCFOR() throws Exception {
    String script =
        "for((i=1;i<=5;i++))\n" +
        "do\n" +
        "j=$((i))\n" +
        "done";
    Shell shell = new Shell();
    shell.eval(script);
    assertEquals("5", shell.getBinding("j"));
    assertEquals(6, shell.getBinding("i"));
    shell.eval(script);
    assertEquals("5", shell.getBinding("j"));
    assertEquals(6, shell.getBinding("i"));
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
      Shell shell1 = new Shell();
      shell1.bind("t", 1);
      shell1.eval(script);
      assertEquals("was_if", shell1.getBinding("a"));
      Shell shell2 = new Shell();
      shell2.bind("t", -1);
      shell2.eval(script);
      assertEquals(null, shell2.getBinding("a"));
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
      Shell shell1 = new Shell();
      shell1.bind("t", 1);
      shell1.eval(script);
      assertEquals("was_if", shell1.getBinding("a"));
      Shell shell2 = new Shell();
      shell2.bind("t", -1);
      shell2.eval(script);
      assertEquals("was_else", shell2.getBinding("a"));
    }
  }

  public void testNesting() throws Exception {
    Shell shell = new Shell().command("echo", new BaseContext.Command() {
      @Override
      public Object execute(BaseContext context, Scope bindings, List<String> parameters, InputStream standardInput, OutputStream standardOutput) {
        PrintWriter writer = new PrintWriter(standardOutput, true);
        for (String parameter : parameters) {
          writer.print(parameter);
        }
        writer.flush();
        return null;
      }
    });
    shell.eval(
        "for((i=1;i<=5;i++))\n" +
        "do\n" +
        "echo $i\n" +
        "done");
    assertEquals("12345", shell.getStandardOutput());
  }

  public void testFunction() throws Exception {
    Shell shell = new Shell().command("foo", new BaseContext.Command() {
      @Override
      public Object execute(BaseContext context, Scope bindings, List<String> parameters, InputStream standardInput, OutputStream standardOutput) {
        return "foo_value";
      }
    });
    shell.eval("hello() { foo; }");
    assertNotNull(shell.context.resolveFunction("hello"));
    assertEquals("foo_value", shell.eval("hello"));
  }

  public void testFunctionVariable() throws Exception {
    Shell shell = new Shell();
    shell.eval("hello() { f=5; }");
    shell.eval("hello");
    assertEquals("5", shell.getBinding("f"));
  }

  public void testFunctionLocalVariable() throws Exception {
    String[] scripts = {
        "hello() { local f=5; g=$f; }",
        "hello() { local f; f=5; g=$f; }",
        "hello() { local f; g=${f+5}; }"
    };
    for (String script : scripts) {
      Shell shell = new Shell();
      shell.eval(script);
      shell.eval("hello");
      assertEquals(null, shell.getBinding("f"));
      assertEquals("5", shell.getBinding("g"));
    }
  }

  public void testLocalVariable() throws Exception {
    Shell shell = new Shell();
    try {
      shell.eval("local a=0");
      fail();
    }
    catch (RuntimeException expected) {
    }
  }

  public void testFunctionShellVariable() throws Exception {
    Shell shell = new Shell();
    shell.eval("hello() { f=$1; g=$2; h=$#; }");
    shell.eval("hello world");
    assertEquals("world", shell.getBinding("f"));
    assertEquals("", shell.getBinding("g"));
    assertEquals("1", shell.getBinding("h"));
  }

  public void testParameters() throws Exception {
    Shell shell = new Shell();
    final List<String> params = new ArrayList<String>();
    shell.command("foo", new BaseContext.Command() {
      @Override
      public Object execute(BaseContext context, Scope bindings, List<String> parameters, InputStream standardInput, OutputStream standardOutput) {
        params.addAll(parameters);
        return null;
      }
    });
    shell.eval("foo bar juu");
    assertEquals(Arrays.asList("bar", "juu"), params);
  }

  public void testReadOnly() throws Exception {
    Shell shell = new Shell();
    shell.context.setReadOnly(shell.bindings, "foo", "foo_value");
    try {
      shell.eval("foo=bar");
      fail();
    }
    catch (RuntimeException e) {
    }
    assertEquals("foo_value", shell.getBinding("foo"));
  }

  // Many test to do for read only too
  // this is not complete

  /**
   * > foo=4;declare -i foo;echo $foo;unset foo
   * 4
   */
  public void testInteger1() throws Exception {
    Shell shell = new Shell();
    shell.eval("foo=4");
    shell.context.setInteger(shell.bindings, "foo", null);
    assertEquals("4", shell.expression("$foo"));
    assertEquals("4", shell.getBinding("foo"));
  }

  /**
   * > declare -i foo;echo $foo;unset foo
   */
  public void testInteger2() throws Exception {
    Shell shell = new Shell();
    shell.context.setInteger(shell.bindings, "foo", null);
    assertEquals("", shell.expression("$foo"));
    assertNull(shell.getBinding("foo"));
  }

  /**
   * > foo=bar;declare -i foo;echo $foo;foo=juu;echo $foo;foo=4;echo $foo;unset foo
   * bar
   * 0
   * 4
   */
  public void testInteger3() throws Exception {
    Shell shell = new Shell();
    shell.eval("foo=bar");
    shell.context.setInteger(shell.bindings, "foo", null);
    assertEquals("bar", shell.expression("$foo"));
    assertEquals("bar", shell.getBinding("foo"));
    shell.eval("foo=juu");
    assertEquals("0", shell.expression("$foo"));
    assertEquals(0, shell.getBinding("foo"));
    shell.eval(new Script("foo=4"));
    assertEquals("4", shell.expression("$foo"));
    assertEquals(4, shell.getBinding("foo"));
  }
}
