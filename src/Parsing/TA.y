%{
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.io.InputStream;

import Engine.StringAllocator;
import Testing.Error;

%}
      
      
%token	REG
%token	ID
%token	ROOM
%token	CASE
%token	NUM		
%token	STRING
%token	WORD
%token	METHOD

%token	EQ
%token	EQU
%token	INC
%token	DEC
%token	LT
%token	GT

%token	RAW

%token	IF
%token	ELSE

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
			|	conditional							{ $$.obj = $1.obj; }
			|	equation							{ $$.obj = $1.obj; }
			|	RAW									{ $$.obj = new Raw($1.sval); }
			;
			
equation	:	REG EQ REG EQU REG					{ $$.obj = new Equation(Equation.equ, $1.sval, $3.sval, $5.sval); }
			|	REG EQ REG EQU NUM					{ $$.obj = new Equation(Equation.equ, $1.sval, $3.sval, $5.ival); }
			|	REG EQ REG LT REG					{ $$.obj = new Equation(Equation.lt, $1.sval, $3.sval, $5.sval); }
			|	REG EQ REG LT NUM					{ $$.obj = new Equation(Equation.lt, $1.sval, $3.sval, $5.ival); }
			|	REG EQ REG GT REG					{ $$.obj = new Equation(Equation.gt, $1.sval, $3.sval, $5.sval); }
			|	REG EQ REG GT NUM					{ $$.obj = new Equation(Equation.gt, $1.sval, $3.sval, $5.ival); }
			|	REG INC NUM							{ $$.obj = new Equation(Equation.inc, $1.sval, $3.ival); }
			|	REG INC REG							{ $$.obj = new Equation(Equation.inc, $1.sval, $3.sval); }
			|	REG DEC NUM							{ $$.obj = new Equation(Equation.dec, $1.sval, $3.ival); }
			|	REG DEC REG							{ $$.obj = new Equation(Equation.dec, $1.sval, $3.sval); }
			|	REG EQ NUM							{ $$.obj = new Equation(Equation.eq, $1.sval, $3.ival); }
			|	REG EQ REG							{ $$.obj = new Equation(Equation.eq, $1.sval, $3.sval); }
			;
			
method		:	METHOD '(' args ')'					{ $$.obj = new Method($1.ival, (LinkedList<String>) $3.obj); }
			|	METHOD '(' ')'						{ $$.obj = new Method($1.ival, new LinkedList<String>()); }
			;
			
conditional	:	if									{ $$.obj = new Conditional($1.sval, (LinkedList<Entry>)$1.obj, null, $1.ival); }
			|	if else								{ $$.obj = new Conditional($1.sval, (LinkedList<Entry>)$1.obj, (LinkedList<Entry>)$2.obj, $1.ival); }
			;
			
if			:	IF '(' WORD ')' '{' commands '}'		{ $$.sval =  $3.sval; $$.ival = Conditional.IFTRUE; $$.obj = $6.obj; }
			|	IF '(' WORD ')' command 				{ $$.sval =  $3.sval; $$.ival = Conditional.IFTRUE; $$.obj = new LinkedList<Entry>(); ((LinkedList<Entry>)$$.obj).add((Entry)$5.obj); }
			|	IF '(' '!' WORD ')' '{' commands '}'	{ $$.sval =  $4.sval; $$.ival = Conditional.IFFALSE; $$.obj = $7.obj; }
			|	IF '(' '!' WORD ')' command 			{ $$.sval =  $4.sval; $$.ival = Conditional.IFFALSE; $$.obj = new LinkedList<Entry>(); ((LinkedList<Entry>)$$.obj).add((Entry)$6.obj); }
			|	IF '(' REG ')' '{' commands '}'			{ $$.sval =  $3.sval; $$.ival = Conditional.IFREG; $$.obj = $6.obj; }
			|	IF '(' REG ')' command 					{ $$.sval =  $3.sval; $$.ival = Conditional.IFREG; $$.obj = new LinkedList<Entry>(); ((LinkedList<Entry>)$$.obj).add((Entry)$5.obj); }
			|	IF '(' '!' REG ')' '{' commands '}'		{ $$.sval =  $4.sval; $$.ival = Conditional.IFREG; $$.obj = $7.obj; }
			|	IF '(' '!' REG')' command 				{ $$.sval =  $4.sval; $$.ival = Conditional.IFREG; $$.obj = new LinkedList<Entry>(); ((LinkedList<Entry>)$$.obj).add((Entry)$6.obj); }
			;
			
else		:	ELSE '{' commands '}'				{ $$.obj = $3.obj; }
			|	ELSE command 						{ $$.obj = new LinkedList<Entry>(); ((LinkedList<Entry>)$$.obj).add((Entry)$2.obj); }
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
		Error.print("Error: [" + (lexer.line() + 1 )+ ':' + lexer.yytext() + "] :" + error);
		Error.flush();
	}


	public TAParser(Reader r) {
		lexer = new TALexer(r, this);
	}
	
	public TAParser(InputStream[] is) {
		lexer = new TALexer(is, this);
	}

	public TAParser(java.io.File[] f) {
		lexer = new TALexer(f, this);
	}

	
