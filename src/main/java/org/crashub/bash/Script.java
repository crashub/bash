package org.crashub.bash;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.gentoo.libbash.java_libbashLexer;
import org.gentoo.libbash.java_libbashParser;

import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * @author Julien Viet
 */
public class Script {

  /** . */
  private static final HashMap<Integer, String> constants = new HashMap<Integer, String>();

  static {
    for (Field f : java_libbashParser.class.getDeclaredFields()) {
      if (f.getType() == int.class) {
        int mod = f.getModifiers();
        if (Modifier.isPublic(mod) && Modifier.isFinal(mod) && Modifier.isStatic(mod)) {
          try {
            Integer i = (Integer)f.get(null);
            constants.put(i, f.getName());
          }
          catch (IllegalAccessException e) {
          }
        }
      }
    }
  }

  private java_libbashParser parser(String s) {
    java_libbashLexer lexer = new java_libbashLexer(new ANTLRStringStream(s));
    return new java_libbashParser(new CommonTokenStream(lexer));
  }

  /** . */
  private final Tree tree;

  public Script(String s) throws RecognitionException {
    tree = (Tree)parser(s).start().getTree();
  }

  public Context execute() {
    Context context = new Context();
    execute(tree, context);
    return context;
  }

  private UnsupportedOperationException unsupported(Tree tree) {
    UnsupportedOperationException x = new UnsupportedOperationException("Tree " + constants.get(tree.getType()) + " not yet implemented");
    StackTraceElement[] t1 = x.getStackTrace();
    StackTraceElement[] t2 = new StackTraceElement[t1.length - 1];
    System.arraycopy(t1, 1, t2, 0, t2.length);
    x.setStackTrace(t2);
    return x;
  }

  private Tree assertTree(Tree tree, int type) {
    if (tree.getType() == type) {
      return tree;
    } else {
      throw new UnsupportedOperationException("Was not expected tree " + constants.get(tree.getType()));
    }
  }

  private Tree assertTree(Tree tree, int... types) {
    for (int type : types) {
      if (tree.getType() == type) {
        return tree;
      }
    }
    throw new UnsupportedOperationException("Was not expected tree " + constants.get(tree.getType()));
  }

  private Object execute(Tree tree, Context context) {
    switch (tree.getType()) {
      case java_libbashParser.LIST:
        return _LIST(tree, context);
      case java_libbashParser.COMMAND:
        return _COMMAND(tree, context);
      case java_libbashParser.VARIABLE_DEFINITIONS:
        _VARIABLE_DEFINITIONS(tree, context);
        return null;
      case java_libbashParser.WHILE:
        _WHILE(tree, context);
        return null;
      case java_libbashParser.ARITHMETIC_EXPRESSION:
        return _ARITHMETIC_EXPRESSION(tree, context);
      default:
        throw unsupported(tree);
    }
  }

  private void _WHILE(Tree tree, Context context) {
    Tree condition = assertTree(
        tree.getChild(0),
        java_libbashParser.LIST).getChild(0);
    while (true) {
      Object value = _LIST(condition, context);
      if (value instanceof Boolean) {
        if (!(Boolean)value) {
          break;
        } else {
          Tree body = tree.getChild(1);
          execute(body, context);
        }
      } else {
        throw new UnsupportedOperationException("Not implemented");
      }
    }
  }

  private void _VARIABLE_DEFINITIONS(Tree tree, Context context) {
    Tree child = tree.getChild(0);
    if (child.getType() == java_libbashParser.EQUALS) {
      Tree lhs = assertTree(child.getChild(0), java_libbashParser.LETTER);
      Tree rhs = assertTree(child.getChild(1), java_libbashParser.STRING);
      String name = lhs.getText();
      Object value = _STRING(rhs.getChild(0), context);
      context.bindings.put(name, value);
    } else {
      throw unsupported(tree);
    }
  }

  private Object _STRING(Tree tree, Context context) {
    switch (tree.getType()) {
      case java_libbashParser.DIGIT:
        return Integer.parseInt(tree.getText());
      case java_libbashParser.ARITHMETIC_EXPRESSION:
        return _ARITHMETIC_EXPRESSION(tree, context);
      default:
        throw unsupported(tree);
    }
  }

  private Object _ARITHMETIC_EXPRESSION(Tree tree, Context context) {
    Tree arithmetic = assertTree(tree.getChild(0), java_libbashParser.ARITHMETIC);
    Tree expression = assertTree(
        arithmetic.getChild(0),
        java_libbashParser.PLUS,
        java_libbashParser.VAR_REF,
        java_libbashParser.LEQ,
        java_libbashParser.DIGIT);
    return evalExpression(expression, context);
  }

  private Object evalExpression(Tree tree, Context context) {
    switch (tree.getType()) {
      case java_libbashParser.PLUS: {
        Tree leftTree = tree.getChild(0);
        Tree rightTree = tree.getChild(1);
        Object left = evalExpression(leftTree, context);
        Object right = evalExpression(rightTree, context);
        if (left instanceof Integer && right instanceof Integer) {
          return ((Integer)left + (Integer)right);
        } else {
          throw new UnsupportedOperationException("Not implemented");
        }
      }
      case java_libbashParser.VAR_REF: {
        Tree ref = assertTree(tree.getChild(0), java_libbashParser.LETTER);
        return context.bindings.get(ref.getText());
      }
      case java_libbashParser.DIGIT: {
        return Integer.parseInt(tree.getText());
      }
      case java_libbashParser.LEQ: {
        Tree leftTree = tree.getChild(0);
        Tree rightTree = tree.getChild(1);
        Object left = evalExpression(leftTree, context);
        Object right = evalExpression(rightTree, context);
        if (left instanceof Integer && right instanceof Integer) {
          return ((Integer)left <= (Integer)right);
        } else {
          throw new UnsupportedOperationException("Not implemented");
        }
      }
      default:
        throw unsupported(tree);
    }
  }

  private Object _COMMAND(Tree tree, Context context) {
    return execute(tree.getChild(0), context);
  }

  private Object _LIST(Tree tree, Context context) {
    int count = tree.getChildCount();
    Object last = null;
    for (int index = 0; index < count;index++) {
      Tree child = tree.getChild(index);
      last = execute(child, context);
    }
    return last;
  }

  public void prettyPrint() {
    prettyPrint(new PrintWriter(System.out, true));
  }

  public void prettyPrint(Writer out) {
    prettyPrint(new PrintWriter(out, true));
  }

  public void prettyPrint(PrintWriter writer) {
    printTree(writer, "", tree);
  }

  void printTree(PrintWriter writer, String padding, Tree tree) {
    writer.print(padding);
    writer.print("tree: ");
    String type = constants.get(tree.getType());
    if (type == null) {
      type = Integer.toString(tree.getType());
    }
    writer.println(type);
    padding += "  ";
    for (int i = 0;i < tree.getChildCount();i++) {
      printTree(writer, padding, tree.getChild(i));
    }
  }
}
