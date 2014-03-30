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
%}


ID			=	\[{WORD}\]

NUM			=	[0-9]+
L			=	.
WORD		=	[A-Za-z0-9_]+

STRING		=	[^\n\r\"]+

ROOM 		= 	room

SWITCH		=	switch
CASE			=	\[{L}\]

PRINT		=	print
JUMP		=	jump

%x STR

%%

	{ROOM}		{ return TAParser.ROOM; }
	{SWITCH}	{ return TAParser.SWITCH; }

	{PRINT}		{ yyparser.yylval = new TAParserVal(0); return TAParser.METHOD; }
	{JUMP}		{ yyparser.yylval = new TAParserVal(1); return TAParser.METHOD; }

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
	
	[\r\n\t ]	{  }
	.			{ System.err.println("Unexpected "+ yytext()); }
	
	
	
	