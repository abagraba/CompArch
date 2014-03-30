%{
import java.util.LinkedList;
import java.io.Reader;
import java.io.IOException;

import java.util.HashMap;

import Compiler.*;
%}
      
      
%token	ID
%token	ROOM
%token	CASE
%token	NUM		
%token	STRING
%token	WORD
%token	METHOD

%token	SWITCH


%%
accept		:	file								{ ((LogEntry)$1.obj).printLog(-1); }
			;

file		:	room								{ $$.obj = new LogEntry(); ((LogEntry)$$.obj).addSubEntry($1.obj);}
			| 	file room							{ $$.obj = $1.obj; ((LogEntry)$$.obj).addSubEntry($2.obj);}
			;	

room		:	ROOM WORD '{' roomdata '}'			{ $$.obj = $4.obj; ((LogEntry)$$.obj).addEntry("ROOM: " + $2.sval); }
			;
		
roomdata	:	switch								{ $$.obj = new LogEntry(); ((LogEntry)$$.obj).addSubEntry($1.obj); }
			|	commands							{ $$.obj = $1.obj; }
			|	roomdata switch						{ $$.obj = $1.obj; ((LogEntry)$$.obj).addSubEntries($2.obj); }
			|	roomdata commands					{ $$.obj = $1.obj; ((LogEntry)$$.obj).addSubEntries($2.obj); }
			;
			
switch		:	SWITCH '{' caseBlock '}'			{ $$.obj = $3.obj; ((LogEntry)$$.obj).addEntry("SWITCH block:"); }
			;
			
caseBlock	:	case								{ $$.obj = new LogEntry(); ((LogEntry)$$.obj).addSubEntry($1.obj); }
			|	caseBlock case						{ $$.obj = $1.obj; ((LogEntry)$$.obj).addSubEntry($2.obj); }
			;

case		:	CASE commands						{ $$.obj = $2.obj; ((LogEntry)$$.obj).addEntry("If [" + (char)($1.ival) + "] is pressed:"); }
			;

commands	:										{ $$.obj = new LogEntry(); }
			|	commands command					{ $$.obj = $1.obj; ((LogEntry)$1.obj).addSubEntry($2.obj);}
			;
			
command		:										{  }
			|	switch								{ $$.obj = $1.obj; }
			|	method								{ $$.obj = $1.obj; }
			;
			
method		:	METHOD '(' args ')'					{ $$.obj = $3.obj; ((LogEntry)$$.obj).addEntry("Calling [" + method[$1.ival] + "] with args:"); }
			;
			
args		:	arg									{ $$.obj = new LogEntry(); ((LogEntry)$$.obj).addSubEntry(new LogEntry('[' + $1.sval + ']')); }
			|	args ',' arg						{ $$.obj = $1.obj; ((LogEntry)$$.obj).addSubEntry(new LogEntry('[' + $3.sval + ']')); }
			;
			
arg			:	STRING								{ $$.sval = '"' + $1.sval + '"'; }
			|	WORD								{ $$.sval = $1.sval; }
			|	NUM									{ $$.sval = "" + $1.ival; }
			;

			
%%
	public static boolean debug;
	private TALexer lexer;

	
	private LinkedList<LogEntry> logs = new LinkedList<LogEntry>();
	private LogEntry log = new LogEntry();
	
	private String[] method = new String[]{"Print", "Jump"};
	

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
		System.err.println ("Error: " + error);
	}


	public TAParser(Reader r) {
		lexer = new TALexer(r, this);
	}

	
