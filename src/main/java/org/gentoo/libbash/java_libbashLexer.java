package org.gentoo.libbash;
// $ANTLR 3.1.3 October 08, 2012 14:11:41 java_libbash.g 2012-10-08 14:22:33

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class java_libbashLexer extends Lexer {
    public static final int FUNCTION=94;
    public static final int DIVIDE_ASSIGN=80;
    public static final int LSQUARE=122;
    public static final int WHILE=141;
    public static final int LETTER=106;
    public static final int SINGLE_QUOTED_STRING_TOKEN=152;
    public static final int BUILTIN_LOGIC_AND=92;
    public static final int MOD_ASSIGN=82;
    public static final int CASE=143;
    public static final int AMP_GREATER_THAN=116;
    public static final int COMMAND_SUBSTITUTION_TICK=177;
    public static final int DO=131;
    public static final int DQUOTE=126;
    public static final int REDIR=33;
    public static final int EQUALS=124;
    public static final int MATCH_ALL=51;
    public static final int ESC_TICK=169;
    public static final int EOF=-1;
    public static final int FILE_DESCRIPTOR_MOVE=32;
    public static final int COMMAND=30;
    public static final int FILE_DESCRIPTOR=31;
    public static final int SUBSHELL=12;
    public static final int MUL_ASSIGN=81;
    public static final int CASE_COMMAND=11;
    public static final int RPAREN=110;
    public static final int NAME=118;
    public static final int EOL=95;
    public static final int USE_ALTERNATE_WHEN_UNSET=60;
    public static final int ARG=5;
    public static final int EXP=178;
    public static final int DONE=132;
    public static final int ESC_LSQUARE=167;
    public static final int CARET=161;
    public static final int ARITHMETIC=36;
    public static final int CFOR=15;
    public static final int KEYWORD_TEST=37;
    public static final int ESC_DOLLAR=168;
    public static final int LIST_EXPAND=68;
    public static final int AMP_RSHIFT=117;
    public static final int EXPORT=119;
    public static final int GEQ=89;
    public static final int COMMENT=179;
    public static final int SELECT=135;
    public static final int ARRAY=6;
    public static final int PRE_DECR=22;
    public static final int ESC_LT=147;
    public static final int ARRAY_SIZE=7;
    public static final int ESC_DQUOTE=170;
    public static final int LSHIFT_ASSIGN=83;
    public static final int EXTENDED_MATCH_AT_MOST_ONE=41;
    public static final int AMP_LESS_THAN=115;
    public static final int LOGICAND=100;
    public static final int FOR_MOD=18;
    public static final int LLPAREN=133;
    public static final int LOGICOR=101;
    public static final int LAZY_REMOVE_AT_END=74;
    public static final int EMPTY_EXPANSION_VALUE=75;
    public static final int RBRACE=128;
    public static final int ESC_RPAREN=164;
    public static final int POUND=99;
    public static final int LSHIFT=113;
    public static final int REPLACE_FIRST=69;
    public static final int BUILTIN_TEST=38;
    public static final int ELSE=138;
    public static final int BLANK=96;
    public static final int MINUS_ASSIGN=79;
    public static final int NUMBER=150;
    public static final int VAR_REF=26;
    public static final int UNDERSCORE=149;
    public static final int FOR_COND=17;
    public static final int LOCAL=120;
    public static final int ARITHMETIC_EXPRESSION=35;
    public static final int LIST=28;
    public static final int COMMAND_SUB=9;
    public static final int MATCH_ONE=52;
    public static final int ALPHANUM=174;
    public static final int REPLACE_AT_START=71;
    public static final int FI=139;
    public static final int IF_STATEMENT=19;
    public static final int ELIF=140;
    public static final int ANSI_C_QUOTING=4;
    public static final int REPLACE_AT_END=72;
    public static final int USE_DEFAULT_WHEN_UNSET=59;
    public static final int EXTENDED_MATCH_ANY=43;
    public static final int UNTIL=142;
    public static final int OP=20;
    public static final int ARITHMETIC_CONDITION=34;
    public static final int LESS_THAN=107;
    public static final int USE_DEFAULT_WHEN_UNSET_OR_NULL=63;
    public static final int POST_INCR=23;
    public static final int COMMAND_SUBSTITUTION_PAREN=176;
    public static final int QMARK=173;
    public static final int EQUIVALENCE_CLASS=54;
    public static final int FOR_INIT=16;
    public static final int MATCH_ANY_EXCEPT=39;
    public static final int DOLLAR=151;
    public static final int AMP=98;
    public static final int ESC=146;
    public static final int LBRACE=127;
    public static final int LAZY_REMOVE_AT_START=73;
    public static final int PROCESS_SUBSTITUTION=25;
    public static final int ASSIGN_DEFAULT_WHEN_UNSET_OR_NULL=66;
    public static final int ESAC=144;
    public static final int ASSIGN_DEFAULT_WHEN_UNSET=62;
    public static final int ESC_GT=148;
    public static final int REPLACE_ALL=70;
    public static final int FOR=129;
    public static final int DOTDOT=159;
    public static final int ESC_LPAREN=165;
    public static final int ESCAPED_CHAR=48;
    public static final int NOT_EQUALS=90;
    public static final int AND_ASSIGN=85;
    public static final int LPAREN=109;
    public static final int IF=136;
    public static final int AT=163;
    public static final int TIME=104;
    public static final int CHARACTER_CLASS=53;
    public static final int SLASH=154;
    public static final int IN=130;
    public static final int THEN=137;
    public static final int COMMA=162;
    public static final int OFFSET=67;
    public static final int MINUS_SIGN=77;
    public static final int XOR_ASSIGN=86;
    public static final int CURRENT_SHELL=13;
    public static final int TILDE=145;
    public static final int MATCH_ANY=50;
    public static final int CASE_PATTERN=10;
    public static final int PIPE=103;
    public static final int PLUS=125;
    public static final int EXTENDED_MATCH_AT_LEAST_ONE=44;
    public static final int DIGIT=111;
    public static final int POST_DECR=24;
    public static final int DOT=158;
    public static final int RSHIFT=114;
    public static final int DOUBLE_QUOTED_STRING=56;
    public static final int TICK=153;
    public static final int DOUBLE_SEMIC=134;
    public static final int GREATER_THAN=108;
    public static final int OTHER=155;
    public static final int PLUS_SIGN=76;
    public static final int SINGLE_QUOTED_STRING=57;
    public static final int MATCH_PATTERN=46;
    public static final int ESC_SQUOTE=171;
    public static final int USE_ALTERNATE_WHEN_UNSET_OR_NULL=64;
    public static final int VARIABLE_DEFINITIONS=58;
    public static final int BRACE_EXP=8;
    public static final int BANG=102;
    public static final int RSHIFT_ASSIGN=84;
    public static final int MINUS=105;
    public static final int RSQUARE=123;
    public static final int SQUOTE=175;
    public static final int CONTINUE_LINE=180;
    public static final int MATCH_REGULAR_EXPRESSION=47;
    public static final int EXTENDED_MATCH_EXACTLY_ONE=40;
    public static final int EQUALS_TO=91;
    public static final int COLLATING_SYMBOL=55;
    public static final int COLON=160;
    public static final int OR_ASSIGN=87;
    public static final int DISPLAY_ERROR_WHEN_UNSET_OR_NULL=65;
    public static final int BUILTIN_LOGIC_OR=93;
    public static final int EXTENDED_MATCH_NONE=42;
    public static final int HERE_STRING_OP=112;
    public static final int PCTPCT=157;
    public static final int NEGATION=27;
    public static final int ESC_RSQUARE=166;
    public static final int DISPLAY_ERROR_WHEN_UNSET=61;
    public static final int PLUS_ASSIGN=78;
    public static final int BRANCH=45;
    public static final int DECLARE=121;
    public static final int NOT_MATCH_PATTERN=49;
    public static final int PCT=156;
    public static final int SEMIC=97;
    public static final int TIMES=172;
    public static final int COMPOUND_COND=14;
    public static final int PRE_INCR=21;
    public static final int STRING=29;
    public static final int LEQ=88;


     boolean double_quoted = false;
     int paren_level = 0;
     int LA(int index) {
      return input.LA(index);
     }



    // delegates
    // delegators

    public java_libbashLexer() {;} 
    public java_libbashLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public java_libbashLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "java_libbash.g"; }

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1007:2: ({...}? => ( BLANK | EOL ) '#' (~ ( '\\n' | '\\r' ) )* )
            // java_libbash.g:1007:4: {...}? => ( BLANK | EOL ) '#' (~ ( '\\n' | '\\r' ) )*
            {
            if ( !(( !double_quoted )) ) {
                throw new FailedPredicateException(input, "COMMENT", " !double_quoted ");
            }
            // java_libbash.g:1007:26: ( BLANK | EOL )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='\t'||LA1_0==' ') ) {
                alt1=1;
            }
            else if ( (LA1_0=='\n'||LA1_0=='\r') ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // java_libbash.g:1007:27: BLANK
                    {
                    mBLANK(); 

                    }
                    break;
                case 2 :
                    // java_libbash.g:1007:33: EOL
                    {
                    mEOL(); 

                    }
                    break;

            }

            match('#'); 
            // java_libbash.g:1007:42: (~ ( '\\n' | '\\r' ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='\u0000' && LA2_0<='\t')||(LA2_0>='\u000B' && LA2_0<='\f')||(LA2_0>='\u000E' && LA2_0<='\uFFFF')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // java_libbash.g:1007:42: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "BANG"
    public final void mBANG() throws RecognitionException {
        try {
            int _type = BANG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1010:6: ( '!' )
            // java_libbash.g:1010:8: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BANG"

    // $ANTLR start "CASE"
    public final void mCASE() throws RecognitionException {
        try {
            int _type = CASE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1011:6: ( 'case' )
            // java_libbash.g:1011:8: 'case'
            {
            match("case"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CASE"

    // $ANTLR start "DO"
    public final void mDO() throws RecognitionException {
        try {
            int _type = DO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1012:4: ( 'do' )
            // java_libbash.g:1012:6: 'do'
            {
            match("do"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DO"

    // $ANTLR start "DONE"
    public final void mDONE() throws RecognitionException {
        try {
            int _type = DONE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1013:6: ( 'done' )
            // java_libbash.g:1013:8: 'done'
            {
            match("done"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DONE"

    // $ANTLR start "ELIF"
    public final void mELIF() throws RecognitionException {
        try {
            int _type = ELIF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1014:6: ( 'elif' )
            // java_libbash.g:1014:8: 'elif'
            {
            match("elif"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ELIF"

    // $ANTLR start "ELSE"
    public final void mELSE() throws RecognitionException {
        try {
            int _type = ELSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1015:6: ( 'else' )
            // java_libbash.g:1015:8: 'else'
            {
            match("else"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ELSE"

    // $ANTLR start "ESAC"
    public final void mESAC() throws RecognitionException {
        try {
            int _type = ESAC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1016:6: ( 'esac' )
            // java_libbash.g:1016:8: 'esac'
            {
            match("esac"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ESAC"

    // $ANTLR start "FI"
    public final void mFI() throws RecognitionException {
        try {
            int _type = FI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1017:4: ( 'fi' )
            // java_libbash.g:1017:6: 'fi'
            {
            match("fi"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FI"

    // $ANTLR start "FOR"
    public final void mFOR() throws RecognitionException {
        try {
            int _type = FOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1018:5: ( 'for' )
            // java_libbash.g:1018:7: 'for'
            {
            match("for"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FOR"

    // $ANTLR start "IF"
    public final void mIF() throws RecognitionException {
        try {
            int _type = IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1019:4: ( 'if' )
            // java_libbash.g:1019:6: 'if'
            {
            match("if"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IF"

    // $ANTLR start "IN"
    public final void mIN() throws RecognitionException {
        try {
            int _type = IN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1020:4: ( 'in' )
            // java_libbash.g:1020:6: 'in'
            {
            match("in"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IN"

    // $ANTLR start "SELECT"
    public final void mSELECT() throws RecognitionException {
        try {
            int _type = SELECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1021:8: ( 'select' )
            // java_libbash.g:1021:10: 'select'
            {
            match("select"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SELECT"

    // $ANTLR start "THEN"
    public final void mTHEN() throws RecognitionException {
        try {
            int _type = THEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1022:6: ( 'then' )
            // java_libbash.g:1022:8: 'then'
            {
            match("then"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "THEN"

    // $ANTLR start "UNTIL"
    public final void mUNTIL() throws RecognitionException {
        try {
            int _type = UNTIL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1023:7: ( 'until' )
            // java_libbash.g:1023:9: 'until'
            {
            match("until"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "UNTIL"

    // $ANTLR start "WHILE"
    public final void mWHILE() throws RecognitionException {
        try {
            int _type = WHILE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1024:7: ( 'while' )
            // java_libbash.g:1024:9: 'while'
            {
            match("while"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WHILE"

    // $ANTLR start "LBRACE"
    public final void mLBRACE() throws RecognitionException {
        try {
            int _type = LBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1025:8: ( '{' )
            // java_libbash.g:1025:10: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LBRACE"

    // $ANTLR start "RBRACE"
    public final void mRBRACE() throws RecognitionException {
        try {
            int _type = RBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1026:8: ( '}' )
            // java_libbash.g:1026:10: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RBRACE"

    // $ANTLR start "TIME"
    public final void mTIME() throws RecognitionException {
        try {
            int _type = TIME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1027:6: ( 'time' )
            // java_libbash.g:1027:8: 'time'
            {
            match("time"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIME"

    // $ANTLR start "RPAREN"
    public final void mRPAREN() throws RecognitionException {
        try {
            int _type = RPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1028:8: ( ')' )
            // java_libbash.g:1028:10: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RPAREN"

    // $ANTLR start "LPAREN"
    public final void mLPAREN() throws RecognitionException {
        try {
            int _type = LPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1029:8: ( '(' )
            // java_libbash.g:1029:10: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LPAREN"

    // $ANTLR start "LLPAREN"
    public final void mLLPAREN() throws RecognitionException {
        try {
            int _type = LLPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1030:9: ( '((' )
            // java_libbash.g:1030:11: '(('
            {
            match("(("); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LLPAREN"

    // $ANTLR start "LSQUARE"
    public final void mLSQUARE() throws RecognitionException {
        try {
            int _type = LSQUARE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1031:9: ( '[' )
            // java_libbash.g:1031:11: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LSQUARE"

    // $ANTLR start "RSQUARE"
    public final void mRSQUARE() throws RecognitionException {
        try {
            int _type = RSQUARE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1032:9: ( ']' )
            // java_libbash.g:1032:11: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RSQUARE"

    // $ANTLR start "TICK"
    public final void mTICK() throws RecognitionException {
        try {
            int _type = TICK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1033:6: ( '`' )
            // java_libbash.g:1033:8: '`'
            {
            match('`'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TICK"

    // $ANTLR start "DOLLAR"
    public final void mDOLLAR() throws RecognitionException {
        try {
            int _type = DOLLAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1034:8: ( '$' )
            // java_libbash.g:1034:10: '$'
            {
            match('$'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOLLAR"

    // $ANTLR start "AT"
    public final void mAT() throws RecognitionException {
        try {
            int _type = AT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1035:4: ( '@' )
            // java_libbash.g:1035:6: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AT"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1036:5: ( '.' )
            // java_libbash.g:1036:7: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "DOTDOT"
    public final void mDOTDOT() throws RecognitionException {
        try {
            int _type = DOTDOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1037:8: ( '..' )
            // java_libbash.g:1037:10: '..'
            {
            match(".."); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOTDOT"

    // $ANTLR start "TIMES"
    public final void mTIMES() throws RecognitionException {
        try {
            int _type = TIMES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1039:7: ( '*' )
            // java_libbash.g:1039:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIMES"

    // $ANTLR start "EQUALS"
    public final void mEQUALS() throws RecognitionException {
        try {
            int _type = EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1040:8: ( '=' )
            // java_libbash.g:1040:10: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQUALS"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1041:7: ( '-' )
            // java_libbash.g:1041:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1042:6: ( '+' )
            // java_libbash.g:1042:8: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "EXP"
    public final void mEXP() throws RecognitionException {
        try {
            int _type = EXP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1043:5: ( '**' )
            // java_libbash.g:1043:7: '**'
            {
            match("**"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EXP"

    // $ANTLR start "AMP"
    public final void mAMP() throws RecognitionException {
        try {
            int _type = AMP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1044:5: ( '&' )
            // java_libbash.g:1044:7: '&'
            {
            match('&'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AMP"

    // $ANTLR start "CARET"
    public final void mCARET() throws RecognitionException {
        try {
            int _type = CARET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1045:7: ( '^' )
            // java_libbash.g:1045:9: '^'
            {
            match('^'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CARET"

    // $ANTLR start "LESS_THAN"
    public final void mLESS_THAN() throws RecognitionException {
        try {
            int _type = LESS_THAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1046:11: ( '<' )
            // java_libbash.g:1046:13: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LESS_THAN"

    // $ANTLR start "GREATER_THAN"
    public final void mGREATER_THAN() throws RecognitionException {
        try {
            int _type = GREATER_THAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1047:14: ( '>' )
            // java_libbash.g:1047:16: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GREATER_THAN"

    // $ANTLR start "LSHIFT"
    public final void mLSHIFT() throws RecognitionException {
        try {
            int _type = LSHIFT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1048:8: ( '<<' )
            // java_libbash.g:1048:10: '<<'
            {
            match("<<"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LSHIFT"

    // $ANTLR start "RSHIFT"
    public final void mRSHIFT() throws RecognitionException {
        try {
            int _type = RSHIFT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1049:8: ( '>>' )
            // java_libbash.g:1049:10: '>>'
            {
            match(">>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RSHIFT"

    // $ANTLR start "AMP_LESS_THAN"
    public final void mAMP_LESS_THAN() throws RecognitionException {
        try {
            int _type = AMP_LESS_THAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1050:15: ( '&<' )
            // java_libbash.g:1050:17: '&<'
            {
            match("&<"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AMP_LESS_THAN"

    // $ANTLR start "AMP_GREATER_THAN"
    public final void mAMP_GREATER_THAN() throws RecognitionException {
        try {
            int _type = AMP_GREATER_THAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1051:18: ( '&>' )
            // java_libbash.g:1051:20: '&>'
            {
            match("&>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AMP_GREATER_THAN"

    // $ANTLR start "AMP_RSHIFT"
    public final void mAMP_RSHIFT() throws RecognitionException {
        try {
            int _type = AMP_RSHIFT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1052:12: ( '&>>' )
            // java_libbash.g:1052:14: '&>>'
            {
            match("&>>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AMP_RSHIFT"

    // $ANTLR start "SEMIC"
    public final void mSEMIC() throws RecognitionException {
        try {
            int _type = SEMIC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1054:7: ( ';' )
            // java_libbash.g:1054:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SEMIC"

    // $ANTLR start "DOUBLE_SEMIC"
    public final void mDOUBLE_SEMIC() throws RecognitionException {
        try {
            int _type = DOUBLE_SEMIC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1055:14: ( ';;' )
            // java_libbash.g:1055:16: ';;'
            {
            match(";;"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOUBLE_SEMIC"

    // $ANTLR start "PIPE"
    public final void mPIPE() throws RecognitionException {
        try {
            int _type = PIPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1056:6: ( '|' )
            // java_libbash.g:1056:8: '|'
            {
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PIPE"

    // $ANTLR start "ESC_DQUOTE"
    public final void mESC_DQUOTE() throws RecognitionException {
        try {
            int _type = ESC_DQUOTE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1057:12: ( '\\\\\"' )
            // java_libbash.g:1057:14: '\\\\\"'
            {
            match("\\\""); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ESC_DQUOTE"

    // $ANTLR start "ESC_SQUOTE"
    public final void mESC_SQUOTE() throws RecognitionException {
        try {
            int _type = ESC_SQUOTE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1058:12: ({...}? => '\\\\\\'' )
            // java_libbash.g:1058:14: {...}? => '\\\\\\''
            {
            if ( !(( !double_quoted )) ) {
                throw new FailedPredicateException(input, "ESC_SQUOTE", " !double_quoted ");
            }
            match("\\'"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ESC_SQUOTE"

    // $ANTLR start "DQUOTE"
    public final void mDQUOTE() throws RecognitionException {
        try {
            int _type = DQUOTE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1059:8: ( '\"' )
            // java_libbash.g:1059:10: '\"'
            {
            match('\"'); 
             if(LA(-1) != '\\') double_quoted = !double_quoted; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DQUOTE"

    // $ANTLR start "SQUOTE"
    public final void mSQUOTE() throws RecognitionException {
        try {
            int _type = SQUOTE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1060:8: ({...}? => '\\'' )
            // java_libbash.g:1060:10: {...}? => '\\''
            {
            if ( !(( double_quoted )) ) {
                throw new FailedPredicateException(input, "SQUOTE", " double_quoted ");
            }
            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SQUOTE"

    // $ANTLR start "SINGLE_QUOTED_STRING_TOKEN"
    public final void mSINGLE_QUOTED_STRING_TOKEN() throws RecognitionException {
        try {
            int _type = SINGLE_QUOTED_STRING_TOKEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1061:28: ({...}? => '\\'' ( . )* '\\'' )
            // java_libbash.g:1061:30: {...}? => '\\'' ( . )* '\\''
            {
            if ( !(( !double_quoted )) ) {
                throw new FailedPredicateException(input, "SINGLE_QUOTED_STRING_TOKEN", " !double_quoted ");
            }
            match('\''); 
            // java_libbash.g:1061:58: ( . )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0=='\'') ) {
                    alt3=2;
                }
                else if ( ((LA3_0>='\u0000' && LA3_0<='&')||(LA3_0>='(' && LA3_0<='\uFFFF')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // java_libbash.g:1061:58: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SINGLE_QUOTED_STRING_TOKEN"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1062:7: ( ',' )
            // java_libbash.g:1062:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "BLANK"
    public final void mBLANK() throws RecognitionException {
        try {
            int _type = BLANK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1063:7: ( ( ' ' | '\\t' )+ )
            // java_libbash.g:1063:9: ( ' ' | '\\t' )+
            {
            // java_libbash.g:1063:9: ( ' ' | '\\t' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0=='\t'||LA4_0==' ') ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // java_libbash.g:
            	    {
            	    if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BLANK"

    // $ANTLR start "EOL"
    public final void mEOL() throws RecognitionException {
        try {
            int _type = EOL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1064:5: ( ( ( '\\r' )? '\\n' )+ )
            // java_libbash.g:1064:7: ( ( '\\r' )? '\\n' )+
            {
            // java_libbash.g:1064:7: ( ( '\\r' )? '\\n' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0=='\n'||LA6_0=='\r') ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // java_libbash.g:1064:8: ( '\\r' )? '\\n'
            	    {
            	    // java_libbash.g:1064:8: ( '\\r' )?
            	    int alt5=2;
            	    int LA5_0 = input.LA(1);

            	    if ( (LA5_0=='\r') ) {
            	        alt5=1;
            	    }
            	    switch (alt5) {
            	        case 1 :
            	            // java_libbash.g:1064:8: '\\r'
            	            {
            	            match('\r'); 

            	            }
            	            break;

            	    }

            	    match('\n'); 

            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EOL"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            int _type = DIGIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1066:7: ( '0' .. '9' )
            // java_libbash.g:1066:9: '0' .. '9'
            {
            matchRange('0','9'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "NUMBER"
    public final void mNUMBER() throws RecognitionException {
        try {
            int _type = NUMBER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1067:8: ( DIGIT ( DIGIT )+ )
            // java_libbash.g:1067:10: DIGIT ( DIGIT )+
            {
            mDIGIT(); 
            // java_libbash.g:1067:16: ( DIGIT )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='0' && LA7_0<='9')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // java_libbash.g:1067:16: DIGIT
            	    {
            	    mDIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NUMBER"

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            int _type = LETTER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1068:8: ( ( 'a' .. 'z' | 'A' .. 'Z' ) )
            // java_libbash.g:1068:10: ( 'a' .. 'z' | 'A' .. 'Z' )
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LETTER"

    // $ANTLR start "ALPHANUM"
    public final void mALPHANUM() throws RecognitionException {
        try {
            // java_libbash.g:1070:10: ( ( DIGIT | LETTER ) )
            // java_libbash.g:1070:12: ( DIGIT | LETTER )
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "ALPHANUM"

    // $ANTLR start "TILDE"
    public final void mTILDE() throws RecognitionException {
        try {
            int _type = TILDE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1072:7: ( '~' )
            // java_libbash.g:1072:9: '~'
            {
            match('~'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TILDE"

    // $ANTLR start "HERE_STRING_OP"
    public final void mHERE_STRING_OP() throws RecognitionException {
        try {
            int _type = HERE_STRING_OP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1073:16: ( '<<<' )
            // java_libbash.g:1073:18: '<<<'
            {
            match("<<<"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "HERE_STRING_OP"

    // $ANTLR start "POUND"
    public final void mPOUND() throws RecognitionException {
        try {
            int _type = POUND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1074:7: ( '#' )
            // java_libbash.g:1074:9: '#'
            {
            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "POUND"

    // $ANTLR start "PCT"
    public final void mPCT() throws RecognitionException {
        try {
            int _type = PCT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1075:5: ( '%' )
            // java_libbash.g:1075:7: '%'
            {
            match('%'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PCT"

    // $ANTLR start "PCTPCT"
    public final void mPCTPCT() throws RecognitionException {
        try {
            int _type = PCTPCT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1076:8: ( '%%' )
            // java_libbash.g:1076:10: '%%'
            {
            match("%%"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PCTPCT"

    // $ANTLR start "SLASH"
    public final void mSLASH() throws RecognitionException {
        try {
            int _type = SLASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1077:7: ( '/' )
            // java_libbash.g:1077:9: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SLASH"

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1078:7: ( ':' )
            // java_libbash.g:1078:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COLON"

    // $ANTLR start "QMARK"
    public final void mQMARK() throws RecognitionException {
        try {
            int _type = QMARK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1079:7: ( '?' )
            // java_libbash.g:1079:9: '?'
            {
            match('?'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "QMARK"

    // $ANTLR start "LOCAL"
    public final void mLOCAL() throws RecognitionException {
        try {
            int _type = LOCAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1081:7: ( 'local' )
            // java_libbash.g:1081:9: 'local'
            {
            match("local"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LOCAL"

    // $ANTLR start "EXPORT"
    public final void mEXPORT() throws RecognitionException {
        try {
            int _type = EXPORT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1082:8: ( 'export' )
            // java_libbash.g:1082:10: 'export'
            {
            match("export"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EXPORT"

    // $ANTLR start "DECLARE"
    public final void mDECLARE() throws RecognitionException {
        try {
            int _type = DECLARE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1083:9: ( 'declare' )
            // java_libbash.g:1083:11: 'declare'
            {
            match("declare"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DECLARE"

    // $ANTLR start "LOGICAND"
    public final void mLOGICAND() throws RecognitionException {
        try {
            int _type = LOGICAND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1084:10: ( '&&' )
            // java_libbash.g:1084:12: '&&'
            {
            match("&&"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LOGICAND"

    // $ANTLR start "LOGICOR"
    public final void mLOGICOR() throws RecognitionException {
        try {
            int _type = LOGICOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1085:9: ( '||' )
            // java_libbash.g:1085:11: '||'
            {
            match("||"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LOGICOR"

    // $ANTLR start "CONTINUE_LINE"
    public final void mCONTINUE_LINE() throws RecognitionException {
        try {
            int _type = CONTINUE_LINE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1087:15: ( ( ESC EOL )+ )
            // java_libbash.g:1087:17: ( ESC EOL )+
            {
            // java_libbash.g:1087:17: ( ESC EOL )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0=='\\') ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // java_libbash.g:1087:18: ESC EOL
            	    {
            	    mESC(); 
            	    mEOL(); 

            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
            } while (true);

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONTINUE_LINE"

    // $ANTLR start "ESC_RPAREN"
    public final void mESC_RPAREN() throws RecognitionException {
        try {
            int _type = ESC_RPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1088:12: ( ESC RPAREN )
            // java_libbash.g:1088:14: ESC RPAREN
            {
            mESC(); 
            mRPAREN(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ESC_RPAREN"

    // $ANTLR start "ESC_LPAREN"
    public final void mESC_LPAREN() throws RecognitionException {
        try {
            int _type = ESC_LPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1089:12: ( ESC LPAREN )
            // java_libbash.g:1089:14: ESC LPAREN
            {
            mESC(); 
            mLPAREN(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ESC_LPAREN"

    // $ANTLR start "ESC_RSQUARE"
    public final void mESC_RSQUARE() throws RecognitionException {
        try {
            int _type = ESC_RSQUARE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1090:13: ( ESC RSQUARE )
            // java_libbash.g:1090:15: ESC RSQUARE
            {
            mESC(); 
            mRSQUARE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ESC_RSQUARE"

    // $ANTLR start "ESC_LSQUARE"
    public final void mESC_LSQUARE() throws RecognitionException {
        try {
            int _type = ESC_LSQUARE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1091:13: ( ESC LSQUARE )
            // java_libbash.g:1091:15: ESC LSQUARE
            {
            mESC(); 
            mLSQUARE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ESC_LSQUARE"

    // $ANTLR start "ESC_DOLLAR"
    public final void mESC_DOLLAR() throws RecognitionException {
        try {
            int _type = ESC_DOLLAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1092:12: ( ESC DOLLAR )
            // java_libbash.g:1092:14: ESC DOLLAR
            {
            mESC(); 
            mDOLLAR(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ESC_DOLLAR"

    // $ANTLR start "ESC_TICK"
    public final void mESC_TICK() throws RecognitionException {
        try {
            int _type = ESC_TICK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1093:10: ( ESC TICK )
            // java_libbash.g:1093:12: ESC TICK
            {
            mESC(); 
            mTICK(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ESC_TICK"

    // $ANTLR start "COMMAND_SUBSTITUTION_PAREN"
    public final void mCOMMAND_SUBSTITUTION_PAREN() throws RecognitionException {
        try {
            int _type = COMMAND_SUBSTITUTION_PAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1095:2: ({...}? => ( DOLLAR LPAREN ( ( ESC_LPAREN | ESC_RPAREN | LPAREN | RPAREN | SINGLE_QUOTED_STRING_TOKEN | . )+ ) ) )
            // java_libbash.g:1095:4: {...}? => ( DOLLAR LPAREN ( ( ESC_LPAREN | ESC_RPAREN | LPAREN | RPAREN | SINGLE_QUOTED_STRING_TOKEN | . )+ ) )
            {
            if ( !((LA(1) == '$' && LA(2) == '(' && LA(3) != '(')) ) {
                throw new FailedPredicateException(input, "COMMAND_SUBSTITUTION_PAREN", "LA(1) == '$' && LA(2) == '(' && LA(3) != '('");
            }
            // java_libbash.g:1096:4: ( DOLLAR LPAREN ( ( ESC_LPAREN | ESC_RPAREN | LPAREN | RPAREN | SINGLE_QUOTED_STRING_TOKEN | . )+ ) )
            // java_libbash.g:1096:5: DOLLAR LPAREN ( ( ESC_LPAREN | ESC_RPAREN | LPAREN | RPAREN | SINGLE_QUOTED_STRING_TOKEN | . )+ )
            {
            mDOLLAR(); 
            mLPAREN(); 
            // java_libbash.g:1096:19: ( ( ESC_LPAREN | ESC_RPAREN | LPAREN | RPAREN | SINGLE_QUOTED_STRING_TOKEN | . )+ )
            // java_libbash.g:1096:20: ( ESC_LPAREN | ESC_RPAREN | LPAREN | RPAREN | SINGLE_QUOTED_STRING_TOKEN | . )+
            {
             paren_level = 1; 
            // java_libbash.g:1097:5: ( ESC_LPAREN | ESC_RPAREN | LPAREN | RPAREN | SINGLE_QUOTED_STRING_TOKEN | . )+
            int cnt9=0;
            loop9:
            do {
                int alt9=7;
                alt9 = dfa9.predict(input);
                switch (alt9) {
            	case 1 :
            	    // java_libbash.g:1098:6: ESC_LPAREN
            	    {
            	    mESC_LPAREN(); 

            	    }
            	    break;
            	case 2 :
            	    // java_libbash.g:1099:8: ESC_RPAREN
            	    {
            	    mESC_RPAREN(); 

            	    }
            	    break;
            	case 3 :
            	    // java_libbash.g:1100:8: LPAREN
            	    {
            	    mLPAREN(); 
            	     ++paren_level; 

            	    }
            	    break;
            	case 4 :
            	    // java_libbash.g:1101:8: RPAREN
            	    {
            	    mRPAREN(); 

            	           if(--paren_level == 0)
            	           {



            	            state.type = _type;
            	            state.channel = _channel;

            	            return;
            	           }
            	          

            	    }
            	    break;
            	case 5 :
            	    // java_libbash.g:1114:8: SINGLE_QUOTED_STRING_TOKEN
            	    {
            	    mSINGLE_QUOTED_STRING_TOKEN(); 

            	    }
            	    break;
            	case 6 :
            	    // java_libbash.g:1115:8: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    if ( cnt9 >= 1 ) break loop9;
                        EarlyExitException eee =
                            new EarlyExitException(9, input);
                        throw eee;
                }
                cnt9++;
            } while (true);


            }


            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMAND_SUBSTITUTION_PAREN"

    // $ANTLR start "COMMAND_SUBSTITUTION_TICK"
    public final void mCOMMAND_SUBSTITUTION_TICK() throws RecognitionException {
        try {
            int _type = COMMAND_SUBSTITUTION_TICK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1118:27: ( TICK ( . )+ (~ ESC ) TICK )
            // java_libbash.g:1118:29: TICK ( . )+ (~ ESC ) TICK
            {
            mTICK(); 
            // java_libbash.g:1118:34: ( . )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='\u0000' && LA10_0<='[')||(LA10_0>=']' && LA10_0<='\uFFFF')) ) {
                    int LA10_1 = input.LA(2);

                    if ( (LA10_1=='`') ) {
                        alt10=2;
                    }
                    else if ( ((LA10_1>='\u0000' && LA10_1<='_')||(LA10_1>='a' && LA10_1<='\uFFFF')) ) {
                        alt10=1;
                    }


                }
                else if ( (LA10_0=='\\') ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // java_libbash.g:1118:34: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);

            // java_libbash.g:1118:37: (~ ESC )
            // java_libbash.g:1118:38: ~ ESC
            {
            if ( (input.LA(1)>='\u0000' && input.LA(1)<='\u0091')||(input.LA(1)>='\u0093' && input.LA(1)<='\uFFFF') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            mTICK(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMAND_SUBSTITUTION_TICK"

    // $ANTLR start "ESC_LT"
    public final void mESC_LT() throws RecognitionException {
        try {
            int _type = ESC_LT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1119:8: ( ESC '<' )
            // java_libbash.g:1119:10: ESC '<'
            {
            mESC(); 
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ESC_LT"

    // $ANTLR start "ESC_GT"
    public final void mESC_GT() throws RecognitionException {
        try {
            int _type = ESC_GT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1120:8: ( ESC '>' )
            // java_libbash.g:1120:10: ESC '>'
            {
            mESC(); 
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ESC_GT"

    // $ANTLR start "ESC"
    public final void mESC() throws RecognitionException {
        try {
            int _type = ESC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1122:5: ( '\\\\' )
            // java_libbash.g:1122:7: '\\\\'
            {
            match('\\'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ESC"

    // $ANTLR start "UNDERSCORE"
    public final void mUNDERSCORE() throws RecognitionException {
        try {
            int _type = UNDERSCORE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1123:12: ( '_' )
            // java_libbash.g:1123:14: '_'
            {
            match('_'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "UNDERSCORE"

    // $ANTLR start "NAME"
    public final void mNAME() throws RecognitionException {
        try {
            int _type = NAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1124:6: ( ( LETTER | UNDERSCORE ) ( ALPHANUM | UNDERSCORE )+ )
            // java_libbash.g:1124:8: ( LETTER | UNDERSCORE ) ( ALPHANUM | UNDERSCORE )+
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // java_libbash.g:1124:27: ( ALPHANUM | UNDERSCORE )+
            int cnt11=0;
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>='0' && LA11_0<='9')||(LA11_0>='A' && LA11_0<='Z')||LA11_0=='_'||(LA11_0>='a' && LA11_0<='z')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // java_libbash.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt11 >= 1 ) break loop11;
                        EarlyExitException eee =
                            new EarlyExitException(11, input);
                        throw eee;
                }
                cnt11++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NAME"

    // $ANTLR start "OTHER"
    public final void mOTHER() throws RecognitionException {
        try {
            int _type = OTHER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // java_libbash.g:1125:7: ( . )
            // java_libbash.g:1125:9: .
            {
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OTHER"

    public void mTokens() throws RecognitionException {
        // java_libbash.g:1:8: ( COMMENT | BANG | CASE | DO | DONE | ELIF | ELSE | ESAC | FI | FOR | IF | IN | SELECT | THEN | UNTIL | WHILE | LBRACE | RBRACE | TIME | RPAREN | LPAREN | LLPAREN | LSQUARE | RSQUARE | TICK | DOLLAR | AT | DOT | DOTDOT | TIMES | EQUALS | MINUS | PLUS | EXP | AMP | CARET | LESS_THAN | GREATER_THAN | LSHIFT | RSHIFT | AMP_LESS_THAN | AMP_GREATER_THAN | AMP_RSHIFT | SEMIC | DOUBLE_SEMIC | PIPE | ESC_DQUOTE | ESC_SQUOTE | DQUOTE | SQUOTE | SINGLE_QUOTED_STRING_TOKEN | COMMA | BLANK | EOL | DIGIT | NUMBER | LETTER | TILDE | HERE_STRING_OP | POUND | PCT | PCTPCT | SLASH | COLON | QMARK | LOCAL | EXPORT | DECLARE | LOGICAND | LOGICOR | CONTINUE_LINE | ESC_RPAREN | ESC_LPAREN | ESC_RSQUARE | ESC_LSQUARE | ESC_DOLLAR | ESC_TICK | COMMAND_SUBSTITUTION_PAREN | COMMAND_SUBSTITUTION_TICK | ESC_LT | ESC_GT | ESC | UNDERSCORE | NAME | OTHER )
        int alt12=85;
        alt12 = dfa12.predict(input);
        switch (alt12) {
            case 1 :
                // java_libbash.g:1:10: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 2 :
                // java_libbash.g:1:18: BANG
                {
                mBANG(); 

                }
                break;
            case 3 :
                // java_libbash.g:1:23: CASE
                {
                mCASE(); 

                }
                break;
            case 4 :
                // java_libbash.g:1:28: DO
                {
                mDO(); 

                }
                break;
            case 5 :
                // java_libbash.g:1:31: DONE
                {
                mDONE(); 

                }
                break;
            case 6 :
                // java_libbash.g:1:36: ELIF
                {
                mELIF(); 

                }
                break;
            case 7 :
                // java_libbash.g:1:41: ELSE
                {
                mELSE(); 

                }
                break;
            case 8 :
                // java_libbash.g:1:46: ESAC
                {
                mESAC(); 

                }
                break;
            case 9 :
                // java_libbash.g:1:51: FI
                {
                mFI(); 

                }
                break;
            case 10 :
                // java_libbash.g:1:54: FOR
                {
                mFOR(); 

                }
                break;
            case 11 :
                // java_libbash.g:1:58: IF
                {
                mIF(); 

                }
                break;
            case 12 :
                // java_libbash.g:1:61: IN
                {
                mIN(); 

                }
                break;
            case 13 :
                // java_libbash.g:1:64: SELECT
                {
                mSELECT(); 

                }
                break;
            case 14 :
                // java_libbash.g:1:71: THEN
                {
                mTHEN(); 

                }
                break;
            case 15 :
                // java_libbash.g:1:76: UNTIL
                {
                mUNTIL(); 

                }
                break;
            case 16 :
                // java_libbash.g:1:82: WHILE
                {
                mWHILE(); 

                }
                break;
            case 17 :
                // java_libbash.g:1:88: LBRACE
                {
                mLBRACE(); 

                }
                break;
            case 18 :
                // java_libbash.g:1:95: RBRACE
                {
                mRBRACE(); 

                }
                break;
            case 19 :
                // java_libbash.g:1:102: TIME
                {
                mTIME(); 

                }
                break;
            case 20 :
                // java_libbash.g:1:107: RPAREN
                {
                mRPAREN(); 

                }
                break;
            case 21 :
                // java_libbash.g:1:114: LPAREN
                {
                mLPAREN(); 

                }
                break;
            case 22 :
                // java_libbash.g:1:121: LLPAREN
                {
                mLLPAREN(); 

                }
                break;
            case 23 :
                // java_libbash.g:1:129: LSQUARE
                {
                mLSQUARE(); 

                }
                break;
            case 24 :
                // java_libbash.g:1:137: RSQUARE
                {
                mRSQUARE(); 

                }
                break;
            case 25 :
                // java_libbash.g:1:145: TICK
                {
                mTICK(); 

                }
                break;
            case 26 :
                // java_libbash.g:1:150: DOLLAR
                {
                mDOLLAR(); 

                }
                break;
            case 27 :
                // java_libbash.g:1:157: AT
                {
                mAT(); 

                }
                break;
            case 28 :
                // java_libbash.g:1:160: DOT
                {
                mDOT(); 

                }
                break;
            case 29 :
                // java_libbash.g:1:164: DOTDOT
                {
                mDOTDOT(); 

                }
                break;
            case 30 :
                // java_libbash.g:1:171: TIMES
                {
                mTIMES(); 

                }
                break;
            case 31 :
                // java_libbash.g:1:177: EQUALS
                {
                mEQUALS(); 

                }
                break;
            case 32 :
                // java_libbash.g:1:184: MINUS
                {
                mMINUS(); 

                }
                break;
            case 33 :
                // java_libbash.g:1:190: PLUS
                {
                mPLUS(); 

                }
                break;
            case 34 :
                // java_libbash.g:1:195: EXP
                {
                mEXP(); 

                }
                break;
            case 35 :
                // java_libbash.g:1:199: AMP
                {
                mAMP(); 

                }
                break;
            case 36 :
                // java_libbash.g:1:203: CARET
                {
                mCARET(); 

                }
                break;
            case 37 :
                // java_libbash.g:1:209: LESS_THAN
                {
                mLESS_THAN(); 

                }
                break;
            case 38 :
                // java_libbash.g:1:219: GREATER_THAN
                {
                mGREATER_THAN(); 

                }
                break;
            case 39 :
                // java_libbash.g:1:232: LSHIFT
                {
                mLSHIFT(); 

                }
                break;
            case 40 :
                // java_libbash.g:1:239: RSHIFT
                {
                mRSHIFT(); 

                }
                break;
            case 41 :
                // java_libbash.g:1:246: AMP_LESS_THAN
                {
                mAMP_LESS_THAN(); 

                }
                break;
            case 42 :
                // java_libbash.g:1:260: AMP_GREATER_THAN
                {
                mAMP_GREATER_THAN(); 

                }
                break;
            case 43 :
                // java_libbash.g:1:277: AMP_RSHIFT
                {
                mAMP_RSHIFT(); 

                }
                break;
            case 44 :
                // java_libbash.g:1:288: SEMIC
                {
                mSEMIC(); 

                }
                break;
            case 45 :
                // java_libbash.g:1:294: DOUBLE_SEMIC
                {
                mDOUBLE_SEMIC(); 

                }
                break;
            case 46 :
                // java_libbash.g:1:307: PIPE
                {
                mPIPE(); 

                }
                break;
            case 47 :
                // java_libbash.g:1:312: ESC_DQUOTE
                {
                mESC_DQUOTE(); 

                }
                break;
            case 48 :
                // java_libbash.g:1:323: ESC_SQUOTE
                {
                mESC_SQUOTE(); 

                }
                break;
            case 49 :
                // java_libbash.g:1:334: DQUOTE
                {
                mDQUOTE(); 

                }
                break;
            case 50 :
                // java_libbash.g:1:341: SQUOTE
                {
                mSQUOTE(); 

                }
                break;
            case 51 :
                // java_libbash.g:1:348: SINGLE_QUOTED_STRING_TOKEN
                {
                mSINGLE_QUOTED_STRING_TOKEN(); 

                }
                break;
            case 52 :
                // java_libbash.g:1:375: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 53 :
                // java_libbash.g:1:381: BLANK
                {
                mBLANK(); 

                }
                break;
            case 54 :
                // java_libbash.g:1:387: EOL
                {
                mEOL(); 

                }
                break;
            case 55 :
                // java_libbash.g:1:391: DIGIT
                {
                mDIGIT(); 

                }
                break;
            case 56 :
                // java_libbash.g:1:397: NUMBER
                {
                mNUMBER(); 

                }
                break;
            case 57 :
                // java_libbash.g:1:404: LETTER
                {
                mLETTER(); 

                }
                break;
            case 58 :
                // java_libbash.g:1:411: TILDE
                {
                mTILDE(); 

                }
                break;
            case 59 :
                // java_libbash.g:1:417: HERE_STRING_OP
                {
                mHERE_STRING_OP(); 

                }
                break;
            case 60 :
                // java_libbash.g:1:432: POUND
                {
                mPOUND(); 

                }
                break;
            case 61 :
                // java_libbash.g:1:438: PCT
                {
                mPCT(); 

                }
                break;
            case 62 :
                // java_libbash.g:1:442: PCTPCT
                {
                mPCTPCT(); 

                }
                break;
            case 63 :
                // java_libbash.g:1:449: SLASH
                {
                mSLASH(); 

                }
                break;
            case 64 :
                // java_libbash.g:1:455: COLON
                {
                mCOLON(); 

                }
                break;
            case 65 :
                // java_libbash.g:1:461: QMARK
                {
                mQMARK(); 

                }
                break;
            case 66 :
                // java_libbash.g:1:467: LOCAL
                {
                mLOCAL(); 

                }
                break;
            case 67 :
                // java_libbash.g:1:473: EXPORT
                {
                mEXPORT(); 

                }
                break;
            case 68 :
                // java_libbash.g:1:480: DECLARE
                {
                mDECLARE(); 

                }
                break;
            case 69 :
                // java_libbash.g:1:488: LOGICAND
                {
                mLOGICAND(); 

                }
                break;
            case 70 :
                // java_libbash.g:1:497: LOGICOR
                {
                mLOGICOR(); 

                }
                break;
            case 71 :
                // java_libbash.g:1:505: CONTINUE_LINE
                {
                mCONTINUE_LINE(); 

                }
                break;
            case 72 :
                // java_libbash.g:1:519: ESC_RPAREN
                {
                mESC_RPAREN(); 

                }
                break;
            case 73 :
                // java_libbash.g:1:530: ESC_LPAREN
                {
                mESC_LPAREN(); 

                }
                break;
            case 74 :
                // java_libbash.g:1:541: ESC_RSQUARE
                {
                mESC_RSQUARE(); 

                }
                break;
            case 75 :
                // java_libbash.g:1:553: ESC_LSQUARE
                {
                mESC_LSQUARE(); 

                }
                break;
            case 76 :
                // java_libbash.g:1:565: ESC_DOLLAR
                {
                mESC_DOLLAR(); 

                }
                break;
            case 77 :
                // java_libbash.g:1:576: ESC_TICK
                {
                mESC_TICK(); 

                }
                break;
            case 78 :
                // java_libbash.g:1:585: COMMAND_SUBSTITUTION_PAREN
                {
                mCOMMAND_SUBSTITUTION_PAREN(); 

                }
                break;
            case 79 :
                // java_libbash.g:1:612: COMMAND_SUBSTITUTION_TICK
                {
                mCOMMAND_SUBSTITUTION_TICK(); 

                }
                break;
            case 80 :
                // java_libbash.g:1:638: ESC_LT
                {
                mESC_LT(); 

                }
                break;
            case 81 :
                // java_libbash.g:1:645: ESC_GT
                {
                mESC_GT(); 

                }
                break;
            case 82 :
                // java_libbash.g:1:652: ESC
                {
                mESC(); 

                }
                break;
            case 83 :
                // java_libbash.g:1:656: UNDERSCORE
                {
                mUNDERSCORE(); 

                }
                break;
            case 84 :
                // java_libbash.g:1:667: NAME
                {
                mNAME(); 

                }
                break;
            case 85 :
                // java_libbash.g:1:672: OTHER
                {
                mOTHER(); 

                }
                break;

        }

    }


    protected DFA9 dfa9 = new DFA9(this);
    protected DFA12 dfa12 = new DFA12(this);
    static final String DFA9_eotS =
        "\1\1\1\uffff\1\6\2\uffff\1\6\3\uffff\3\6\1\uffff\3\6\1\uffff";
    static final String DFA9_eofS =
        "\21\uffff";
    static final String DFA9_minS =
        "\1\0\1\uffff\1\50\2\uffff\1\0\3\uffff\7\0\1\uffff";
    static final String DFA9_maxS =
        "\1\uffff\1\uffff\1\51\2\uffff\1\uffff\3\uffff\3\uffff\1\0\3\uffff"+
        "\1\uffff";
    static final String DFA9_acceptS =
        "\1\uffff\1\7\1\uffff\1\3\1\4\1\uffff\1\6\1\1\1\2\7\uffff\1\5";
    static final String DFA9_specialS =
        "\1\4\4\uffff\1\1\3\uffff\1\7\1\2\1\3\1\5\1\6\1\0\1\10\1\uffff}>";
    static final String[] DFA9_transitionS = {
            "\47\6\1\5\1\3\1\4\62\6\1\2\uffa3\6",
            "",
            "\1\7\1\10",
            "",
            "",
            "\47\15\1\14\1\12\1\13\62\15\1\11\uffa3\15",
            "",
            "",
            "",
            "\47\15\1\14\1\17\1\16\62\15\1\11\uffa3\15",
            "\47\15\1\14\1\12\1\13\62\15\1\11\uffa3\15",
            "\47\15\1\14\1\12\1\13\62\15\1\11\uffa3\15",
            "\1\uffff",
            "\47\15\1\14\1\12\1\13\62\15\1\11\uffa3\15",
            "\47\15\1\14\1\12\1\13\62\15\1\11\uffa3\15",
            "\47\15\1\14\1\12\1\13\62\15\1\11\uffa3\15",
            ""
    };

    static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
    static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
    static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
    static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
    static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
    static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
    static final short[][] DFA9_transition;

    static {
        int numStates = DFA9_transitionS.length;
        DFA9_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
        }
    }

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = DFA9_eot;
            this.eof = DFA9_eof;
            this.min = DFA9_min;
            this.max = DFA9_max;
            this.accept = DFA9_accept;
            this.special = DFA9_special;
            this.transition = DFA9_transition;
        }
        public String getDescription() {
            return "()+ loopback of 1097:5: ( ESC_LPAREN | ESC_RPAREN | LPAREN | RPAREN | SINGLE_QUOTED_STRING_TOKEN | . )+";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA9_14 = input.LA(1);

                        s = -1;
                        if ( (LA9_14=='\\') ) {s = 9;}

                        else if ( (LA9_14=='(') ) {s = 10;}

                        else if ( (LA9_14==')') ) {s = 11;}

                        else if ( (LA9_14=='\'') ) {s = 12;}

                        else if ( ((LA9_14>='\u0000' && LA9_14<='&')||(LA9_14>='*' && LA9_14<='[')||(LA9_14>=']' && LA9_14<='\uFFFF')) ) {s = 13;}

                        else s = 6;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA9_5 = input.LA(1);

                        s = -1;
                        if ( (LA9_5=='\\') ) {s = 9;}

                        else if ( (LA9_5=='(') ) {s = 10;}

                        else if ( (LA9_5==')') ) {s = 11;}

                        else if ( (LA9_5=='\'') ) {s = 12;}

                        else if ( ((LA9_5>='\u0000' && LA9_5<='&')||(LA9_5>='*' && LA9_5<='[')||(LA9_5>=']' && LA9_5<='\uFFFF')) ) {s = 13;}

                        else s = 6;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA9_10 = input.LA(1);

                        s = -1;
                        if ( (LA9_10=='\\') ) {s = 9;}

                        else if ( (LA9_10=='(') ) {s = 10;}

                        else if ( (LA9_10==')') ) {s = 11;}

                        else if ( (LA9_10=='\'') ) {s = 12;}

                        else if ( ((LA9_10>='\u0000' && LA9_10<='&')||(LA9_10>='*' && LA9_10<='[')||(LA9_10>=']' && LA9_10<='\uFFFF')) ) {s = 13;}

                        else s = 6;

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA9_11 = input.LA(1);

                        s = -1;
                        if ( (LA9_11=='\\') ) {s = 9;}

                        else if ( (LA9_11=='(') ) {s = 10;}

                        else if ( (LA9_11==')') ) {s = 11;}

                        else if ( (LA9_11=='\'') ) {s = 12;}

                        else if ( ((LA9_11>='\u0000' && LA9_11<='&')||(LA9_11>='*' && LA9_11<='[')||(LA9_11>=']' && LA9_11<='\uFFFF')) ) {s = 13;}

                        else s = 6;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA9_0 = input.LA(1);

                        s = -1;
                        if ( (LA9_0=='\\') ) {s = 2;}

                        else if ( (LA9_0=='(') ) {s = 3;}

                        else if ( (LA9_0==')') ) {s = 4;}

                        else if ( (LA9_0=='\'') ) {s = 5;}

                        else if ( ((LA9_0>='\u0000' && LA9_0<='&')||(LA9_0>='*' && LA9_0<='[')||(LA9_0>=']' && LA9_0<='\uFFFF')) ) {s = 6;}

                        else s = 1;

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA9_12 = input.LA(1);

                         
                        int index9_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (( !double_quoted )) ) {s = 16;}

                        else if ( (true) ) {s = 6;}

                         
                        input.seek(index9_12);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA9_13 = input.LA(1);

                        s = -1;
                        if ( (LA9_13=='\\') ) {s = 9;}

                        else if ( (LA9_13=='(') ) {s = 10;}

                        else if ( (LA9_13==')') ) {s = 11;}

                        else if ( (LA9_13=='\'') ) {s = 12;}

                        else if ( ((LA9_13>='\u0000' && LA9_13<='&')||(LA9_13>='*' && LA9_13<='[')||(LA9_13>=']' && LA9_13<='\uFFFF')) ) {s = 13;}

                        else s = 6;

                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA9_9 = input.LA(1);

                        s = -1;
                        if ( (LA9_9==')') ) {s = 14;}

                        else if ( (LA9_9=='\\') ) {s = 9;}

                        else if ( (LA9_9=='(') ) {s = 15;}

                        else if ( (LA9_9=='\'') ) {s = 12;}

                        else if ( ((LA9_9>='\u0000' && LA9_9<='&')||(LA9_9>='*' && LA9_9<='[')||(LA9_9>=']' && LA9_9<='\uFFFF')) ) {s = 13;}

                        else s = 6;

                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA9_15 = input.LA(1);

                        s = -1;
                        if ( (LA9_15=='\\') ) {s = 9;}

                        else if ( (LA9_15=='(') ) {s = 10;}

                        else if ( (LA9_15==')') ) {s = 11;}

                        else if ( (LA9_15=='\'') ) {s = 12;}

                        else if ( ((LA9_15>='\u0000' && LA9_15<='&')||(LA9_15>='*' && LA9_15<='[')||(LA9_15>=']' && LA9_15<='\uFFFF')) ) {s = 13;}

                        else s = 6;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 9, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA12_eotS =
        "\1\uffff\1\61\1\60\1\65\1\uffff\11\72\3\uffff\1\115\2\uffff\1\120"+
        "\1\122\1\uffff\1\126\1\130\3\uffff\1\137\1\uffff\1\142\1\144\1\146"+
        "\1\150\1\153\1\uffff\1\167\1\uffff\1\171\1\72\2\uffff\1\177\3\uffff"+
        "\1\72\1\u0083\3\uffff\1\61\1\65\3\uffff\1\71\2\uffff\1\u0086\4\71"+
        "\1\u008c\1\71\1\u008e\1\u008f\5\71\24\uffff\1\u0096\3\uffff\1\u0098"+
        "\31\uffff\1\71\10\uffff\2\71\1\uffff\5\71\1\uffff\1\u00a2\2\uffff"+
        "\5\71\5\uffff\1\71\1\u00a9\1\u00aa\1\71\1\u00ac\1\u00ad\1\u00ae"+
        "\1\71\1\uffff\1\71\1\u00b1\1\u00b2\3\71\2\uffff\1\71\3\uffff\2\71"+
        "\2\uffff\1\u00b9\1\u00ba\1\u00bb\1\71\1\u00bd\1\u00be\3\uffff\1"+
        "\u00bf\3\uffff";
    static final String DFA12_eofS =
        "\u00c0\uffff";
    static final String DFA12_minS =
        "\1\0\1\11\2\12\1\uffff\11\60\3\uffff\1\50\2\uffff\1\0\1\50\1\uffff"+
        "\1\56\1\52\3\uffff\1\46\1\uffff\1\74\1\76\1\73\1\174\1\12\1\uffff"+
        "\1\0\1\uffff\2\60\2\uffff\1\45\3\uffff\2\60\3\uffff\1\11\1\12\1"+
        "\uffff\1\12\1\uffff\1\163\2\uffff\1\60\1\143\1\151\1\141\1\160\1"+
        "\60\1\162\2\60\1\154\1\145\1\155\1\164\1\151\24\uffff\1\76\3\uffff"+
        "\1\74\25\uffff\1\0\3\uffff\1\143\10\uffff\2\145\1\uffff\1\154\1"+
        "\146\1\145\1\143\1\157\1\uffff\1\60\2\uffff\1\145\1\156\1\145\1"+
        "\151\1\154\5\uffff\1\141\2\60\1\141\3\60\1\162\1\uffff\1\143\2\60"+
        "\1\154\1\145\1\154\2\uffff\1\162\3\uffff\2\164\2\uffff\3\60\1\145"+
        "\2\60\3\uffff\1\60\3\uffff";
    static final String DFA12_maxS =
        "\1\uffff\1\43\1\12\1\43\1\uffff\11\172\3\uffff\1\50\2\uffff\1\uffff"+
        "\1\50\1\uffff\1\56\1\52\3\uffff\1\76\1\uffff\1\74\1\76\1\73\1\174"+
        "\1\140\1\uffff\1\uffff\1\uffff\1\71\1\172\2\uffff\1\45\3\uffff\2"+
        "\172\3\uffff\2\43\1\uffff\1\12\1\uffff\1\163\2\uffff\1\172\1\143"+
        "\1\163\1\141\1\160\1\172\1\162\2\172\1\154\1\145\1\155\1\164\1\151"+
        "\24\uffff\1\76\3\uffff\1\74\25\uffff\1\0\3\uffff\1\143\10\uffff"+
        "\2\145\1\uffff\1\154\1\146\1\145\1\143\1\157\1\uffff\1\172\2\uffff"+
        "\1\145\1\156\1\145\1\151\1\154\5\uffff\1\141\2\172\1\141\3\172\1"+
        "\162\1\uffff\1\143\2\172\1\154\1\145\1\154\2\uffff\1\162\3\uffff"+
        "\2\164\2\uffff\3\172\1\145\2\172\3\uffff\1\172\3\uffff";
    static final String DFA12_acceptS =
        "\4\uffff\1\2\11\uffff\1\21\1\22\1\24\1\uffff\1\27\1\30\2\uffff\1"+
        "\33\2\uffff\1\37\1\40\1\41\1\uffff\1\44\5\uffff\1\61\1\uffff\1\64"+
        "\2\uffff\1\72\1\74\1\uffff\1\77\1\100\1\101\2\uffff\1\125\1\65\1"+
        "\1\2\uffff\1\66\1\uffff\1\2\1\uffff\1\124\1\71\16\uffff\1\21\1\22"+
        "\1\24\1\26\1\25\1\27\1\30\1\31\1\117\1\32\1\116\1\33\1\35\1\34\1"+
        "\42\1\36\1\37\1\40\1\41\1\51\1\uffff\1\105\1\43\1\44\1\uffff\1\45"+
        "\1\50\1\46\1\55\1\54\1\106\1\56\1\57\1\60\1\122\1\113\1\107\1\110"+
        "\1\114\1\111\1\115\1\120\1\112\1\121\1\61\1\63\1\uffff\1\64\1\67"+
        "\1\70\1\uffff\1\72\1\74\1\76\1\75\1\77\1\100\1\101\1\123\2\uffff"+
        "\1\4\5\uffff\1\11\1\uffff\1\13\1\14\5\uffff\1\53\1\52\1\73\1\47"+
        "\1\62\10\uffff\1\12\6\uffff\1\3\1\5\1\uffff\1\6\1\7\1\10\2\uffff"+
        "\1\16\1\23\6\uffff\1\17\1\20\1\102\1\uffff\1\103\1\15\1\104";
    static final String DFA12_specialS =
        "\1\4\1\0\1\uffff\1\7\20\uffff\1\1\1\11\14\uffff\1\2\1\uffff\1\3"+
        "\16\uffff\1\10\1\6\102\uffff\1\5\110\uffff}>";
    static final String[] DFA12_transitionS = {
            "\11\60\1\1\1\3\2\60\1\2\22\60\1\1\1\4\1\43\1\51\1\25\1\52\1"+
            "\34\1\44\1\21\1\20\1\30\1\33\1\45\1\32\1\27\1\53\12\46\1\54"+
            "\1\40\1\36\1\31\1\37\1\55\1\26\32\56\1\22\1\42\1\23\1\35\1\57"+
            "\1\24\2\56\1\5\1\6\1\7\1\10\2\56\1\11\2\56\1\47\6\56\1\12\1"+
            "\13\1\14\1\56\1\15\3\56\1\16\1\41\1\17\1\50\uff81\60",
            "\1\63\26\uffff\1\63\2\uffff\1\62",
            "\1\64",
            "\1\64\2\uffff\1\66\25\uffff\1\62",
            "",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\70\31\71",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\4\71\1\74\11\71\1"+
            "\73\13\71",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\13\71\1\75\6\71\1"+
            "\76\4\71\1\77\2\71",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\10\71\1\100\5\71"+
            "\1\101\13\71",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\5\71\1\102\7\71\1"+
            "\103\14\71",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\4\71\1\104\25\71",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\7\71\1\105\1\106"+
            "\21\71",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\15\71\1\107\14\71",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\7\71\1\110\22\71",
            "",
            "",
            "",
            "\1\114",
            "",
            "",
            "\0\121",
            "\1\123",
            "",
            "\1\125",
            "\1\127",
            "",
            "",
            "",
            "\1\136\25\uffff\1\134\1\uffff\1\135",
            "",
            "\1\141",
            "\1\143",
            "\1\145",
            "\1\147",
            "\1\155\2\uffff\1\155\24\uffff\1\151\1\uffff\1\157\2\uffff\1"+
            "\152\1\160\1\156\22\uffff\1\162\1\uffff\1\164\34\uffff\1\154"+
            "\1\uffff\1\163\2\uffff\1\161",
            "",
            "\0\166",
            "",
            "\12\172",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\16\71\1\173\13\71",
            "",
            "",
            "\1\176",
            "",
            "",
            "",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32\71",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32\71",
            "",
            "",
            "",
            "\1\63\26\uffff\1\63\2\uffff\1\62",
            "\1\64\2\uffff\1\66\25\uffff\1\62",
            "",
            "\1\64",
            "",
            "\1\u0084",
            "",
            "",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\15\71\1\u0085\14"+
            "\71",
            "\1\u0087",
            "\1\u0088\11\uffff\1\u0089",
            "\1\u008a",
            "\1\u008b",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32\71",
            "\1\u008d",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32\71",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32\71",
            "\1\u0090",
            "\1\u0091",
            "\1\u0092",
            "\1\u0093",
            "\1\u0094",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u0095",
            "",
            "",
            "",
            "\1\u0097",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "\1\u009a",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u009b",
            "\1\u009c",
            "",
            "\1\u009d",
            "\1\u009e",
            "\1\u009f",
            "\1\u00a0",
            "\1\u00a1",
            "",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32\71",
            "",
            "",
            "\1\u00a3",
            "\1\u00a4",
            "\1\u00a5",
            "\1\u00a6",
            "\1\u00a7",
            "",
            "",
            "",
            "",
            "",
            "\1\u00a8",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32\71",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32\71",
            "\1\u00ab",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32\71",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32\71",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32\71",
            "\1\u00af",
            "",
            "\1\u00b0",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32\71",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32\71",
            "\1\u00b3",
            "\1\u00b4",
            "\1\u00b5",
            "",
            "",
            "\1\u00b6",
            "",
            "",
            "",
            "\1\u00b7",
            "\1\u00b8",
            "",
            "",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32\71",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32\71",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32\71",
            "\1\u00bc",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32\71",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32\71",
            "",
            "",
            "",
            "\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32\71",
            "",
            "",
            ""
    };

    static final short[] DFA12_eot = DFA.unpackEncodedString(DFA12_eotS);
    static final short[] DFA12_eof = DFA.unpackEncodedString(DFA12_eofS);
    static final char[] DFA12_min = DFA.unpackEncodedStringToUnsignedChars(DFA12_minS);
    static final char[] DFA12_max = DFA.unpackEncodedStringToUnsignedChars(DFA12_maxS);
    static final short[] DFA12_accept = DFA.unpackEncodedString(DFA12_acceptS);
    static final short[] DFA12_special = DFA.unpackEncodedString(DFA12_specialS);
    static final short[][] DFA12_transition;

    static {
        int numStates = DFA12_transitionS.length;
        DFA12_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA12_transition[i] = DFA.unpackEncodedString(DFA12_transitionS[i]);
        }
    }

    class DFA12 extends DFA {

        public DFA12(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 12;
            this.eot = DFA12_eot;
            this.eof = DFA12_eof;
            this.min = DFA12_min;
            this.max = DFA12_max;
            this.accept = DFA12_accept;
            this.special = DFA12_special;
            this.transition = DFA12_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( COMMENT | BANG | CASE | DO | DONE | ELIF | ELSE | ESAC | FI | FOR | IF | IN | SELECT | THEN | UNTIL | WHILE | LBRACE | RBRACE | TIME | RPAREN | LPAREN | LLPAREN | LSQUARE | RSQUARE | TICK | DOLLAR | AT | DOT | DOTDOT | TIMES | EQUALS | MINUS | PLUS | EXP | AMP | CARET | LESS_THAN | GREATER_THAN | LSHIFT | RSHIFT | AMP_LESS_THAN | AMP_GREATER_THAN | AMP_RSHIFT | SEMIC | DOUBLE_SEMIC | PIPE | ESC_DQUOTE | ESC_SQUOTE | DQUOTE | SQUOTE | SINGLE_QUOTED_STRING_TOKEN | COMMA | BLANK | EOL | DIGIT | NUMBER | LETTER | TILDE | HERE_STRING_OP | POUND | PCT | PCTPCT | SLASH | COLON | QMARK | LOCAL | EXPORT | DECLARE | LOGICAND | LOGICOR | CONTINUE_LINE | ESC_RPAREN | ESC_LPAREN | ESC_RSQUARE | ESC_LSQUARE | ESC_DOLLAR | ESC_TICK | COMMAND_SUBSTITUTION_PAREN | COMMAND_SUBSTITUTION_TICK | ESC_LT | ESC_GT | ESC | UNDERSCORE | NAME | OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA12_1 = input.LA(1);

                         
                        int index12_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA12_1=='#') && (( !double_quoted ))) {s = 50;}

                        else if ( (LA12_1=='\t'||LA12_1==' ') ) {s = 51;}

                        else s = 49;

                         
                        input.seek(index12_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA12_20 = input.LA(1);

                        s = -1;
                        if ( ((LA12_20>='\u0000' && LA12_20<='\uFFFF')) ) {s = 81;}

                        else s = 80;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA12_34 = input.LA(1);

                         
                        int index12_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA12_34=='\"') ) {s = 105;}

                        else if ( (LA12_34=='\'') && (( !double_quoted ))) {s = 106;}

                        else if ( (LA12_34=='[') ) {s = 108;}

                        else if ( (LA12_34=='\n'||LA12_34=='\r') ) {s = 109;}

                        else if ( (LA12_34==')') ) {s = 110;}

                        else if ( (LA12_34=='$') ) {s = 111;}

                        else if ( (LA12_34=='(') ) {s = 112;}

                        else if ( (LA12_34=='`') ) {s = 113;}

                        else if ( (LA12_34=='<') ) {s = 114;}

                        else if ( (LA12_34==']') ) {s = 115;}

                        else if ( (LA12_34=='>') ) {s = 116;}

                        else s = 107;

                         
                        input.seek(index12_34);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA12_36 = input.LA(1);

                         
                        int index12_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA12_36>='\u0000' && LA12_36<='\uFFFF')) && (( !double_quoted ))) {s = 118;}

                        else s = 119;

                         
                        input.seek(index12_36);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA12_0 = input.LA(1);

                        s = -1;
                        if ( (LA12_0=='\t'||LA12_0==' ') ) {s = 1;}

                        else if ( (LA12_0=='\r') ) {s = 2;}

                        else if ( (LA12_0=='\n') ) {s = 3;}

                        else if ( (LA12_0=='!') ) {s = 4;}

                        else if ( (LA12_0=='c') ) {s = 5;}

                        else if ( (LA12_0=='d') ) {s = 6;}

                        else if ( (LA12_0=='e') ) {s = 7;}

                        else if ( (LA12_0=='f') ) {s = 8;}

                        else if ( (LA12_0=='i') ) {s = 9;}

                        else if ( (LA12_0=='s') ) {s = 10;}

                        else if ( (LA12_0=='t') ) {s = 11;}

                        else if ( (LA12_0=='u') ) {s = 12;}

                        else if ( (LA12_0=='w') ) {s = 13;}

                        else if ( (LA12_0=='{') ) {s = 14;}

                        else if ( (LA12_0=='}') ) {s = 15;}

                        else if ( (LA12_0==')') ) {s = 16;}

                        else if ( (LA12_0=='(') ) {s = 17;}

                        else if ( (LA12_0=='[') ) {s = 18;}

                        else if ( (LA12_0==']') ) {s = 19;}

                        else if ( (LA12_0=='`') ) {s = 20;}

                        else if ( (LA12_0=='$') ) {s = 21;}

                        else if ( (LA12_0=='@') ) {s = 22;}

                        else if ( (LA12_0=='.') ) {s = 23;}

                        else if ( (LA12_0=='*') ) {s = 24;}

                        else if ( (LA12_0=='=') ) {s = 25;}

                        else if ( (LA12_0=='-') ) {s = 26;}

                        else if ( (LA12_0=='+') ) {s = 27;}

                        else if ( (LA12_0=='&') ) {s = 28;}

                        else if ( (LA12_0=='^') ) {s = 29;}

                        else if ( (LA12_0=='<') ) {s = 30;}

                        else if ( (LA12_0=='>') ) {s = 31;}

                        else if ( (LA12_0==';') ) {s = 32;}

                        else if ( (LA12_0=='|') ) {s = 33;}

                        else if ( (LA12_0=='\\') ) {s = 34;}

                        else if ( (LA12_0=='\"') ) {s = 35;}

                        else if ( (LA12_0=='\'') ) {s = 36;}

                        else if ( (LA12_0==',') ) {s = 37;}

                        else if ( ((LA12_0>='0' && LA12_0<='9')) ) {s = 38;}

                        else if ( (LA12_0=='l') ) {s = 39;}

                        else if ( (LA12_0=='~') ) {s = 40;}

                        else if ( (LA12_0=='#') ) {s = 41;}

                        else if ( (LA12_0=='%') ) {s = 42;}

                        else if ( (LA12_0=='/') ) {s = 43;}

                        else if ( (LA12_0==':') ) {s = 44;}

                        else if ( (LA12_0=='?') ) {s = 45;}

                        else if ( ((LA12_0>='A' && LA12_0<='Z')||(LA12_0>='a' && LA12_0<='b')||(LA12_0>='g' && LA12_0<='h')||(LA12_0>='j' && LA12_0<='k')||(LA12_0>='m' && LA12_0<='r')||LA12_0=='v'||(LA12_0>='x' && LA12_0<='z')) ) {s = 46;}

                        else if ( (LA12_0=='_') ) {s = 47;}

                        else if ( ((LA12_0>='\u0000' && LA12_0<='\b')||(LA12_0>='\u000B' && LA12_0<='\f')||(LA12_0>='\u000E' && LA12_0<='\u001F')||(LA12_0>='\u007F' && LA12_0<='\uFFFF')) ) {s = 48;}

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA12_119 = input.LA(1);

                         
                        int index12_119 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (( double_quoted )) ) {s = 153;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index12_119);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA12_52 = input.LA(1);

                         
                        int index12_52 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA12_52=='\r') ) {s = 54;}

                        else if ( (LA12_52=='\n') ) {s = 52;}

                        else if ( (LA12_52=='#') && (( !double_quoted ))) {s = 50;}

                        else s = 53;

                         
                        input.seek(index12_52);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA12_3 = input.LA(1);

                         
                        int index12_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA12_3=='\r') ) {s = 54;}

                        else if ( (LA12_3=='\n') ) {s = 52;}

                        else if ( (LA12_3=='#') && (( !double_quoted ))) {s = 50;}

                        else s = 53;

                         
                        input.seek(index12_3);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA12_51 = input.LA(1);

                         
                        int index12_51 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA12_51=='#') && (( !double_quoted ))) {s = 50;}

                        else if ( (LA12_51=='\t'||LA12_51==' ') ) {s = 51;}

                        else s = 49;

                         
                        input.seek(index12_51);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA12_21 = input.LA(1);

                         
                        int index12_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA12_21=='(') && ((LA(1) == '$' && LA(2) == '(' && LA(3) != '('))) {s = 83;}

                        else s = 82;

                         
                        input.seek(index12_21);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 12, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}
