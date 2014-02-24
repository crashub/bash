package org.crashub.bash;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.crashub.bash.ir.CFOR;
import org.crashub.bash.ir.COMPOUND_COND;
import org.crashub.bash.ir.Command;
import org.crashub.bash.ir.Comparator;
import org.crashub.bash.ir.Expression;
import org.crashub.bash.ir.FunctionNode;
import org.crashub.bash.ir.IF_STATEMENT;
import org.crashub.bash.ir.LIST;
import org.crashub.bash.ir.Node;
import org.crashub.bash.ir.PIPE;
import org.crashub.bash.ir.STRING;
import org.crashub.bash.ir.SetLocal;
import org.crashub.bash.ir.WHILE;
import org.crashub.bash.spi.Context;
import org.crashub.bash.spi.Scope;
import org.gentoo.libbash.java_libbashLexer;
import org.gentoo.libbash.java_libbashParser;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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

  /**
   * @return the script intermediate representation
   */
  public Node toIR() {
    switch (tree.getType()) {
      case java_libbashParser.LIST:
        return _LIST(tree);
      default:
        throw unsupported(tree);
    }
  }

  /**
   * Evaluate the script.
   *
   * @param bindings the global bindings
   * @param context the evaluation context
   * @return the evaluated value
   */
  public Object eval(Scope bindings, Context context) {
    return toIR().eval(bindings, context);
  }

  static UnsupportedOperationException unsupported(Tree tree) {
    StringWriter msg = new StringWriter();
    msg.append("Tree ").append(constants.get(tree.getType())).append(" not yet implemented:\n");
    PrintWriter writer = new PrintWriter(msg);
    printTree(writer, "", tree);
    writer.flush();
    UnsupportedOperationException x = new UnsupportedOperationException(msg.toString());
    StackTraceElement[] t1 = x.getStackTrace();
    StackTraceElement[] t2 = new StackTraceElement[t1.length - 1];
    System.arraycopy(t1, 1, t2, 0, t2.length);
    x.setStackTrace(t2);
    return x;
  }

  static Tree assertTree(Tree tree, int... types) {
    for (int type : types) {
      if (tree.getType() == type) {
        return tree;
      }
    }
    throw unsupported(tree);
  }

  private CFOR _CFOR(final Tree tree) {
    final Tree initTree = assertTree(tree.getChild(0), java_libbashParser.FOR_INIT);
    final Tree condTree = assertTree(tree.getChild(1), java_libbashParser.FOR_COND);
    final Tree bodyTree = assertTree(tree.getChild(2), java_libbashParser.LIST);
    final Tree modTree = assertTree(tree.getChild(3), java_libbashParser.FOR_MOD);
    Expression<?> init = _ARITHMETIC(assertTree(initTree.getChild(0), java_libbashParser.ARITHMETIC));
    Expression<?> mod = _ARITHMETIC(modTree.getChild(0));
    Expression<?> test = _ARITHMETIC(condTree.getChild(0));
    LIST body = _LIST(bodyTree);
    return new CFOR(body, init, mod, test);
  }

  private WHILE _WHILE(final Tree tree) {
    final Tree condition = assertTree(tree.getChild(0), java_libbashParser.LIST);
    final Tree body = tree.getChild(1);
    final LIST cond = _LIST(condition);
    LIST b = _LIST(body);
    return new WHILE(b, cond);
  }

  private static Expression.Assign _EQUALS(Tree tree) {
    Tree lhsTree = assertTree(tree.getChild(0), java_libbashParser.LETTER, java_libbashParser.NAME);
    final Tree rhsTree = tree.getChild(1);
    String identifier = lhsTree.getText();
    Node rhs;
    switch (rhsTree.getType()) {
      case java_libbashParser.STRING: {
        rhs = _STRING(rhsTree);
        break;
      }
      case java_libbashParser.DIGIT: {
        rhs = evalExpression(rhsTree);
        break;
      }
      default:
        throw unsupported(rhsTree);
    }
    return new Expression.Assign(identifier, rhs);
  }

  private Node _VARIABLE_DEFINITIONS(Tree tree) {
    final Tree child = tree.getChild(0);
    if (child.getType() == java_libbashParser.EQUALS) {
      return _EQUALS(child);
    } else {
      throw unsupported(tree);
    }
  }

  static Expression.VarRef _VAR_REF(Tree tree) {
    Tree child = tree.getChild(0);
    int childType = child.getType();
    switch (childType) {
      case java_libbashParser.LETTER:
      case java_libbashParser.NAME:
      case java_libbashParser.DIGIT:
      case java_libbashParser.NUMBER:
      case java_libbashParser.POUND:
        return new Expression.VarRef.Eval(child.getText());
      case java_libbashParser.DISPLAY_ERROR_WHEN_UNSET_OR_NULL:
      case java_libbashParser.DISPLAY_ERROR_WHEN_UNSET:
      case java_libbashParser.ASSIGN_DEFAULT_WHEN_UNSET:
      case java_libbashParser.ASSIGN_DEFAULT_WHEN_UNSET_OR_NULL:
      case java_libbashParser.USE_DEFAULT_WHEN_UNSET_OR_NULL:
      case java_libbashParser.USE_ALTERNATE_WHEN_UNSET:
        return new Expression.VarRef.Default(
            child.getChild(0).getText(),
            child.getType(),
            _STRING(child.getChild(1)));
      default:
        throw unsupported(child);
    }
  }

  static Expression<?> _ARITHMETIC_EXPRESSION(Tree tree) {
    Tree arithmetic = assertTree(tree.getChild(0), java_libbashParser.ARITHMETIC);
    return _ARITHMETIC(arithmetic);
  }

  private static Expression<?> _ARITHMETIC(Tree tree) {
    assertTree(tree, java_libbashParser.ARITHMETIC);
    return evalExpression(tree.getChild(0));
  }

  private static Expression evalExpression(Tree tree) {
    switch (tree.getType()) {
      case java_libbashParser.PLUS:
      case java_libbashParser.MINUS:
      case java_libbashParser.TIMES:
      case java_libbashParser.SLASH:
      case java_libbashParser.PCT:
      case java_libbashParser.LESS_THAN:
      case java_libbashParser.LEQ:
      case java_libbashParser.GEQ:
      case java_libbashParser.EQUALS_TO:
      case java_libbashParser.NOT_EQUALS:
      case java_libbashParser.GREATER_THAN: {
        Tree leftTree = tree.getChild(0);
        Tree rightTree = tree.getChild(1);
        Expression<?> left = evalExpression(leftTree);
        Expression<?> right = evalExpression(rightTree);
        return new Expression.Binary(left, right, tree.getType());
      }
      case java_libbashParser.PRE_INCR:
      case java_libbashParser.PRE_DECR:
      case java_libbashParser.POST_INCR:
      case java_libbashParser.POST_DECR: {
        Tree exprTree = tree.getChild(0);
        Expression.VarRef varRef = _VAR_REF(exprTree);
        return new Expression.Unary(varRef, tree.getType());
      }
      case java_libbashParser.VAR_REF: {
        return _VAR_REF(tree);
      }
      case java_libbashParser.NUMBER:
      case java_libbashParser.DIGIT: {
        return new Expression.Literal(Integer.parseInt(tree.getText()));
      }
      case java_libbashParser.EQUALS: {
        return _EQUALS(tree);
      }
      default:
        throw unsupported(tree);
    }
  }

  // Utility class for creating STRING
  private static class STRINGBuilder extends LinkedList<Object> {

    void append(Tree tree) {
      Object o;
      switch (tree.getType()) {
        case java_libbashParser.DIGIT:
          o = Integer.parseInt(tree.getText());
          break;
        case java_libbashParser.ARITHMETIC_EXPRESSION:
          o = Script._ARITHMETIC_EXPRESSION(tree);
          break;
        case java_libbashParser.PLUS:
          o = "+";
          break;
        case java_libbashParser.MINUS:
          // Shoud we handle options there ?
          o = "-";
          break;
        case java_libbashParser.VAR_REF:
          o = Script._VAR_REF(tree);
          break;
        case java_libbashParser.LETTER:
        case java_libbashParser.NAME:
        case java_libbashParser.BLANK:
          o = tree.getText();
          break;
        case java_libbashParser.SINGLE_QUOTED_STRING: {
          Tree token = assertTree(tree.getChild(0), java_libbashParser.SINGLE_QUOTED_STRING_TOKEN);
          String value = token.getText();
          o = value.substring(1, value.length() - 1);
          break;
        }
        default:
          throw Script.unsupported(tree);
      }
      add(o);
    }

    STRING toSTRING() {
      return new STRING(toArray());
    }
  }

  private Node _COMMAND(Tree tree) {
    assertTree(tree, java_libbashParser.COMMAND);
    final Tree child = tree.getChild(0);
    switch (child.getType()) {
      case java_libbashParser.STRING: {
        if (child.getChild(0).getType() == java_libbashParser.LOCAL) {
          // Special case for local variables
          // that seems weird
          Tree t = tree.getChild(1);
          String identifier = assertTree(t.getChild(0), java_libbashParser.NAME, java_libbashParser.LETTER).getText();
          if (t.getChildCount() > 1) {
            // Local + Assign
            assertTree(t.getChild(1), java_libbashParser.EQUALS);
            Expression expr = evalExpression(t.getChild(2));
            return new SetLocal(identifier, new Expression.Assign(identifier, expr));
          } else {
            // Local only
            return new SetLocal(identifier);
          }
        } else {
          STRING command;
          List<STRING> parameters;
          if (child.getChild(0).getType() == java_libbashParser.DECLARE) {
            command = new STRING("declare");
            if (tree.getChildCount() > 1) {
              Tree s = assertTree(tree.getChild(1), java_libbashParser.STRING);
              parameters = new ArrayList<STRING>();
              STRINGBuilder builder = new STRINGBuilder();
              for (int i = 0;i < s.getChildCount();i++) {
                Tree e = s.getChild(i);
                if (e.getType() == java_libbashParser.BLANK) {
                  if (!builder.isEmpty()) {
                    parameters.add(builder.toSTRING());
                    builder.clear();
                  }
                } else {
                  builder.add(e);
                }
              }
              if (!builder.isEmpty()) {
                parameters.add(builder.toSTRING());
              }
            } else {
              parameters = Collections.emptyList();
            }
          } else {
            command = _STRING(child);
            int childCount = tree.getChildCount();
            if (childCount > 1) {
              parameters = new ArrayList<STRING>(childCount - 1);
              for (int index = 1;index < childCount;index++) {
                parameters.add(_STRING(tree.getChild(index)));
              }
            } else {
              parameters = Collections.emptyList();
            }
          }
          return new Command(command, parameters);
        }
      }
      case java_libbashParser.VARIABLE_DEFINITIONS:
        return _VARIABLE_DEFINITIONS(child);
      case java_libbashParser.WHILE:
        return _WHILE(child);
      case java_libbashParser.CFOR:
        return _CFOR(child);
      case java_libbashParser.IF_STATEMENT:
        return _IF_STATEMENT(child);
      case java_libbashParser.ARITHMETIC_EXPRESSION:
        return _ARITHMETIC_EXPRESSION(child);
      case java_libbashParser.COMPOUND_COND:
        return _COMPOUND_COND(child);
      case java_libbashParser.FUNCTION:
        return _FUNCTION(child);
      default:
        throw unsupported(child);
    }
  }

  private Node _FUNCTION(Tree tree) {
    assertTree(tree, java_libbashParser.FUNCTION);
    String name = STRING_NAME(tree.getChild(0));
    Tree currentShell = assertTree(tree.getChild(1), java_libbashParser.CURRENT_SHELL);
    Tree list = currentShell.getChild((0));
    LIST body = _LIST(list);
    return new FunctionNode(name, body);
  }

  private static String STRING_NAME(Tree tree) {
    assertTree(tree, java_libbashParser.STRING);
    Tree child = tree.getChild(0);
    switch (child.getType()) {
      case java_libbashParser.LETTER:
      case java_libbashParser.NAME:
        return child.getText();
      default:
        throw unsupported(child);
    }
  }

  private static STRING _STRING(Tree tree) {
    Script.assertTree(tree, java_libbashParser.STRING);
    STRINGBuilder builder = new STRINGBuilder();
    for (int i = 0;i < tree.getChildCount();i++) {
      Tree child = tree.getChild(i);
      builder.append(child);
    }
    return builder.toSTRING();
  }

  private Node _COMPOUND_COND(Tree tree) {
    assertTree(tree, java_libbashParser.COMPOUND_COND);
    Tree test = assertTree(tree.getChild(0), java_libbashParser.BUILTIN_TEST);
    Tree testName = assertTree(test.getChild(0), java_libbashParser.NAME);
    final String testType = testName.getText();
    final STRING left = _STRING(testName.getChild(0));
    final STRING right = _STRING(testName.getChild(1));
    Comparator comparator;
    if (testType.equals("gt")) {
      comparator = Comparator.gt;
    } else {
      throw new UnsupportedOperationException("Test type " + testType + " not implemented");
    }
    return new COMPOUND_COND(comparator, left, right);
  }

  private IF_STATEMENT _IF_STATEMENT(final Tree tree) {
    final Tree if_ = assertTree(tree.getChild(0), java_libbashParser.IF);
    final LIST cond = _LIST(if_.getChild(0));
    final LIST then = _LIST(if_.getChild(1));
    Tree child = tree.getChild(1);
    final LIST bilto;
    if (child != null) {
      Tree else_ = assertTree(child, java_libbashParser.ELSE);
      Tree else_list = assertTree(else_.getChild(0), java_libbashParser.LIST);
      bilto = _LIST(else_list);
    } else {
      bilto = null;
    }
    return new IF_STATEMENT(cond, then, bilto);
  }

  LIST _LIST(Tree tree) {
    assertTree(tree, java_libbashParser.LIST);
    int count = tree.getChildCount();
    Node[] blocks = new Node[count];
    for (int index = 0; index < count;index++) {
      final Tree child = tree.getChild(index);
      Node block;
      switch (child.getType()) {
        case java_libbashParser.COMMAND: {
          block = _COMMAND(child);
          break;
        }
        case java_libbashParser.PIPE: {
          final List<Node> pipeline = _PIPE(child);
          block = new PIPE(pipeline);
          break;
        }
        default:
          throw unsupported(child);
      }
      blocks[index] = block;
    }
    return new LIST(blocks);
  }

  private List<Node> _PIPE(Tree tree) {
    assertTree(tree, java_libbashParser.PIPE);
    LinkedList<Node> ret = new LinkedList<Node>();
    Tree rest = tree.getChild(0);
    switch (rest.getType()) {
      case java_libbashParser.COMMAND:
        ret.add(_COMMAND(rest));
        break;
      case java_libbashParser.PIPE: {
        ret.addAll(_PIPE(rest));
        break;
      }
      default:
        throw unsupported(rest);
    }
    Node last = _COMMAND(tree.getChild(1));
    ret.add(last);
    return ret;
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

  static void printTree(PrintWriter writer, String padding, Tree tree) {
    writer.print(padding);
    writer.print("tree: ");
    String type = constants.get(tree.getType());
    if (type == null) {
      type = Integer.toString(tree.getType());
    }
    writer.print(type);
    writer.print(": ");
    writer.println(tree.getText());
    padding += "  ";
    for (int i = 0;i < tree.getChildCount();i++) {
      printTree(writer, padding, tree.getChild(i));
    }
  }
}
