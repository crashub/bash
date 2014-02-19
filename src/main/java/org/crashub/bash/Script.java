package org.crashub.bash;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.crashub.bash.spi.*;
import org.crashub.bash.spi.Process;
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

  public Object execute(Context context) {
    switch (tree.getType()) {
      case java_libbashParser.LIST:
        return _LIST(tree, context);
      default:
        throw unsupported(tree);
    }
  }

  private UnsupportedOperationException unsupported(Tree tree) {
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

  private Tree assertTree(Tree tree, int... types) {
    for (int type : types) {
      if (tree.getType() == type) {
        return tree;
      }
    }
    throw unsupported(tree);
  }

  // Base class for loops
  private abstract class Loop extends Block {
    final Context context;
    final Tree body;
    protected Loop(Context context, Tree body) {
      this.context = context;
      this.body = body;
    }
    protected abstract Object test();
    @Override
    public org.crashub.bash.spi.Process createProcess(final Context context) {
      return new Process() {
        @Override
        public Object execute(Context context) {
          while  (true) {
            Object value = test();
            if (value instanceof Integer) {
              int v = (Integer) value;
              if (v == 0) {
                break;
              } else {
                switch (body.getType()) {
                  case java_libbashParser.LIST:
                    _LIST(body, context);
                    break;
                  default:
                    throw unsupported(body);
                }
              }
            }
          }
          return null;
        }
      };
    }
  }

  private Block _CFOR(final Tree tree, Context context) {
    final Tree init = assertTree(tree.getChild(0), java_libbashParser.FOR_INIT);
    final Tree cond = assertTree(tree.getChild(1), java_libbashParser.FOR_COND);
    final Tree body = assertTree(tree.getChild(2), java_libbashParser.LIST);
    final Tree mod = assertTree(tree.getChild(3), java_libbashParser.FOR_MOD);
    return new Loop(context, body) {
      boolean initialized;
      @Override
      protected Object test() {
        if (!initialized) {
          _ARITHMETIC(mod.getChild(0), context);
        } else {
          _ARITHMETIC(assertTree(init.getChild(0), java_libbashParser.ARITHMETIC), context);
        }
        return _ARITHMETIC(cond.getChild(0), context);
      }
    };
  }

  private Block _WHILE(final Tree tree, Context context) {
    final Tree condition = assertTree(tree.getChild(0), java_libbashParser.LIST).getChild(0);
    final Tree body = tree.getChild(1);
    return new Loop(context, body) {
      @Override
      protected Object test() {
        return _LIST(condition, context);
      }
    };
  }

  private Object _EQUALS(Tree tree, Context context) {
    Tree lhs = assertTree(tree.getChild(0), java_libbashParser.LETTER);
    String name = lhs.getText();
    Tree rhs = tree.getChild(1);
    Object value;
    switch (rhs.getType()) {
      case java_libbashParser.STRING: {
        value = _STRING(rhs, context);
        break;
      }
      case java_libbashParser.DIGIT: {
        value = evalExpression(rhs, context);
        break;
      }
      default:
        throw unsupported(rhs);
    }
    context.setBinding(name, value);
    return value;
  }

  private Block _VARIABLE_DEFINITIONS(Tree tree, Context context) {
    final Tree child = tree.getChild(0);
    if (child.getType() == java_libbashParser.EQUALS) {
      return new Block() {
        @Override
        public Process createProcess(final Context context) {
          return new Process() {
            @Override
            public Object execute(Context context) {
              _EQUALS(child, context);
              return null;
            }
          };
        }
      };
    } else {
      throw unsupported(tree);
    }
  }

  private Object _STRING(Tree tree, Context context) {
    assertTree(tree, java_libbashParser.STRING);
    StringBuilder sb = new StringBuilder();
    for (int i = 0;i < tree.getChildCount();i++) {
      Tree child = tree.getChild(i);
      Object o;
      switch (child.getType()) {
        case java_libbashParser.DIGIT:
          o = Integer.parseInt(child.getText());
          break;
        case java_libbashParser.ARITHMETIC_EXPRESSION:
          o = _ARITHMETIC_EXPRESSION(child, context);
        break;
        case java_libbashParser.PLUS:
          o = "+";
          break;
        case java_libbashParser.VAR_REF:
          o = _VAR_REF(child, context);
          break;
        case java_libbashParser.LETTER:
        case java_libbashParser.NAME:
        case java_libbashParser.BLANK:
          o = child.getText();
          break;
        default:
          throw unsupported(child);
      }
      sb.append(o);
    }
    return sb.toString();
  }

  private static final int ACTION_DISPLAY = 0;
  private static final int ACTION_USE = 1;
  private static final int ACTION_ASSIGN = 2;

  private Object _VAR_REF(Tree tree, Context context) {
    Tree child = tree.getChild(0);
    int childType = child.getType();
    switch (childType) {
      case java_libbashParser.LETTER:
      case java_libbashParser.NAME: {
        return context.getBinding(child.getText());
      }
      case java_libbashParser.DISPLAY_ERROR_WHEN_UNSET_OR_NULL:
      case java_libbashParser.DISPLAY_ERROR_WHEN_UNSET:
      case java_libbashParser.ASSIGN_DEFAULT_WHEN_UNSET:
      case java_libbashParser.ASSIGN_DEFAULT_WHEN_UNSET_OR_NULL:
      case java_libbashParser.USE_DEFAULT_WHEN_UNSET_OR_NULL: {

        int action;
        String identifier = child.getChild(0).getText();
        Object o = context.getBinding(identifier);
        if (o != null) {
          if (o.toString().length() > 0) {
            return o;
          } else {
            if (childType == java_libbashParser.DISPLAY_ERROR_WHEN_UNSET_OR_NULL) {
              action = ACTION_DISPLAY;
            } else if (childType == java_libbashParser.USE_DEFAULT_WHEN_UNSET_OR_NULL) {
              action = ACTION_USE;
            } else if (childType == java_libbashParser.ASSIGN_DEFAULT_WHEN_UNSET_OR_NULL) {
              action = ACTION_ASSIGN;
            } else {
              return o;
            }
          }
        } else {
          if (childType == java_libbashParser.ASSIGN_DEFAULT_WHEN_UNSET || childType == java_libbashParser.ASSIGN_DEFAULT_WHEN_UNSET_OR_NULL) {
            action = ACTION_ASSIGN;
          } else if (childType == java_libbashParser.USE_DEFAULT_WHEN_UNSET_OR_NULL) {
            action = ACTION_USE;
          }else {
            action = ACTION_DISPLAY;
          }
        }

        //
        switch (action) {
          case ACTION_DISPLAY: {
            String s = _STRING(child.getChild(1), context).toString();
            throw new RuntimeException(s);
          }
          case ACTION_USE: {
            return _STRING(child.getChild(1), context).toString();
          }
          case ACTION_ASSIGN: {
            String s = _STRING(child.getChild(1), context).toString();
            context.setBinding(identifier, s);
            return s;
          }
          default:
            throw new AssertionError();
        }
      }
      default:
        throw unsupported(child);
    }
  }

  private Object _ARITHMETIC_EXPRESSION(Tree tree, Context context) {
    Tree arithmetic = assertTree(tree.getChild(0), java_libbashParser.ARITHMETIC);
    return _ARITHMETIC(arithmetic, context);
  }

  private Object _ARITHMETIC(Tree tree, Context context) {
    assertTree(tree, java_libbashParser.ARITHMETIC);
    Tree expression = assertTree(
        tree.getChild(0),
        java_libbashParser.MINUS,
        java_libbashParser.PLUS,
        java_libbashParser.TIMES,
        java_libbashParser.SLASH,
        java_libbashParser.PCT,
        java_libbashParser.PRE_INCR,
        java_libbashParser.PRE_DECR,
        java_libbashParser.POST_INCR,
        java_libbashParser.POST_DECR,
        java_libbashParser.LESS_THAN,
        java_libbashParser.VAR_REF,
        java_libbashParser.LEQ,
        java_libbashParser.GEQ,
        java_libbashParser.EQUALS_TO,
        java_libbashParser.NOT_EQUALS,
        java_libbashParser.GREATER_THAN,
        java_libbashParser.DIGIT,
        java_libbashParser.EQUALS);
    return evalExpression(expression, context);
  }

  private Object evalExpression(Tree tree, Context context) {
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
        Object left = evalExpression(leftTree, context);
        Object right = evalExpression(rightTree, context);
        int l = fooInt(left);
        int r = fooInt(right);
        switch (tree.getType()) {
          case java_libbashParser.PLUS:
            return l + r;
          case java_libbashParser.MINUS:
            return l - r;
          case java_libbashParser.TIMES:
            return l * r;
          case java_libbashParser.SLASH:
            return l / r;
          case java_libbashParser.PCT:
            return l % r;
          case java_libbashParser.LESS_THAN:
            return l < r ? 1 : 0;
          case java_libbashParser.LEQ:
            return l <= r ? 1 : 0;
          case java_libbashParser.GEQ:
            return l >= r ? 1 : 0;
          case java_libbashParser.EQUALS_TO:
            return l == r ? 1 : 0;
          case java_libbashParser.NOT_EQUALS:
            return l != r ? 1 : 0;
          case java_libbashParser.GREATER_THAN:
            return l > r ? 1 : 0;
          default:
            throw new AssertionError();
        }
      }
      case java_libbashParser.PRE_INCR:
      case java_libbashParser.PRE_DECR:
      case java_libbashParser.POST_INCR:
      case java_libbashParser.POST_DECR: {
        Tree exprTree = tree.getChild(0);
        if (exprTree.getType() == java_libbashParser.VAR_REF) {
          Tree ff = exprTree.getChild(0);
          if (ff.getType() == java_libbashParser.LETTER || ff.getType() == java_libbashParser.NAME) {
            String identifier = ff.getText();
            Object o = context.getBinding(identifier);
            int val;
            if (o == null) {
              val = 0;
            } else {
              val = fooInt(o);
            }
            int next;
            switch (tree.getType()) {
              case java_libbashParser.PRE_INCR:
                next = ++val;
                break;
              case java_libbashParser.PRE_DECR:
                next = --val;
                break;
              case java_libbashParser.POST_INCR:
                next = val + 1;
                break;
              case java_libbashParser.POST_DECR:
                next = val - 1;
                break;
              default:
                throw new AssertionError();
            }
            context.setBinding(identifier, next);
            return val;
          } else {
            // That should be enforced by the AST isn't it ?
            // for instance $(( ${x}++ )) does not make sense to bash
            // but it does in our case
            throw unsupported(ff);
          }
        } else {
          throw unsupported(tree);
        }
      }
      case java_libbashParser.VAR_REF: {
        return _VAR_REF(tree, context);
      }
      case java_libbashParser.NUMBER:
      case java_libbashParser.DIGIT: {
        return Integer.parseInt(tree.getText());
      }
      case java_libbashParser.EQUALS: {
        return _EQUALS(tree, context);
      }
      default:
        throw unsupported(tree);
    }
  }

  private int fooInt(Object o) {
    if (o instanceof Integer) {
      return (Integer)o;
    } else if (o instanceof String) {
      return Integer.parseInt((String)o);
    } else {
      throw new UnsupportedOperationException();
    }
  }

  private Block _COMMAND(Tree tree, Context context) {
    Tree child = tree.getChild(0);
    switch (child.getType()) {
      case java_libbashParser.STRING:
        Object command = _STRING(child, context);
        int childCount = tree.getChildCount();
        List<String> parameters;
        if (childCount > 1) {
          parameters = new ArrayList<String>(childCount - 1);
          for (int index = 1;index < childCount;index++) {
            Object o = _STRING(tree.getChild(index), context);
            parameters.add(o.toString());
          }
        } else {
          parameters = Collections.emptyList();
        }
        return new Command(command.toString(), parameters);
      case java_libbashParser.VARIABLE_DEFINITIONS:
        return _VARIABLE_DEFINITIONS(child, context);
      case java_libbashParser.WHILE:
        return _WHILE(child, context);
      case java_libbashParser.CFOR:
        return _CFOR(child, context);
      default:
        throw unsupported(child);
    }
  }

  private Object _LIST(Tree tree, Context context) {
    int count = tree.getChildCount();
    Object last = null;
    for (int index = 0; index < count;index++) {
      Tree child = tree.getChild(index);
      switch (child.getType()) {
        case java_libbashParser.COMMAND:
          Block block = _COMMAND(child, context);
          last = context.execute(new Process[]{block.createProcess(context)});
          break;
        case java_libbashParser.ARITHMETIC_EXPRESSION:
          last = _ARITHMETIC_EXPRESSION(child, context);
          break;
        case java_libbashParser.PIPE: {
          Process[] pipeline = new Process[child.getChildCount()];
          for (int c = 0;c < child.getChildCount();c++) {
            Tree pipeComponent = child.getChild(c);
            assertTree(pipeComponent, java_libbashParser.COMMAND);
            pipeline[c] = _COMMAND(child.getChild(c), context).createProcess(context);
          }
          last  = context.execute(pipeline);
          break;
        }
        default:
          throw unsupported(child);
      }
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
