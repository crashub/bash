package org.crashub.bash.ir;

import org.crashub.bash.spi.Context;
import org.crashub.bash.spi.Scope;
import org.gentoo.libbash.java_libbashParser;

/**
 * @author Julien Viet
 */
public abstract class Expression<R> extends Node<R> {

  public static class Literal extends Expression<Integer> {

    final int value;

    public Literal(int value) {
      this.value = value;
    }

    @Override
    public Integer eval(Scope bindings, Context context) {
      return value;
    }
  }

  public static class Binary extends Expression<Object> {

    final Expression<?> left;
    final Expression<?> right;
    final int type;

    public Binary(Expression<?> left, Expression<?> right, int type) {
      this.left = left;
      this.right = right;
      this.type = type;
    }

    @Override
    public Object eval(Scope bindings, Context context) {
      Object left = this.left.eval(bindings, context);
      Object right = this.right.eval(bindings, context);
      int l = toInt(left);
      int r = toInt(right);
      switch (type) {
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
  }

  public static class Assign extends Expression<Object> {

    final String identifier;
    final Node rhs;

    public Assign(String identifier, Node rhs) {
      this.identifier = identifier;
      this.rhs = rhs;
    }

    @Override
    public Object eval(Scope bindings, Context context) {
      Object value = rhs.eval(bindings, context);
      context.setValue(bindings, identifier, value);
      return null;
    }
  }

  public static class Unary extends Expression<Integer> {

    final VarRef varRef;
    final int type;

    public Unary(VarRef varRef, int type) {
      // Note we get VarRef but it should be VarRef.Eval
      // for instance $(( ${x}++ )) does not make sense to bash
      // but it does in our case
      this.varRef = varRef;
      this.type = type;
    }

    @Override
    public Integer eval(Scope bindings, Context context) {
      Object o = context.getValue(bindings, varRef.identifier);
      int val;
      if (o == null) {
        val = 0;
      } else {
        val = toInt(o);
      }
      int next;
      switch (type) {
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
      context.setValue(bindings, varRef.identifier, next);
      return val;
    }
  }

  public abstract static class VarRef extends Expression<Object> {

    final String identifier;

    public VarRef(String identifier) {
      this.identifier = identifier;
    }

    public static class Eval extends VarRef {
      public Eval(String identifier) {
        super(identifier);
      }
      @Override
      public Object eval(Scope bindings, Context context) {
        return context.getValue(bindings, identifier);
      }
    }

    public static class Default extends VarRef {
      private static final int ACTION_DISPLAY = 0;
      private static final int ACTION_USE = 1;
      private static final int ACTION_ASSIGN = 2;
      final int type;
      final STRING message;
      public Default(String identifier, int type, STRING message) {
        super(identifier);
        this.type = type;
        this.message = message;
      }
      @Override
      public Object eval(Scope bindings, Context context) {
        int action;
        Object o = context.getValue(bindings, identifier);
        if (o != null) {
          if (o.toString().length() > 0) {
            // Normal case
            if (type == java_libbashParser.USE_ALTERNATE_WHEN_UNSET) {
              action = ACTION_USE;
            } else {
              return o;
            }
          } else {
            // Null case
            if (type == java_libbashParser.DISPLAY_ERROR_WHEN_UNSET_OR_NULL) {
              action = ACTION_DISPLAY;
            } else if (type == java_libbashParser.USE_DEFAULT_WHEN_UNSET_OR_NULL) {
              action = ACTION_USE;
            } else if (type == java_libbashParser.ASSIGN_DEFAULT_WHEN_UNSET_OR_NULL) {
              action = ACTION_ASSIGN;
            } else if (type == java_libbashParser.USE_ALTERNATE_WHEN_UNSET) {
              action = ACTION_USE;
            } else {
              return o;
            }
          }
        } else {
          // Unset case
          if (type == java_libbashParser.ASSIGN_DEFAULT_WHEN_UNSET || type == java_libbashParser.ASSIGN_DEFAULT_WHEN_UNSET_OR_NULL) {
            action = ACTION_ASSIGN;
          } else if (type == java_libbashParser.USE_DEFAULT_WHEN_UNSET_OR_NULL) {
            action = ACTION_USE;
          } else if (type == java_libbashParser.USE_ALTERNATE_WHEN_UNSET) {
            return null;
          } else {
            action = ACTION_DISPLAY;
          }
        }
        String s = message.eval(bindings, context);
        switch (action) {
          case ACTION_DISPLAY: {
            throw new RuntimeException(s);
          }
          case ACTION_USE: {
            return s;
          }
          case ACTION_ASSIGN: {
            context.setValue(bindings, identifier, s);
            return s;
          }
          default:
            throw new AssertionError();
        }
      }
    }
  }
}
