%{
import java.util.LinkedList;
import java.io.Reader;
import java.io.IOException;

import java.util.HashMap;

import Engine.*;

%}
      
      
%token	ID
%token	ROOM
%token	CASE
%token	NUM		
%token	STRING
%token	WORD
%token	METHOD

%token	SWITCH
%token	SWITCHR


%%
accept		:	file								{ if (debug)((Entry)$1.obj).debug();else ((Entry)$1.obj).codeGen();}
			;

file		:	room								{ $$.obj = new File(); ((File)$$.obj).addRoom((Room)$1.obj);}
			| 	file room							{ $$.obj = $1.obj; ((File)$$.obj).addRoom((Room)$2.obj);}
			;	

room		:	ROOM WORD '{' roomdata '}'			{ $$.obj = new Room($2.sval, (LinkedList<Entry>)$4.obj); }
			;
		
roomdata	:	commands							{ $$.obj = $1.obj; }
			|	roomdata commands					{ $$.obj = $1.obj; ((LinkedList<Entry>)$$.obj).addAll((LinkedList<Entry>)$2.obj); }
			;
			
switch		:	SWITCH '{' caseBlock '}'			{ $$.obj = new Switch((LinkedList<Case>)$3.obj, false); }
			|	SWITCHR '{' caseBlock '}'			{ $$.obj = new Switch((LinkedList<Case>)$3.obj, true); }
			;
			
caseBlock	:	case								{ LinkedList<Case> list = new LinkedList<Case>(); list.add((Case)$1.obj); $$.obj = list; }
			|	caseBlock case						{ LinkedList<Case> list = (LinkedList<Case>)$1.obj; list.add((Case)$2.obj); $$.obj = list; }
			;

case		:	CASE commands						{ $$.obj = new Case($1.ival, (LinkedList<Entry>)$2.obj); }
			;

commands	:										{ $$.obj = new LinkedList<Entry>(); }
			|	commands command					{ LinkedList<Entry> entries = (LinkedList<Entry>)$1.obj; entries.add((Entry)$2.obj); $$.obj = entries; }
			;
			
command		:	switch								{ $$.obj = $1.obj; }
			|	method								{ $$.obj = $1.obj; }
			;
			
method		:	METHOD '(' args ')'					{ $$.obj = new Method($1.ival, (LinkedList<String>) $3.obj); }
			|	METHOD '(' ')'						{ $$.obj = new Method($1.ival, new LinkedList<String>()); }
			;
			
args		:	arg									{ LinkedList<String> args = new LinkedList<String>(); args.add($1.sval); $$.obj = args; }
			|	args ',' arg						{ LinkedList<String> args = (LinkedList<String>) $1.obj; args.add($3.sval); $$.obj = args; }
			;
			
arg			:	STRING								{ $$.sval = StringAllocator.allocate($1.sval); }
			|	WORD								{ $$.sval = $1.sval; }
			|	NUM									{ $$.sval = "" + $1.ival; }
			;

			
%%
	public static boolean debug = false;
	private TALexer lexer;

	
	private int yylex () {
		int token = -1;
		try {
			yylval = new TAParserVal(0);
			token = lexer.yylex();
			//System.out.println(yyname[token]);
		}
		catch (IOException e) {
			System.err.println("IO error :"+e);
		}
		return token;
	}


	public void yyerror (String error) {
		System.err.println ("Error: [" + (lexer.line() + 1 )+ ':' + lexer.yytext() + "] :" + error);
	}


	public TAParser(Reader r) {
		lexer = new TALexer(r, this);
	}

	
