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
WORD		=	[A-Za-z0-9_]+

STRING		=	[^\n\r\"]+

ROOM 		= 	room

VAL			=	[0-9a-zA-Z ]

SWITCH		=	switch
SWITCHR		=	switchr
CASE		=	\[{VAL}\]

PRINT		=	print
PRINTLN		=	println
GOTO		=	goto
INPUT		=	input
CONTINUE	=	continue
OPTION		=	option

NL			=   (\r\n|[\n\r]) 

%x STR

%%

	{ROOM}		{ return TAParser.ROOM; }
	{SWITCH}	{ return TAParser.SWITCH; }
	{SWITCHR}	{ return TAParser.SWITCHR; }
	{CASE}		{ yyparser.yylval = new TAParserVal(yytext().charAt(1)); return TAParser.CASE; }

	{PRINT}		{ yyparser.yylval = new TAParserVal(0); return TAParser.METHOD; }
	{PRINTLN}	{ yyparser.yylval = new TAParserVal(1); return TAParser.METHOD; }
	{GOTO}		{ yyparser.yylval = new TAParserVal(2); return TAParser.METHOD; }
	{INPUT}		{ yyparser.yylval = new TAParserVal(3); return TAParser.METHOD; }
	{CONTINUE}	{ yyparser.yylval = new TAParserVal(4); return TAParser.METHOD; }
	{OPTION}	{ yyparser.yylval = new TAParserVal(5); return TAParser.METHOD; }

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
	
	
	
	