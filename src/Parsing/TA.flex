package Parsing;
import java.io.Reader;
%%

%class TALexer
%byaccj



%{
	private TAParser yyparser;
	private String str;

	public TALexer(Reader r, TAParser yyparser) {
		this(r);
		this.yyparser = yyparser;
	}
  
		public TALexer(InputStream[] r, TAParser yyparser) {
		this(new InputStreamReader(getReader(r)));
		this.yyparser = yyparser;
	}

	private static InputStream getReader(InputStream[] rs) {
		if (rs.length == 0)
			return null;
		if (rs.length == 1)
			return rs[0];
		InputStream is = new SequenceInputStream(rs[0], rs[1]);
		for (int i = 2; i < rs.length; i++)
			is = new SequenceInputStream(is, rs[i]);
		return is;
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

VAL			=	'[0-9a-zA-Z ]'

SWITCH		=	switch
SWITCHR		=	switchr
CASE		=	\[{VAL}\]

IF			=	if
ELSE		=	else

PRINT		=	print
PRINTLN		=	println
GOTO		=	goto
INPUT		=	input
CONTINUE	=	continue
OPTION		=	option
CHECKPOINT	=	checkpoint
SET			=	set
UNSET		=	unset

NL			=   (\r\n|[\n\r]) 

%x STR

%%

	{ROOM}			{ return TAParser.ROOM; }
	{SWITCH}		{ return TAParser.SWITCH; }
	{SWITCHR}		{ return TAParser.SWITCHR; }
	{CASE}			{ yyparser.yylval = new TAParserVal(yytext().charAt(2)); return TAParser.CASE; }

	{IF}			{ return TAParser.IF; }
	{ELSE}			{ return TAParser.ELSE; }

	{PRINT}			{ yyparser.yylval = new TAParserVal(0); return TAParser.METHOD; }
	{PRINTLN}		{ yyparser.yylval = new TAParserVal(1); return TAParser.METHOD; }
	{GOTO}			{ yyparser.yylval = new TAParserVal(2); return TAParser.METHOD; }
	{INPUT}			{ yyparser.yylval = new TAParserVal(3); return TAParser.METHOD; }
	{CONTINUE}		{ yyparser.yylval = new TAParserVal(4); return TAParser.METHOD; }
	{OPTION}		{ yyparser.yylval = new TAParserVal(5); return TAParser.METHOD; }
	{CHECKPOINT}	{ yyparser.yylval = new TAParserVal(6); return TAParser.METHOD; }
	{SET}			{ yyparser.yylval = new TAParserVal(7); return TAParser.METHOD; }
	{UNSET}			{ yyparser.yylval = new TAParserVal(8); return TAParser.METHOD; }

	[{]				{ return '{'; }
	[}]				{ return '}'; }
	[(]				{ return '('; }
	[)]				{ return ')'; }
	,				{ return ','; }
	
	\"				{ yybegin(STR); str = ""; }
	
	
	<STR>{
	\"				{ yyparser.yylval = new TAParserVal(str); yybegin(YYINITIAL); return TAParser.STRING; }
	{STRING}		{ str = yytext(); }
}               	
                	
	{ID}			{ yyparser.yylval = new TAParserVal(yytext().substring(1, yytext().length()-1)); return TAParser.ID; }
	{NUM}			{ yyparser.yylval = new TAParserVal(Integer.parseInt(yytext())); return TAParser.NUM; }
	{WORD}			{ yyparser.yylval = new TAParserVal(yytext()); return TAParser.WORD; }
	            	
	{NL}			{ yyline++; }
	[\t ]			{  }
	.				{ System.err.println("Unexpected "+ yytext()); }
	
	
	
	