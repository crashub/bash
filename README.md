# Bash interpreter

A proof of concept to see how feasible a bash interpreter for the JVM would/could be.

# Current status

Very early prototype built on top of the libbash ANTLR grammar, aims to implement basic stuff. The grammar
provides an AST.

# Test drive

Interactive repl can trivially be started:

    mvn test -Prepl
    ...
    > a=4
    > echo $a
    4
    > echo $((a + 4))
    8

Anything that does not work provide the abstract syntax tree portion not implemented:

    > ls | more
    java.lang.UnsupportedOperationException: Tree PIPE not yet implemented:
    tree: PIPE
      tree: COMMAND
        tree: STRING
          tree: NAME
      tree: COMMAND
        tree: STRING
          tree: NAME

    	at org.crashub.bash.Script._LIST(Script.java:417)
    	at org.crashub.bash.Script.execute(Script.java:64)
    	at org.crashub.bash.Repl.main(Repl.java:40)
    	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
    	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
    	at java.lang.reflect.Method.invoke(Method.java:606)
    	at org.codehaus.mojo.exec.ExecJavaMojo$1.run(ExecJavaMojo.java:297)
    	at java.lang.Thread.run(Thread.java:744)

# Implementing missing features

We use _test driven development_ for implementing the interpreter.

## Add a test that make the execution fail

    public void testARITHMETIC_EXPRESSION_LESS_THAN() throws Exception {
      assertEquals("0", eval("$((2<2))\n"));
      assertEquals("1", eval("$((2<3))\n"));
      assertEquals("0", eval("$((3<2))\n"));
    }

## Execute the tests

    java.lang.UnsupportedOperationException: Tree LESS_THAN not yet implemented:
    tree: LESS_THAN
      tree: DIGIT
      tree: DIGIT
            at org.crashub.bash.Script.assertTree(Script.java:90)
            at org.crashub.bash.Script._ARITHMETIC_EXPRESSION(Script.java:234)
            at org.crashub.bash.Script._STRING(Script.java:143)
            at org.crashub.bash.Script._VARIABLE_DEFINITIONS(Script.java:125)
            at org.crashub.bash.Script._COMMAND(Script.java:386)
            at org.crashub.bash.Script._LIST(Script.java:403)
            at org.crashub.bash.Script.execute(Script.java:64)
            at org.crashub.bash.TestScript.eval(TestScript.java:24)
            at org.crashub.bash.TestScript.eval(TestScript.java:19)
            at org.crashub.bash.TestScript.testARITHMETIC_EXPRESSION_LESS_THAN(TestScript.java:88)

## Implement the missing code

    case java_libbashParser.LESS_THAN:
      return l < r ? 1 : 0;

# Wanna help ?

Sure there are plenty of things to do, get in touch with me