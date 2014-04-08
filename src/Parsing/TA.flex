package Parsing;

%%

%class TALexer
%byaccj



%{
  private TAParser yyparser;
  private String str;

  public TALexer(java.io.Reader r, TAParser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }
  
  public int line(){
  	return yyline;
  }
  
  public int pos(){
  	return yychar;
  }
  
  
%}


ID			=	\[{WORD}\]

NUM			=	[0-9]+
L			=	.
WORD		=	[A-Za-z0-9_]+

STRING		=	[^\n\r\"]+

ROOM 		= 	room

VAL				=	[0-9a-zA-Z ]

SWITCH		=	switch
SWITCHR		=	switchr
CASE			=	\[{VAL}\]

PRINT		=	print
PRINTLN	=	println
JUMP		=	jump
INPUT		=	input

NL			=   (\r\n|[\n\r]) 

%x STR

%%

	{ROOM}		{ return TAParser.ROOM; }
	{SWITCH}	{ return TAParser.SWITCH; }
	{SWITCHR}	{ return TAParser.SWITCHR; }

	{PRINT}		{ yyparser.yylval = new TAParserVal(0); return TAParser.METHOD; }
	{PRINTLN}		{ yyparser.yylval = new TAParserVal(1); return TAParser.METHOD; }
	{JUMP}		{ yyparser.yylval = new TAParserVal(2); return TAParser.METHOD; }
	{INPUT}		{ yyparser.yylval = new TAParserVal(3); return TAParser.METHOD; }

	{CASE}		{ yyparser.yylval = new TAParserVal(yytext().charAt(1)); return TAParser.CASE; }

	[{]			{ return '{'; }
	[}]			{ return '}'; }
	[(]			{ return '('; }
	[)]			{ return ')'; }
	,			{ return ','; }

	\"			{ yybegin(STR); str = ""; }
	
	
	<STR>{
	\"			{ yyparser.yylval = new TAParserVal(str); yybegin(YYINITIAL); return TAParser.STRING; }
	{STRING}	{ str = yytext(); }
}

	{ID}		{ yyparser.yylval = new TAParserVal(yytext().substring(1, yytext().length()-1)); return TAParser.ID; }
	{NUM}		{ yyparser.yylval = new TAParserVal(Integer.parseInt(yytext())); return TAParser.NUM; }
	{WORD}		{ yyparser.yylval = new TAParserVal(yytext()); return TAParser.WORD; }
	
	{NL}		{ yyline++; }
	[\t ]		{  }
	.			{ System.err.println("Unexpected "+ yytext()); }
	
	
	
	