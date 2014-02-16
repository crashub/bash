/*
   Please use git log for copyright holder and year information

   This file is part of libbash.

   libbash is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 2 of the License, or
   (at your option) any later version.

   libbash is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with libbash.  If not, see <http://www.gnu.org/licenses/>.
*/

#ifdef OUTPUT_C
grammar libbash;
#else
grammar java_libbash;
#endif

options
{
	output = AST;
#ifdef OUTPUT_C
	language  = C;
	ASTLabelType  = pANTLR3_BASE_TREE;
#else
	language = Java;
	ASTLabelType = CommonTree;
#endif
}

tokens{
	ANSI_C_QUOTING;
	ARG;
	ARRAY;
	ARRAY_SIZE;
	BRACE_EXP;
	COMMAND_SUB;
	CASE_PATTERN;
	CASE_COMMAND;
	SUBSHELL;
	CURRENT_SHELL;
	COMPOUND_COND;
	CFOR;
	FOR_INIT;
	FOR_COND;
	FOR_MOD;
	IF_STATEMENT;
	OP;
	PRE_INCR;
	PRE_DECR;
	POST_INCR;
	POST_DECR;
	PROCESS_SUBSTITUTION;
	VAR_REF;
	NEGATION;
	LIST;
	STRING;
	COMMAND;
	FILE_DESCRIPTOR;
	FILE_DESCRIPTOR_MOVE;
	REDIR;
	ARITHMETIC_CONDITION;
	ARITHMETIC_EXPRESSION;
	ARITHMETIC;
	KEYWORD_TEST;
	BUILTIN_TEST;
	MATCH_ANY_EXCEPT;
	EXTENDED_MATCH_EXACTLY_ONE;
	EXTENDED_MATCH_AT_MOST_ONE;
	EXTENDED_MATCH_NONE;
	EXTENDED_MATCH_ANY;
	EXTENDED_MATCH_AT_LEAST_ONE;
	BRANCH;
	MATCH_PATTERN;
	MATCH_REGULAR_EXPRESSION;
	ESCAPED_CHAR;
	NOT_MATCH_PATTERN;
	MATCH_ANY;
	MATCH_ANY_EXCEPT;
	MATCH_ALL;
	MATCH_ONE;
	CHARACTER_CLASS;
	EQUIVALENCE_CLASS;
	COLLATING_SYMBOL;
	DOUBLE_QUOTED_STRING;
	SINGLE_QUOTED_STRING;
	VARIABLE_DEFINITIONS;

	USE_DEFAULT_WHEN_UNSET;
	USE_ALTERNATE_WHEN_UNSET;
	DISPLAY_ERROR_WHEN_UNSET;
	ASSIGN_DEFAULT_WHEN_UNSET;
	USE_DEFAULT_WHEN_UNSET_OR_NULL;
	USE_ALTERNATE_WHEN_UNSET_OR_NULL;
	DISPLAY_ERROR_WHEN_UNSET_OR_NULL;
	ASSIGN_DEFAULT_WHEN_UNSET_OR_NULL;
	OFFSET;
	LIST_EXPAND;
	REPLACE_FIRST;
	REPLACE_ALL;
	REPLACE_AT_START;
	REPLACE_AT_END;
	LAZY_REMOVE_AT_START;
	LAZY_REMOVE_AT_END;
	EMPTY_EXPANSION_VALUE;

	PLUS_SIGN;
	MINUS_SIGN;
	PLUS_ASSIGN;
	MINUS_ASSIGN;
	DIVIDE_ASSIGN;
	MUL_ASSIGN;
	MOD_ASSIGN;
	LSHIFT_ASSIGN;
	RSHIFT_ASSIGN;
	AND_ASSIGN;
	XOR_ASSIGN;
	OR_ASSIGN;
	LEQ;
	GEQ;

	NOT_EQUALS;
	EQUALS_TO;
	BUILTIN_LOGIC_AND;
	BUILTIN_LOGIC_OR;

	FUNCTION;
}

@lexer::context
{
#ifdef OUTPUT_C
	bool double_quoted;
	int paren_level;
#endif
}

@lexer::apifuncs
{
#ifdef OUTPUT_C
	ctx->double_quoted = false;
	ctx->paren_level = 0;
#endif
}

@lexer::members
{
#ifndef OUTPUT_C
	boolean double_quoted = false;
	int paren_level = 0;
	int LA(int index) {
		return input.LA(index);
	}
#endif
}
#ifdef OUTPUT_C
@includes {
	C_INCLUDE #include <iostream>
	C_INCLUDE #include <string>
}
@postinclude {
	C_INCLUDE #include <boost/numeric/conversion/cast.hpp>

	C_INCLUDE #include "exceptions.h"
}
@lexer::postinclude {
	#define double_quoted ctx->double_quoted
	#define paren_level ctx->paren_level
}
#endif
@members
{
#ifdef OUTPUT_C
	static std::string get_string(pANTLR3_COMMON_TOKEN token)
	{
		if(!token || !token->start)
			return "";
		// Use reinterpret_cast here because we have to cast C code.
		// The real type here is int64_t which is used as a pointer.
		// token->stop - token->start + 1 should be bigger than 0.
		return std::string(reinterpret_cast<const char *>(token->start),
			boost::numeric_cast<unsigned>(token->stop - token->start + 1));
	}

	static bool is_here_end(plibbashParser ctx, const std::string& here_document_word, int number_of_tokens_in_word)
	{
		std::string word;
		for(int i = 1; i <= number_of_tokens_in_word; ++i)
			word += get_string(LT(i));
		return (word == here_document_word);
	}

	static void free_here_document(plibbashParser_here_document_SCOPE scope)
	{
		(&(scope->here_document_word))->std::string::~string();
	}
#else
	boolean is_here_end(String here_document_word, int number_of_tokens) {
		String word = "";
		for(int i = 1; i <= number_of_tokens; ++i)
			word += input.LT(i).getText();
		return (word.equals(here_document_word));
	}

	String get_string(Token token) {
		return token.getText();
	}

	Token LT(int index) {
		return input.LT(index);
	}

	int LA(int index) {
		return input.LA(index);
	}
#endif
#ifdef OUTPUT_C
	bool is_special_token(int token)
#else
	boolean is_special_token(int token)
#endif
	{
		return token == AMP
		||token == BLANK
		// for bash redirection
		||token == LESS_THAN
		||token == GREATER_THAN
		||token == RSHIFT
		||token == AMP_LESS_THAN
		||token == AMP_GREATER_THAN
		||token == AMP_RSHIFT
		// for end of command
		||token == SEMIC
		||token == EOL
		// for sub shell
		||token == LPAREN
		||token == RPAREN
		// for case statement
		||token == DOUBLE_SEMIC
		// for logical operator
		||token == LOGICAND
		||token == LOGICOR
		// for pipeline
		||token == PIPE
		// for document and here string
		||token == HERE_STRING_OP
		||token == LSHIFT;
	}
}

start
	:	((POUND) =>first_line_comment)? EOL* BLANK? command_list BLANK? (SEMIC|AMP|EOL)? EOF -> command_list;

first_line_comment
	:	POUND ~(EOL)* EOL;

command_list
	:	list_level_2 -> ^(LIST list_level_2);
list_level_1
	:	pipeline (BLANK!?(LOGICAND^|LOGICOR^)(BLANK!|EOL!)* pipeline)*;
list_level_2
	:	list_level_1 (BLANK!? command_separator (BLANK!? EOL!)* BLANK!? list_level_1)*;
command_separator
	:	SEMIC!
	|	AMP^
	|	EOL!;
pipeline
	:	time? ((BANG) => (BANG BLANK!))? command^ (BLANK!? PIPE^ wspace!? command)*;

time
	:	TIME^ BLANK! ((time_posix) => time_posix)?;
time_posix
	:	MINUS! LETTER BLANK!;

redirection
	:	redirection_atom+;
redirection_atom
	:	redirection_operator redirection_destination -> ^(REDIR redirection_operator redirection_destination)
	|	BLANK! process_substitution
	|	here_string;

process_substitution
	:	(dir=LESS_THAN|dir=GREATER_THAN)LPAREN BLANK* command_list BLANK* RPAREN
			-> ^(PROCESS_SUBSTITUTION $dir command_list);

redirection_destination
	:	(BLANK? file_descriptor) => BLANK!? file_descriptor
	|	BLANK! process_substitution
	|	BLANK!? string_expr;
file_descriptor
	:	DIGIT -> ^(FILE_DESCRIPTOR DIGIT)
	|	DIGIT MINUS -> ^(FILE_DESCRIPTOR_MOVE DIGIT);

here_string
	:	BLANK!? HERE_STRING_OP^ BLANK!? (string_expr) => string_expr;

here_document
#ifdef OUTPUT_C
scope {
	std::string here_document_word;
	int number_of_tokens;
}
@init {
	// http://antlr.1301665.n2.nabble.com/C-target-initialization-of-return-scope-structures-td5078478.html
	new (&($here_document::here_document_word)) std::string;
	$here_document::number_of_tokens = 0;
	ctx->plibbashParser_here_documentTop->free = &free_here_document;
}
#else
scope {
	String here_document_word;
	int number_of_tokens;
}
@init {
	$here_document::here_document_word = "";
	$here_document::number_of_tokens = 0;
}
#endif
	:	BLANK? (here_document_operator) => here_document_operator BLANK? here_document_begin
		redirection? EOL here_document_content? here_document_end
			-> ^(here_document_operator ^(STRING here_document_content?) redirection?);

here_document_operator
	:	LSHIFT
		(
			(MINUS) => MINUS -> OP["<<-"]
			| -> OP["<<"]
		);

here_document_begin
	:	(
			token=~(EOL|BLANK|LESS_THAN|HERE_STRING_OP|GREATER_THAN|RSHIFT|AMP_LESS_THAN|AMP_GREATER_THAN|AMP_RSHIFT)
			{
				if(LA(-1) != DQUOTE && LA(-1) != ESC)
				{
					$here_document::here_document_word += get_string($token);
					$here_document::number_of_tokens++;
				}
			}
		)+;
here_document_end
	:	({ $here_document::number_of_tokens != 0 }? => . { $here_document::number_of_tokens--; })+;
here_document_content
#ifdef OUTPUT_C
	:	({ !is_here_end(ctx, $here_document::here_document_word, $here_document::number_of_tokens)}? => .)+;
#else
	:	({ !is_here_end($here_document::here_document_word, $here_document::number_of_tokens)}? => .)+;
#endif

redirection_operator
	:	BLANK! DIGIT redirection_operator
	|	BLANK?
		(
			AMP_LESS_THAN -> OP["&<"]
			|	GREATER_THAN AMP -> OP[">&"]
			|	LESS_THAN AMP -> OP["<&"]
			|	LESS_THAN GREATER_THAN -> OP["<>"]
			|	RSHIFT -> OP[">>"]
			|	AMP_GREATER_THAN -> OP["&>"]
			|	AMP_RSHIFT -> OP ["&>>"]
			|	LESS_THAN -> LESS_THAN
			|	GREATER_THAN -> GREATER_THAN
		);

command
	:	command_atom
		(
			redirection here_document? -> ^(COMMAND redirection command_atom here_document?)
			|	here_document -> ^(COMMAND command_atom here_document)
			|	-> ^(COMMAND command_atom)
		);

command_atom
	:	{LA(1) == FOR|| LA(1) == SELECT|| LA(1) == IF|| LA(1) == WHILE|| LA(1) == UNTIL||
		 LA(1) == CASE|| LA(1) == LPAREN|| LA(1) == LBRACE|| LA(1) == LLPAREN|| LA(1) == LSQUARE||
#ifdef OUTPUT_C
		(LA(1) == NAME && LA(2) == BLANK && "test" == get_string(LT(1)))}? => compound_command
	|	{LA(1) == NAME && LA(2) == BLANK && "function" == get_string(LT(1))}? =>
#else
		(LA(1) == NAME && LA(2) == BLANK && "test".equals(get_string(LT(1))))}? => compound_command
	|	{LA(1) == NAME && LA(2) == BLANK && "function".equals(get_string(LT(1)))}? =>
#endif
			NAME BLANK string_expr_no_reserved_word ((BLANK? parens wspace?)|wspace) compound_command
			-> ^(FUNCTION string_expr_no_reserved_word compound_command)
	|	(name (LSQUARE|EQUALS|PLUS EQUALS)) => variable_definitions
			(
				(BLANK bash_command) => BLANK bash_command -> bash_command variable_definitions
				|	-> ^(VARIABLE_DEFINITIONS variable_definitions)
			)
	|	(EXPORT) => EXPORT BLANK builtin_variable_definition_item
			-> ^(STRING EXPORT) ^(STRING builtin_variable_definition_item)
	|	(LOCAL) => LOCAL BLANK builtin_variable_definition_item
			-> ^(STRING LOCAL) ^(STRING builtin_variable_definition_item)
	|	(DECLARE) => DECLARE BLANK builtin_variable_definition_item
			-> ^(STRING DECLARE) ^(STRING builtin_variable_definition_item)
	|	command_name
		(
			(BLANK? parens) => BLANK? parens wspace? compound_command
				-> ^(FUNCTION command_name compound_command)
			|	(
					{LA(1) == BLANK &&
					(
						!is_special_token(LA(2))
						// redirection
						&&(LA(2) != DIGIT || (LA(3) != AMP_LESS_THAN &&
											  LA(3) != AMP_GREATER_THAN &&
											  LA(3) != AMP_RSHIFT &&
											  LA(3) != GREATER_THAN &&
											  LA(3) != LESS_THAN &&
											  LA(3) != RSHIFT))
					)}? => BLANK bash_command_arguments
				)* -> command_name bash_command_arguments*
		);

command_name
	:	string_expr_no_reserved_word
	|	{LA(1) == GREATER_THAN}? => redirection_atom -> redirection_atom ^(STRING NAME);

variable_definitions
	:	variable_definition_atom ((BLANK name (LSQUARE|EQUALS|PLUS EQUALS)) => BLANK! variable_definition_atom)* ;

variable_definition_atom
	:	name LSQUARE BLANK? explicit_arithmetic BLANK? RSQUARE EQUALS string_expr?
			-> ^(EQUALS ^(name explicit_arithmetic) string_expr?)
	|	name EQUALS value? -> ^(EQUALS name value?)
	|	name PLUS EQUALS array_value -> ^(PLUS_ASSIGN name array_value)
	|	name PLUS EQUALS string_expr_part*
			-> ^(EQUALS name ^(STRING ^(VAR_REF name) string_expr_part*));
value
	:	string_expr
	|	array_value;

array_value
scope {
#ifdef OUTPUT_C
	bool array_value_end;
#else
	boolean array_value_end;
#endif
}
	:	LPAREN wspace?
		(
			RPAREN -> ^(ARRAY)
			|	{$array_value::array_value_end = false; } array_atom
				({!$array_value::array_value_end}? => wspace array_atom)*
					-> ^(ARRAY array_atom+)
		);
array_atom
	:	(
			(LSQUARE) => LSQUARE! BLANK!? explicit_arithmetic BLANK!? RSQUARE! EQUALS^ string_expr
			|	string_expr
		)
		(
			(wspace RPAREN) => wspace! RPAREN! {$array_value::array_value_end = true; }
			|	(RPAREN) => RPAREN! {$array_value::array_value_end = true; }
			|
		);

builtin_variable_definition_item
scope {
	int parens;
#ifdef OUTPUT_C
	bool dquotes;
#else
	boolean dquotes;
#endif
}
@init {
	$builtin_variable_definition_item::parens = 0;
	$builtin_variable_definition_item::dquotes = false;
}
	:	(
			(LPAREN) => LPAREN { ++$builtin_variable_definition_item::parens; }
			|(RPAREN) => RPAREN { --$builtin_variable_definition_item::parens; }
			|(DQUOTE) => DQUOTE { $builtin_variable_definition_item::dquotes = ! $builtin_variable_definition_item::dquotes; }
			|(~(EOL|SEMIC)) => expansion_base
			| {LA(1) == SEMIC && $builtin_variable_definition_item::dquotes}? => SEMIC
			| {LA(1) == EOL && $builtin_variable_definition_item::parens > 0 || $builtin_variable_definition_item::dquotes}? => EOL
		)+;

#ifdef OUTPUT_C
builtin_variable_definitions[bool local]
	:	{$local}? => (builtin_variable_definition_atom) (BLANK builtin_variable_definition_atom)*
			-> ^(LIST ^(COMMAND ^(VARIABLE_DEFINITIONS LOCAL builtin_variable_definition_atom+)))
	|	{!$local}? => (builtin_variable_definition_atom) (BLANK builtin_variable_definition_atom)*
			-> ^(LIST ^(COMMAND ^(VARIABLE_DEFINITIONS builtin_variable_definition_atom+)));
#else
builtin_variable_definitions
	:	(builtin_variable_definition_atom) (BLANK builtin_variable_definition_atom)*
			-> ^(LIST ^(COMMAND ^(VARIABLE_DEFINITIONS builtin_variable_definition_atom+)));
#endif

builtin_variable_definition_atom
	:	variable_definition_atom
	// We completely ignore the options for export, local and readonly for now
	|	(MINUS LETTER BLANK) => MINUS LETTER ->
	|	name -> ^(EQUALS name ^(STRING ^(VAR_REF name)));

bash_command
	:	string_expr_no_reserved_word ((BLANK bash_command_arguments) => BLANK! bash_command_arguments)*;

bash_command_arguments
	:	bash_command_argument_atom+ -> ^(STRING bash_command_argument_atom+);
// TODO support brace expansion and braces
bash_command_argument_atom
	:	(LBRACE) =>
		(
			(brace_expansion) => brace_expansion
			|LBRACE
		)
	|	RBRACE
	|	string_expr_part;

parens
	:	LPAREN BLANK? RPAREN;

compound_command
	:	for_expr
	|	select_expr
	|	if_expr
	|	while_expr
	|	until_expr
	|	case_expr
	|	subshell
	|	current_shell
	|	arithmetic_expression
	|	condition_comparison;

semiel
	:	BLANK? SEMIC wspace?
	|	BLANK? EOL wspace?;

for_expr
	:	FOR BLANK?
		(
			name
			(
				wspace IN for_each_value* BLANK? (SEMIC|EOL) wspace?
				| wspace? SEMIC wspace?
				| wspace
			) DO wspace command_list semiel DONE -> ^(FOR name for_each_value* command_list)
			|	LLPAREN EOL?
				// initilization
				(BLANK? init=arithmetics BLANK?|BLANK)?
				// condition
				(SEMIC (BLANK? fcond=arithmetics BLANK?|BLANK)? SEMIC|DOUBLE_SEMIC)
				// modification
				(BLANK? mod=arithmetics)? wspace? RPAREN RPAREN semiel DO wspace command_list semiel DONE
					-> ^(CFOR ^(FOR_INIT $init)? ^(FOR_COND $fcond)? command_list ^(FOR_MOD $mod)?)
		);
for_each_value
	:	{LA(1) == BLANK && LA(2) != EOL && LA(2) != SEMIC && LA(2) != DO}?
			=> (BLANK! string_expr);

select_expr
	:	SELECT BLANK name (wspace IN BLANK string_expr)? semiel DO wspace command_list semiel DONE
			-> ^(SELECT name string_expr? command_list) ;
if_expr
	:	IF wspace ag=command_list semiel THEN wspace iflist=command_list semiel
		(elif_expr)*
		(ELSE wspace else_list=command_list semiel)? FI
			-> ^(IF_STATEMENT ^(IF $ag $iflist) (elif_expr)* ^(ELSE $else_list)?);
elif_expr
	:	ELIF BLANK ag=command_list semiel THEN wspace iflist=command_list semiel
			-> ^(IF["if"] $ag $iflist);
while_expr
	:	WHILE wspace? istrue=command_list semiel DO wspace dothis=command_list semiel DONE
			-> ^(WHILE $istrue $dothis);
until_expr
	:	UNTIL wspace? istrue=command_list semiel DO wspace dothis=command_list semiel DONE
			-> ^(UNTIL $istrue $dothis);

case_expr
	:	CASE BLANK string_expr wspace IN case_body -> ^(CASE string_expr case_body);
case_body
scope {
#ifdef OUTPUT_C
	bool case_end;
#else
	boolean case_end;
#endif
}
	:	{$case_body::case_end = false;}
		(
			(wspace ESAC) => (wspace ESAC) -> ^(CASE_PATTERN)
			|({!$case_body::case_end}? => case_statement)+ -> case_statement+
		);
case_statement
	:	wspace? (LPAREN BLANK?)? extended_pattern (BLANK? PIPE BLANK? extended_pattern)* BLANK? RPAREN
		(wspace command_list)?
		(
			wspace? DOUBLE_SEMIC ((wspace ESAC) => wspace ESAC {$case_body::case_end = true;})?
			|wspace ESAC {$case_body::case_end = true;}
		)
			-> ^(CASE_PATTERN extended_pattern+ (CASE_COMMAND command_list)?);

subshell
	:	LPAREN wspace? command_list (BLANK? SEMIC)? wspace? RPAREN -> ^(SUBSHELL command_list);

current_shell
	:	LBRACE wspace command_list semiel RBRACE -> ^(CURRENT_SHELL command_list);

arithmetic_expression
	:	LLPAREN wspace? arithmetics wspace? RPAREN RPAREN -> ^(ARITHMETIC_EXPRESSION arithmetics);
condition_comparison
	:	condition_expr -> ^(COMPOUND_COND condition_expr);

condition_expr
	:	LSQUARE LSQUARE wspace keyword_condition wspace RSQUARE RSQUARE -> ^(KEYWORD_TEST keyword_condition)
	|	LSQUARE wspace builtin_condition wspace RSQUARE -> ^(BUILTIN_TEST builtin_condition)
#ifdef OUTPUT_C
	|	{LA(1) == NAME && LA(2) == BLANK && get_string(LT(1)) == "test"}? => NAME wspace? builtin_condition-> ^(BUILTIN_TEST builtin_condition);
#else
	|	{LA(1) == NAME && LA(2) == BLANK && "test".equals(get_string(LT(1)))}? => NAME wspace? builtin_condition-> ^(BUILTIN_TEST builtin_condition);
#endif

keyword_condition_and
	:	keyword_condition_primary ( wspace!? LOGICAND^ wspace!? keyword_condition_primary)*;
keyword_condition
	:	keyword_condition_and ( wspace!? LOGICOR^ wspace!? keyword_condition_and)*;
keyword_negation_primary
	:	BANG BLANK keyword_condition_primary -> ^(NEGATION keyword_condition_primary);
keyword_condition_primary
	:	LPAREN! BLANK!? keyword_condition BLANK!? RPAREN!
	|	(BANG) => keyword_negation_primary
	|	(unary_operator) => keyword_condition_unary
	|	keyword_condition_binary;
keyword_condition_unary
	:	unary_operator^ BLANK! condition_part;
keyword_condition_binary
	:	condition_part
		(
			(BLANK EQUALS TILDE) => BLANK EQUALS TILDE BLANK bash_pattern_part
				-> ^(MATCH_REGULAR_EXPRESSION condition_part ^(STRING bash_pattern_part))
			|	keyword_binary_string_operator right=condition_part
					-> ^(keyword_binary_string_operator condition_part $right)
			|	BLANK (BANG EQUALS) BLANK extended_pattern_match+
					-> ^(NOT_MATCH_PATTERN condition_part ^(STRING extended_pattern_match+))
			|	BLANK (EQUALS EQUALS) BLANK extended_pattern_match+
					-> ^(MATCH_PATTERN condition_part ^(STRING extended_pattern_match+))
			|	-> condition_part
		);
bash_pattern_part
scope {
	int parens;
#ifdef OUTPUT_C
	bool quoted;
#else
	boolean quoted;
#endif
}
@init {
	$bash_pattern_part::parens = 0;
	$bash_pattern_part::quoted = false;
}
	:(
		DQUOTE! { $bash_pattern_part::quoted = !$bash_pattern_part::quoted; }
		|	{$bash_pattern_part::quoted}? => preserved_tokens
		|	(ESC BLANK) => ESC BLANK
		|	LPAREN { if(LA(-2) != ESC) $bash_pattern_part::parens++; }
		|	LLPAREN { if(LA(-2) != ESC) $bash_pattern_part::parens += 2; }
		|	{$bash_pattern_part::parens != 0}? => RPAREN { if(LA(-2) != ESC) $bash_pattern_part::parens--; }
		|	~(BLANK|EOL|LOGICAND|LOGICOR|LPAREN|RPAREN|DQUOTE|LLPAREN)
	 )+;

preserved_tokens
	:	non_dquote -> ESCAPED_CHAR non_dquote;

non_dquote
	:	~DQUOTE;

keyword_binary_string_operator
	:	BLANK! binary_operator BLANK!
	|	BLANK! EQUALS BLANK!
	|	BLANK!? LESS_THAN BLANK!?
	|	BLANK!? GREATER_THAN BLANK!?;


builtin_condition_and
	:	builtin_condition_primary (builtin_logic_and^ BLANK! builtin_condition_primary)*;
builtin_condition
	:	builtin_condition_and (builtin_logic_or^ BLANK! builtin_condition_and)*;
builtin_negation_primary
	:	BANG BLANK builtin_condition_primary -> ^(NEGATION builtin_condition_primary);
builtin_condition_primary
	:	LPAREN! BLANK!? builtin_condition BLANK!? RPAREN!
	|	(BANG) => builtin_negation_primary
	|	(unary_operator) => builtin_condition_unary
	|	builtin_condition_binary;
builtin_condition_unary
	:	unary_operator^ BLANK! condition_part;
builtin_condition_binary
	:	condition_part (BLANK! builtin_binary_string_operator^ BLANK! condition_part)?;
builtin_binary_string_operator
	:	binary_operator
	|	(EQUALS EQUALS) => EQUALS EQUALS -> EQUALS
	|	EQUALS
	|	BANG EQUALS -> NOT_EQUALS
	|	ESC_LT
	|	ESC_GT;
builtin_logic_and
#ifdef OUTPUT_C
	:	{LA(1) == BLANK && LA(2) == MINUS && LA(3) == LETTER && "a" == get_string(LT(3))}?=> BLANK MINUS LETTER -> BUILTIN_LOGIC_AND;
#else
	:	{LA(1) == BLANK && LA(2) == MINUS && LA(3) == LETTER && "a".equals(get_string(LT(3)))}?=> BLANK MINUS LETTER -> BUILTIN_LOGIC_AND;
#endif
builtin_logic_or
#ifdef OUTPUT_C
	:	{LA(1) == BLANK && LA(2) == MINUS && LA(3) == LETTER && "o" == get_string(LT(3))}?=> BLANK MINUS LETTER -> BUILTIN_LOGIC_OR;
#else
	:	{LA(1) == BLANK && LA(2) == MINUS && LA(3) == LETTER && "o".equals(get_string(LT(3)))}?=> BLANK MINUS LETTER -> BUILTIN_LOGIC_OR;
#endif

binary_operator
	:	MINUS! NAME^;
unary_operator
	:	MINUS! LETTER;

// TODO support brace expansion
condition_part
	:	string_expr;

name
	:	NAME |	LETTER | UNDERSCORE;

num
options{k=1;}
	:	DIGIT|NUMBER;

string_expr
	:	(~POUND) => string_expr_part string_expr_part* -> ^(STRING string_expr_part+);

string_expr_part
	:	quoted_string | non_quoted_string | reserved_word;

string_expr_no_reserved_word
	:	(~POUND) =>
			(
				non_quoted_string string_expr_part* -> ^(STRING non_quoted_string string_expr_part*)
				|	quoted_string string_expr_part* -> ^(STRING quoted_string string_expr_part*)
			);

reserved_word
	:	CASE|DO|DONE|ELIF|ELSE|ESAC|FI|FOR|IF|IN|SELECT|THEN|UNTIL|WHILE|TIME;

non_quoted_string
	:	string_part
	|	variable_reference
	|	command_substitution
	|	arithmetic_expansion
	|	brace_expansion
	|	BANG
	|	DOLLAR SINGLE_QUOTED_STRING_TOKEN -> ^(ANSI_C_QUOTING SINGLE_QUOTED_STRING_TOKEN);

quoted_string
	:	double_quoted_string
	|	SINGLE_QUOTED_STRING_TOKEN -> ^(SINGLE_QUOTED_STRING SINGLE_QUOTED_STRING_TOKEN);

double_quoted_string
	:	DQUOTE ((~DQUOTE) => expansion_base)* DQUOTE -> ^(DOUBLE_QUOTED_STRING expansion_base*);

// Perform all kinds of expansions
expansion_base
	:	(DOLLAR (LBRACE|name|num|TIMES|AT|POUND|QMARK|MINUS|DOLLAR|BANG)) => variable_reference
	|	(command_substitution) => command_substitution
	|	(DOLLAR (LLPAREN|LSQUARE)) => arithmetic_expansion
	|	(ESC DQUOTE) => ESC DQUOTE -> DQUOTE
	|	(ESC TICK) => ESC TICK -> TICK
	|	(ESC DOLLAR) => ESC DOLLAR -> DOLLAR
	|	(brace_expansion) => brace_expansion
	|	(DOLLAR SINGLE_QUOTED_STRING_TOKEN) => DOLLAR SINGLE_QUOTED_STRING_TOKEN
			-> ^(ANSI_C_QUOTING SINGLE_QUOTED_STRING_TOKEN)
	|	.;

all_expansions
	:	expansion_atom+ -> ^(STRING expansion_atom+);
expansion_atom
	:	(DQUOTE) => double_quoted_string
	|	expansion_base;

string_part
	:	ns_string_part
	|	SLASH;

ns_string_part
	:	num|name|escaped_character
	|OTHER|EQUALS|PCT|PCTPCT|PLUS|MINUS|DOT|DOTDOT|COLON
	|TILDE|LSQUARE|RSQUARE|CARET|POUND|COMMA|EXPORT|LOCAL|DECLARE|AT
	// Escaped characters
	|ESC_RPAREN|ESC_LPAREN|ESC_RSQUARE|ESC_LSQUARE|ESC_DOLLAR|ESC_GT|ESC_LT|ESC_TICK|ESC_DQUOTE|ESC_SQUOTE
	// The following is for filename expansion
	|TIMES|QMARK;

escaped_character
	:	ESC
		(
			(DIGIT) => DIGIT
			|	(DIGIT DIGIT) => DIGIT DIGIT
			|	(DIGIT DIGIT DIGIT) => DIGIT DIGIT DIGIT
			|	LETTER ALPHANUM ALPHANUM?
			|	.
		);

extended_pattern_match
	:	(QMARK LPAREN) => QMARK LPAREN extended_pattern (PIPE extended_pattern)* RPAREN
			-> ^(EXTENDED_MATCH_AT_MOST_ONE extended_pattern+)
	|	(TIMES LPAREN) => TIMES LPAREN extended_pattern (PIPE extended_pattern)* RPAREN
			-> ^(EXTENDED_MATCH_ANY extended_pattern+)
	|	(PLUS LPAREN) => PLUS LPAREN extended_pattern (PIPE extended_pattern)* RPAREN
			-> ^(EXTENDED_MATCH_AT_LEAST_ONE extended_pattern+)
	|	(AT LPAREN) => AT LPAREN extended_pattern (PIPE extended_pattern)* RPAREN
			-> ^(EXTENDED_MATCH_EXACTLY_ONE extended_pattern+)
	|	(BANG LPAREN) => BANG LPAREN extended_pattern (PIPE extended_pattern)* RPAREN
			-> ^(EXTENDED_MATCH_NONE extended_pattern+)
	|	(bracket_pattern_match) => bracket_pattern_match
	|	(pattern_class_match) => pattern_class_match
	|	string_expr_part;

extended_pattern
	:	((~(RPAREN|PIPE)) => extended_pattern_match)+ -> ^(BRANCH extended_pattern_match+);

bracket_pattern_match
	:	LSQUARE! bracket_pattern_match_operator^ bracket_pattern RSQUARE!
	|	TIMES -> MATCH_ALL
	|	QMARK -> MATCH_ONE;
bracket_pattern_match_operator
	:	(BANG) => BANG -> MATCH_ANY_EXCEPT
	|	(CARET) => CARET -> MATCH_ANY_EXCEPT
	|	-> MATCH_ANY;

bracket_pattern_part
	:	(pattern_class_match) => pattern_class_match
	|	string_expr_part;

bracket_pattern
	:	((~RSQUARE) => bracket_pattern_part)+;

pattern_class_match
	:	LSQUARE COLON NAME COLON RSQUARE -> ^(CHARACTER_CLASS NAME)
	|	LSQUARE EQUALS pattern_char EQUALS RSQUARE -> ^(EQUIVALENCE_CLASS pattern_char)
	|	LSQUARE DOT NAME DOT RSQUARE -> ^(COLLATING_SYMBOL NAME);

pattern_char
	:	LETTER|DIGIT|OTHER|QMARK|COLON|AT|SEMIC|POUND|SLASH
		|BANG|TIMES|COMMA|PIPE|AMP|MINUS|PLUS|PCT|LSQUARE|RSQUARE
		|RPAREN|LPAREN|RBRACE|LBRACE|DOLLAR|TICK|DOT|LESS_THAN
		|GREATER_THAN|SQUOTE|DQUOTE|AMP_LESS_THAN|AMP_GREATER_THAN|AMP_RSHIFT;

variable_reference
	:	DOLLAR LBRACE parameter_expansion RBRACE -> ^(VAR_REF parameter_expansion)
	|	DOLLAR name -> ^(VAR_REF name)
	|	DOLLAR num -> ^(VAR_REF num)
	|	DOLLAR TIMES -> ^(VAR_REF TIMES)
	|	DOLLAR AT -> ^(VAR_REF AT)
	|	DOLLAR POUND -> ^(VAR_REF POUND)
	|	DOLLAR QMARK -> ^(VAR_REF QMARK)
	|	DOLLAR MINUS -> ^(VAR_REF MINUS)
	|	DOLLAR DOLLAR -> ^(VAR_REF DOLLAR)
	|	DOLLAR BANG -> ^(VAR_REF BANG);

parameter_expansion
	:	variable_name
		(
			(parameter_value_operator) => parameter_value_operator parameter_expansion_value
				-> ^(parameter_value_operator variable_name parameter_expansion_value)
			|	COLON BLANK?
				(
					os=explicit_arithmetic (COLON BLANK? len=explicit_arithmetic)?
					// It will make the tree parser's work easier if OFFSET is used as the root of arithmetic.
					// Otherwise, the tree parser can see several arithmetic expressions but can not tell
					// which one is for offset and which one is for length.
						-> ^(OFFSET variable_name ^(OFFSET $os) ^(OFFSET ^($len))?)
					|	COLON BLANK? len=explicit_arithmetic
						-> ^(OFFSET variable_name ^(OFFSET NUMBER["0"]) ^(OFFSET ^($len))?)
				)
			|	parameter_delete_operator parameter_delete_pattern
				-> ^(parameter_delete_operator variable_name parameter_delete_pattern)
			|	parameter_replace_operator parameter_replace_pattern (SLASH parameter_expansion_value)?
				-> ^(parameter_replace_operator variable_name parameter_replace_pattern parameter_expansion_value?)
			|	BLANK? -> variable_name
		)
		|	BANG variable_name_for_bang
			(
				TIMES -> ^(BANG variable_name_for_bang TIMES)
				|	AT -> ^(BANG variable_name_for_bang AT)
				|	LSQUARE (op=TIMES|op=AT) RSQUARE -> ^(LIST_EXPAND variable_name_for_bang $op)
			)
		|	{LA(1) == POUND && LA(2) != RBRACE }? => variable_size_ref;
parameter_delete_operator
	:	(POUND POUND) => POUND POUND -> REPLACE_AT_START
	|	POUND -> LAZY_REMOVE_AT_START
	|	PCT -> LAZY_REMOVE_AT_END
	|	PCTPCT -> REPLACE_AT_END;
parameter_value_operator
	:	COLON MINUS -> USE_DEFAULT_WHEN_UNSET_OR_NULL
	|	COLON EQUALS -> ASSIGN_DEFAULT_WHEN_UNSET_OR_NULL
	|	COLON QMARK -> DISPLAY_ERROR_WHEN_UNSET_OR_NULL
	|	COLON PLUS -> USE_ALTERNATE_WHEN_UNSET_OR_NULL
	|	MINUS -> USE_DEFAULT_WHEN_UNSET
	|	EQUALS -> ASSIGN_DEFAULT_WHEN_UNSET
	|	QMARK -> DISPLAY_ERROR_WHEN_UNSET
	|	PLUS -> USE_ALTERNATE_WHEN_UNSET;
parameter_replace_pattern
	:	(SLASH) => -> ^(STRING NAME) // Create an empty string
	|	((~SLASH) => parameter_pattern_part)+ -> ^(STRING parameter_pattern_part+);
parameter_delete_pattern
	:	parameter_pattern_part+ -> ^(STRING parameter_pattern_part+);
parameter_pattern_part
	:	extended_pattern_match|{is_special_token(LA(1))}? => .;

// TODO fix this rule
parameter_expansion_value
scope {
	int num_of_braces;
}
	:	parameter_expansion_value_atom -> ^(STRING parameter_expansion_value_atom);

parameter_expansion_value_atom
	:	(~RBRACE) =>
			{$parameter_expansion_value::num_of_braces = 1;}
			(
				{$parameter_expansion_value::num_of_braces != 0}? => .
				{
					if(LA(1) == LBRACE && LA(-1) != ESC)
						++$parameter_expansion_value::num_of_braces;
					else if(LA(1) == RBRACE && LA(-1) != ESC)
						--$parameter_expansion_value::num_of_braces;
				}
			)+
	|	-> EMPTY_EXPANSION_VALUE;

parameter_replace_operator
	:	(SLASH SLASH) => SLASH SLASH -> REPLACE_ALL
	|	(SLASH PCT) => SLASH PCT -> REPLACE_AT_END
	|	(SLASH POUND) => SLASH POUND -> REPLACE_AT_START
	|	SLASH -> REPLACE_FIRST;

variable_name
	:	num
	|	name LSQUARE AT RSQUARE -> ^(ARRAY name AT)
	|	name LSQUARE TIMES RSQUARE -> ^(ARRAY name TIMES)
	|	BANG variable_name_for_bang -> ^(VAR_REF variable_name_for_bang)
	|	variable_name_no_digit
	|	DOLLAR
	|	TIMES
	|	AT
	|	QMARK
	|	POUND;

variable_name_no_digit
	:	name LSQUARE BLANK? explicit_arithmetic BLANK? RSQUARE -> ^(name explicit_arithmetic)
	|	name;

variable_name_for_bang
	:	num|name|POUND;
variable_size_ref
	:	(POUND name LSQUARE) => POUND name LSQUARE array_size_index RSQUARE -> ^(POUND ^(name array_size_index))
	|	POUND^ variable_name;
array_size_index
	:	DIGIT+
	|	(AT|TIMES) -> ARRAY_SIZE;

wspace
	:	(BLANK|EOL)+;

command_substitution
	:	COMMAND_SUBSTITUTION_PAREN -> ^(COMMAND_SUB COMMAND_SUBSTITUTION_PAREN)
	|	COMMAND_SUBSTITUTION_TICK -> ^(COMMAND_SUB COMMAND_SUBSTITUTION_TICK);

brace_expansion
	:	LBRACE BLANK* brace_expansion_inside BLANK* RBRACE -> ^(BRACE_EXP brace_expansion_inside);
brace_expansion_inside
	:	commasep|range;
range
	:	DIGIT DOTDOT^ DIGIT
	|	LETTER DOTDOT^ LETTER;
brace_expansion_part
	:	((~COMMA) => string_expr_part)* -> ^(STRING string_expr_part*);
commasep
	:	brace_expansion_part (COMMA! brace_expansion_part)+;

explicit_arithmetic
	:	(DOLLAR (LLPAREN|LSQUARE)) => arithmetic_part // (the predicate resolves the conflict with the primary rule)
	|	arithmetics;

arithmetic_expansion
	:	arithmetic_part -> ^(ARITHMETIC_EXPRESSION arithmetic_part);

arithmetic_part
	:	DOLLAR LLPAREN BLANK? arithmetics BLANK? RPAREN RPAREN -> arithmetics
	|	DOLLAR LSQUARE BLANK? arithmetics BLANK? RSQUARE -> arithmetics;

arithmetics
	:	arithmetic (COMMA BLANK? arithmetic)* -> ^(ARITHMETIC arithmetic)+;

arithmetics_test
	:	arithmetics EOF!;

arithmetic
	:(variable_name_no_digit BLANK? arithmetic_assignment_operator)
		=> variable_name_no_digit BLANK!? arithmetic_assignment_operator^ BLANK!? logicor
	|	(arithmetic_variable_reference BLANK? arithmetic_assignment_operator)
			=> arithmetic_variable_reference BLANK!? arithmetic_assignment_operator^ BLANK!? logicor
	|	cnd=logicor
		(
			QMARK t=logicor COLON f=logicor -> ^(ARITHMETIC_CONDITION $cnd $t $f)
			|	-> $cnd
		);

arithmetic_assignment_operator
	:	{LA(1) == EQUALS && LA(2) != EQUALS}? => EQUALS
	|	TIMES EQUALS -> MUL_ASSIGN
	|	SLASH EQUALS -> DIVIDE_ASSIGN
	|	PCT EQUALS -> MOD_ASSIGN
	|	PLUS EQUALS -> PLUS_ASSIGN
	|	MINUS EQUALS -> MINUS_ASSIGN
	|	LSHIFT EQUALS -> LSHIFT_ASSIGN
	|	RSHIFT EQUALS -> RSHIFT_ASSIGN
	|	AMP EQUALS -> AND_ASSIGN
	|	CARET EQUALS -> XOR_ASSIGN
	|	PIPE EQUALS -> OR_ASSIGN;

arithmetic_variable_reference
	:	variable_reference -> ^(VAR_REF variable_reference);
primary
	:	num
	|	command_substitution
	|	variable_name_no_digit -> ^(VAR_REF variable_name_no_digit)
	|	variable_reference
	|	arithmetic_expansion
	|	LPAREN! (arithmetics) RPAREN!;
pre_post_primary
	:	DQUOTE!? primary DQUOTE!?;
post_inc_dec
	:	pre_post_primary ((BLANK) => BLANK)?
		(
			(PLUS PLUS) => BLANK? PLUS PLUS -> ^(POST_INCR pre_post_primary)
			|	(MINUS MINUS) => BLANK? MINUS MINUS -> ^(POST_DECR pre_post_primary)
			|	-> pre_post_primary
		);
pre_inc_dec
	:	PLUS PLUS BLANK? pre_post_primary -> ^(PRE_INCR pre_post_primary)
	|	MINUS MINUS BLANK? pre_post_primary -> ^(PRE_DECR pre_post_primary);
unary_with_operator
	:	PLUS BLANK? unary -> ^(PLUS_SIGN unary)
	|	MINUS BLANK? unary -> ^(MINUS_SIGN unary)
	|	TILDE BLANK? unary -> ^(TILDE unary)
	|	BANG BLANK? unary -> ^(BANG unary);
unary
	:	post_inc_dec
	|	(PLUS PLUS|MINUS MINUS) => pre_inc_dec
	|	(PLUS|MINUS|TILDE|BANG) => unary_with_operator;
exponential
	:	unary (EXP^ BLANK!? unary)* ;
times_division_modulus
	:	exponential ((TIMES^|SLASH^|PCT^) BLANK!? exponential)*;
addsub
	:	times_division_modulus ((PLUS^|MINUS^) BLANK!? times_division_modulus)*;
shifts
	:	addsub ((LSHIFT^|RSHIFT^) BLANK!? addsub)*;
compare
	:	shifts (compare_operator^ BLANK!? shifts)?;
compare_operator
	:	LESS_THAN EQUALS -> LEQ
	|	GREATER_THAN EQUALS -> GEQ
	|	LESS_THAN
	|	GREATER_THAN
	|	EQUALS EQUALS -> EQUALS_TO
	|	BANG EQUALS -> NOT_EQUALS;
bitwiseand
	:	compare (AMP^ BLANK!? compare)*;
bitwisexor
	:	bitwiseand (CARET^ BLANK!? bitwiseand)*;
bitwiseor
	:	bitwisexor (PIPE^ BLANK!? bitwisexor)*;
logicand
	:	bitwiseor (LOGICAND^ BLANK!? bitwiseor)*;
logicor
	:	logicand (LOGICOR^ BLANK!? logicand)*;

COMMENT
	:	{ !double_quoted }?=> (BLANK|EOL) '#' ~('\n'|'\r')* {$channel=HIDDEN;}
	;

BANG	:	'!';
CASE	:	'case';
DO		:	'do';
DONE	:	'done';
ELIF	:	'elif';
ELSE	:	'else';
ESAC	:	'esac';
FI		:	'fi';
FOR		:	'for';
IF		:	'if';
IN		:	'in';
SELECT	:	'select';
THEN	:	'then';
UNTIL	:	'until';
WHILE	:	'while';
LBRACE	:	'{';
RBRACE	:	'}';
TIME	:	'time';
RPAREN	:	')';
LPAREN	:	'(';
LLPAREN	:	'((';
LSQUARE	:	'[';
RSQUARE	:	']';
TICK	:	'`';
DOLLAR	:	'$';
AT	:	'@';
DOT	:	'.';
DOTDOT	:	'..';

TIMES	:	'*';
EQUALS	:	'=';
MINUS	:	'-';
PLUS	:	'+';
EXP		:	'**';
AMP		:	'&';
CARET	:	'^';
LESS_THAN	:	'<';
GREATER_THAN	:	'>';
LSHIFT	:	'<<';
RSHIFT	:	'>>';
AMP_LESS_THAN	:	'&<';
AMP_GREATER_THAN	:	'&>';
AMP_RSHIFT	:	'&>>';

SEMIC	:	';';
DOUBLE_SEMIC	:	';;';
PIPE	:	'|';
ESC_DQUOTE	:	'\\"';
ESC_SQUOTE	: { !double_quoted }? => '\\\'';
DQUOTE	:	'"' { if(LA(-1) != '\\') double_quoted = !double_quoted; };
SQUOTE	:	{ double_quoted }? => '\'';
SINGLE_QUOTED_STRING_TOKEN	:	{ !double_quoted }? => '\'' .* '\'';
COMMA	:	',';
BLANK	:	(' '|'\t')+;
EOL		:	('\r'?'\n')+ ;

DIGIT	:	'0'..'9';
NUMBER	:	DIGIT DIGIT+;
LETTER	:	('a'..'z'|'A'..'Z');
fragment
ALPHANUM	:	(DIGIT|LETTER);

TILDE	:	'~';
HERE_STRING_OP	:	'<<<';
POUND	:	'#';
PCT		:	'%';
PCTPCT	:	'%%';
SLASH	:	'/';
COLON	:	':';
QMARK	:	'?';

LOCAL	:	'local';
EXPORT	:	'export';
DECLARE	:	'declare';
LOGICAND	:	'&&';
LOGICOR	:	'||';

CONTINUE_LINE	:	(ESC EOL)+{$channel=HIDDEN;};
ESC_RPAREN	:	ESC RPAREN;
ESC_LPAREN	:	ESC LPAREN;
ESC_RSQUARE	:	ESC RSQUARE;
ESC_LSQUARE	:	ESC LSQUARE;
ESC_DOLLAR	:	ESC DOLLAR;
ESC_TICK	:	ESC TICK;
COMMAND_SUBSTITUTION_PAREN
	:	{LA(1) == '$' && LA(2) == '(' && LA(3) != '('}? =>
			(DOLLAR LPAREN ({ paren_level = 1; }
				(
					ESC_LPAREN
					|	ESC_RPAREN
					|	LPAREN { ++paren_level; }
					|	RPAREN
						{
							if(--paren_level == 0)
							{
#ifdef OUTPUT_C
								LEXSTATE->type = _type;
#else
								state.type = _type;
								state.channel = _channel;
#endif
								return;
							}
						}
					|	SINGLE_QUOTED_STRING_TOKEN
					|	.
				)+
			));
COMMAND_SUBSTITUTION_TICK	:	TICK .+ (~ESC) TICK;
ESC_LT	:	ESC'<';
ESC_GT	:	ESC'>';

ESC	:	'\\';
UNDERSCORE	:	'_';
NAME	:	(LETTER|UNDERSCORE)(ALPHANUM|UNDERSCORE)+;
OTHER	:	.;
