//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package Parsing;



//#line 2 "TA.y"
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.io.InputStream;

import Engine.StringAllocator;
import Testing.Error;

//#line 26 "TAParser.java"




public class TAParser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class TAParserVal is defined in TAParserVal.java


String   yytext;//user variable to return contextual strings
TAParserVal yyval; //used to return semantic vals from action routines
TAParserVal yylval;//the 'lval' (result) I got from yylex()
TAParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new TAParserVal[YYSTACKSIZE];
  yyval=new TAParserVal();
  yylval=new TAParserVal();
  valptr=-1;
}
void val_push(TAParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
TAParserVal val_pop()
{
  if (valptr<0)
    return new TAParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
TAParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new TAParserVal();
  return valstk[ptr];
}
final TAParserVal dup_yyval(TAParserVal val)
{
  TAParserVal dup = new TAParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short REG=257;
public final static short ID=258;
public final static short ROOM=259;
public final static short CASE=260;
public final static short NUM=261;
public final static short STRING=262;
public final static short WORD=263;
public final static short METHOD=264;
public final static short EQ=265;
public final static short EQU=266;
public final static short INC=267;
public final static short DEC=268;
public final static short LT=269;
public final static short GT=270;
public final static short RAW=271;
public final static short IF=272;
public final static short ELSE=273;
public final static short SWITCH=274;
public final static short SWITCHR=275;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    3,    3,    5,    5,    6,    6,
    7,    4,    4,    8,    8,    8,    8,    8,   11,   11,
   11,   11,   11,   11,   11,   11,   11,   11,   11,   11,
    9,    9,   10,   10,   13,   13,   13,   13,   13,   13,
   13,   13,   14,   14,   12,   12,   15,   15,   15,
};
final static short yylen[] = {                            2,
    1,    1,    2,    5,    1,    2,    4,    4,    1,    2,
    2,    0,    2,    1,    1,    1,    1,    1,    5,    5,
    5,    5,    5,    5,    3,    3,    3,    3,    3,    3,
    4,    3,    1,    2,    7,    5,    8,    6,    7,    5,
    8,    6,    4,    2,    1,    3,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    3,   12,    0,    0,    4,
    0,    0,    0,   18,    0,    0,    0,   14,   13,   15,
   16,   17,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   34,    0,   29,   26,   25,   28,   27,   49,   47,
   48,   32,    0,   45,    0,    0,    0,   12,    0,    9,
    0,   12,   44,    0,    0,    0,   31,    0,    0,    0,
    0,    0,    0,    7,   10,    8,    0,   19,   20,   21,
   22,   23,   24,   46,   12,   40,   12,   36,    0,    0,
   43,    0,    0,   12,   42,   12,   38,   39,   35,    0,
    0,   41,   37,
};
final static short yydgoto[] = {                          2,
    3,    4,    8,    9,   18,   49,   50,   19,   20,   21,
   22,   43,   23,   32,   44,
};
final static short yysindex[] = {                      -250,
 -243,    0, -250,    0,  -88,    0,    0,  -77, -245,    0,
 -245, -211,   15,    0,   22,  -59,  -58,    0,    0,    0,
    0,    0, -207, -246, -239, -236,  -40,  -33, -193, -193,
 -121,    0, -253,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   -7,    0,   27,   28, -249,    0, -122,    0,
 -120,    0,    0, -221, -214, -212,    0, -203, -116, -100,
   30,   31, -245,    0,    0,    0,  -46,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -95,  -79,
    0,  -30,  -25,    0,    0,    0,    0,    0,    0,   -9,
   -4,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,   74,    0,    0,    0,    0, -233,  -49,    0,
  -48,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -55,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -74,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -115,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   75,    0,   -2,    0,   50,   12,  -27,    0,    0,
    0,    0,    0,    0,   23,
};
final static int YYTABLESIZE=271;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         47,
   42,   52,   64,   53,   66,   11,   75,   61,    1,   11,
   33,   12,   54,   62,   34,   55,   56,   35,   13,    5,
   37,   36,   77,   12,   38,   14,   15,   84,   16,   17,
   12,   76,   78,   57,    7,   68,   58,   12,   12,   69,
   12,   12,   70,   86,   72,   63,   71,   10,   73,   67,
   30,   85,   87,   24,   27,   25,   26,   39,   40,   41,
   65,   28,   65,   29,   30,   31,   48,   59,   60,   33,
   79,   80,   82,    1,   83,    5,    6,    6,   81,   51,
   74,   90,    0,   91,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   88,    0,    0,    0,    0,   89,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   92,    0,    0,    0,    0,
   93,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   12,    0,   48,    0,   48,
   12,    0,   13,    0,   11,    0,    0,   13,    0,   14,
   15,    0,   16,   17,   14,   15,   12,   16,   17,    0,
    0,   12,    0,   13,    0,    0,    0,    0,   13,    0,
   14,   15,    0,   16,   17,   14,   15,   12,   16,   17,
    0,    0,   30,    0,   13,   30,    0,    0,    0,   30,
    0,   14,   15,    0,   16,   17,   30,   30,   30,   30,
   30,   33,    0,    0,   33,    0,    0,    0,   33,    0,
   12,    0,    0,    0,    0,   33,   33,   13,   33,   33,
   39,   40,   41,   45,   14,   15,   12,   16,   17,   46,
    0,   12,    0,   13,    0,    0,    0,    0,   13,    0,
   14,   15,    0,   16,   17,   14,   15,   12,   16,   17,
    0,    0,   12,    0,   13,    0,    0,    0,    0,   13,
    0,   14,   15,    0,   16,   17,   14,   15,    0,   16,
   17,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   41,  123,  125,   31,  125,    8,  123,  257,  259,  125,
  257,  257,  266,  263,  261,  269,  270,  257,  264,  263,
  257,  261,  123,  257,  261,  271,  272,  123,  274,  275,
  264,   59,   60,   41,  123,  257,   44,  271,  272,  261,
  274,  275,  257,  123,  257,   48,  261,  125,  261,   52,
  125,   79,   80,  265,   40,  267,  268,  261,  262,  263,
   49,   40,   51,  123,  123,  273,  260,   41,   41,  125,
   41,   41,   75,    0,   77,  125,  125,    3,  125,   30,
   58,   84,   -1,   86,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  125,   -1,   -1,   -1,   -1,  125,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  125,   -1,   -1,   -1,   -1,
  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,   -1,  260,   -1,  260,
  257,   -1,  264,   -1,  260,   -1,   -1,  264,   -1,  271,
  272,   -1,  274,  275,  271,  272,  257,  274,  275,   -1,
   -1,  257,   -1,  264,   -1,   -1,   -1,   -1,  264,   -1,
  271,  272,   -1,  274,  275,  271,  272,  257,  274,  275,
   -1,   -1,  257,   -1,  264,  260,   -1,   -1,   -1,  264,
   -1,  271,  272,   -1,  274,  275,  271,  272,  273,  274,
  275,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,
  257,   -1,   -1,   -1,   -1,  271,  272,  264,  274,  275,
  261,  262,  263,  257,  271,  272,  257,  274,  275,  263,
   -1,  257,   -1,  264,   -1,   -1,   -1,   -1,  264,   -1,
  271,  272,   -1,  274,  275,  271,  272,  257,  274,  275,
   -1,   -1,  257,   -1,  264,   -1,   -1,   -1,   -1,  264,
   -1,  271,  272,   -1,  274,  275,  271,  272,   -1,  274,
  275,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=275;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,null,null,null,"'('","')'",null,null,"','",
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,"'{'",
null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,"REG","ID","ROOM","CASE","NUM","STRING","WORD",
"METHOD","EQ","EQU","INC","DEC","LT","GT","RAW","IF","ELSE","SWITCH","SWITCHR",
};
final static String yyrule[] = {
"$accept : accept",
"accept : file",
"file : room",
"file : file room",
"room : ROOM WORD '{' roomdata '}'",
"roomdata : commands",
"roomdata : roomdata commands",
"switch : SWITCH '{' caseBlock '}'",
"switch : SWITCHR '{' caseBlock '}'",
"caseBlock : case",
"caseBlock : caseBlock case",
"case : CASE commands",
"commands :",
"commands : commands command",
"command : switch",
"command : method",
"command : conditional",
"command : equation",
"command : RAW",
"equation : REG EQ REG EQU REG",
"equation : REG EQ REG EQU NUM",
"equation : REG EQ REG LT REG",
"equation : REG EQ REG LT NUM",
"equation : REG EQ REG GT REG",
"equation : REG EQ REG GT NUM",
"equation : REG INC NUM",
"equation : REG INC REG",
"equation : REG DEC NUM",
"equation : REG DEC REG",
"equation : REG EQ NUM",
"equation : REG EQ REG",
"method : METHOD '(' args ')'",
"method : METHOD '(' ')'",
"conditional : if",
"conditional : if else",
"if : IF '(' WORD ')' '{' commands '}'",
"if : IF '(' WORD ')' command",
"if : IF '(' '!' WORD ')' '{' commands '}'",
"if : IF '(' '!' WORD ')' command",
"if : IF '(' REG ')' '{' commands '}'",
"if : IF '(' REG ')' command",
"if : IF '(' '!' REG ')' '{' commands '}'",
"if : IF '(' '!' REG ')' command",
"else : ELSE '{' commands '}'",
"else : ELSE command",
"args : arg",
"args : args ',' arg",
"arg : STRING",
"arg : WORD",
"arg : NUM",
};

//#line 122 "TA.y"
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

	
//#line 355 "TAParser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 39 "TA.y"
{ if (debug)((Entry)val_peek(0).obj).debug();else ((Entry)val_peek(0).obj).codeGen();}
break;
case 2:
//#line 42 "TA.y"
{ yyval.obj = new File(); ((File)yyval.obj).addRoom((Room)val_peek(0).obj);}
break;
case 3:
//#line 43 "TA.y"
{ yyval.obj = val_peek(1).obj; ((File)yyval.obj).addRoom((Room)val_peek(0).obj);}
break;
case 4:
//#line 46 "TA.y"
{ yyval.obj = new Room(val_peek(3).sval, (LinkedList<Entry>)val_peek(1).obj); }
break;
case 5:
//#line 49 "TA.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 6:
//#line 50 "TA.y"
{ yyval.obj = val_peek(1).obj; ((LinkedList<Entry>)yyval.obj).addAll((LinkedList<Entry>)val_peek(0).obj); }
break;
case 7:
//#line 53 "TA.y"
{ yyval.obj = new Switch((LinkedList<Case>)val_peek(1).obj, false); }
break;
case 8:
//#line 54 "TA.y"
{ yyval.obj = new Switch((LinkedList<Case>)val_peek(1).obj, true); }
break;
case 9:
//#line 57 "TA.y"
{ LinkedList<Case> list = new LinkedList<Case>(); list.add((Case)val_peek(0).obj); yyval.obj = list; }
break;
case 10:
//#line 58 "TA.y"
{ LinkedList<Case> list = (LinkedList<Case>)val_peek(1).obj; list.add((Case)val_peek(0).obj); yyval.obj = list; }
break;
case 11:
//#line 61 "TA.y"
{ yyval.obj = new Case(val_peek(1).ival, (LinkedList<Entry>)val_peek(0).obj); }
break;
case 12:
//#line 64 "TA.y"
{ yyval.obj = new LinkedList<Entry>(); }
break;
case 13:
//#line 65 "TA.y"
{ LinkedList<Entry> entries = (LinkedList<Entry>)val_peek(1).obj; entries.add((Entry)val_peek(0).obj); yyval.obj = entries; }
break;
case 14:
//#line 68 "TA.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 15:
//#line 69 "TA.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 16:
//#line 70 "TA.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 17:
//#line 71 "TA.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 18:
//#line 72 "TA.y"
{ yyval.obj = new Raw(val_peek(0).sval); }
break;
case 19:
//#line 75 "TA.y"
{ yyval.obj = new Equation(Equation.equ, val_peek(4).sval, val_peek(2).sval, val_peek(0).sval); }
break;
case 20:
//#line 76 "TA.y"
{ yyval.obj = new Equation(Equation.equ, val_peek(4).sval, val_peek(2).sval, val_peek(0).ival); }
break;
case 21:
//#line 77 "TA.y"
{ yyval.obj = new Equation(Equation.lt, val_peek(4).sval, val_peek(2).sval, val_peek(0).sval); }
break;
case 22:
//#line 78 "TA.y"
{ yyval.obj = new Equation(Equation.lt, val_peek(4).sval, val_peek(2).sval, val_peek(0).ival); }
break;
case 23:
//#line 79 "TA.y"
{ yyval.obj = new Equation(Equation.gt, val_peek(4).sval, val_peek(2).sval, val_peek(0).sval); }
break;
case 24:
//#line 80 "TA.y"
{ yyval.obj = new Equation(Equation.gt, val_peek(4).sval, val_peek(2).sval, val_peek(0).ival); }
break;
case 25:
//#line 81 "TA.y"
{ yyval.obj = new Equation(Equation.inc, val_peek(2).sval, val_peek(0).ival); }
break;
case 26:
//#line 82 "TA.y"
{ yyval.obj = new Equation(Equation.inc, val_peek(2).sval, val_peek(0).sval); }
break;
case 27:
//#line 83 "TA.y"
{ yyval.obj = new Equation(Equation.dec, val_peek(2).sval, val_peek(0).ival); }
break;
case 28:
//#line 84 "TA.y"
{ yyval.obj = new Equation(Equation.dec, val_peek(2).sval, val_peek(0).sval); }
break;
case 29:
//#line 85 "TA.y"
{ yyval.obj = new Equation(Equation.eq, val_peek(2).sval, val_peek(0).ival); }
break;
case 30:
//#line 86 "TA.y"
{ yyval.obj = new Equation(Equation.eq, val_peek(2).sval, val_peek(0).sval); }
break;
case 31:
//#line 89 "TA.y"
{ yyval.obj = new Method(val_peek(3).ival, (LinkedList<String>) val_peek(1).obj); }
break;
case 32:
//#line 90 "TA.y"
{ yyval.obj = new Method(val_peek(2).ival, new LinkedList<String>()); }
break;
case 33:
//#line 93 "TA.y"
{ yyval.obj = new Conditional(val_peek(0).sval, (LinkedList<Entry>)val_peek(0).obj, null, val_peek(0).ival); }
break;
case 34:
//#line 94 "TA.y"
{ yyval.obj = new Conditional(val_peek(1).sval, (LinkedList<Entry>)val_peek(1).obj, (LinkedList<Entry>)val_peek(0).obj, val_peek(1).ival); }
break;
case 35:
//#line 97 "TA.y"
{ yyval.sval =  val_peek(4).sval; yyval.ival = Conditional.IFTRUE; yyval.obj = val_peek(1).obj; }
break;
case 36:
//#line 98 "TA.y"
{ yyval.sval =  val_peek(2).sval; yyval.ival = Conditional.IFTRUE; yyval.obj = new LinkedList<Entry>(); ((LinkedList<Entry>)yyval.obj).add((Entry)val_peek(0).obj); }
break;
case 37:
//#line 99 "TA.y"
{ yyval.sval =  val_peek(4).sval; yyval.ival = Conditional.IFFALSE; yyval.obj = val_peek(1).obj; }
break;
case 38:
//#line 100 "TA.y"
{ yyval.sval =  val_peek(2).sval; yyval.ival = Conditional.IFFALSE; yyval.obj = new LinkedList<Entry>(); ((LinkedList<Entry>)yyval.obj).add((Entry)val_peek(0).obj); }
break;
case 39:
//#line 101 "TA.y"
{ yyval.sval =  val_peek(4).sval; yyval.ival = Conditional.IFREG; yyval.obj = val_peek(1).obj; }
break;
case 40:
//#line 102 "TA.y"
{ yyval.sval =  val_peek(2).sval; yyval.ival = Conditional.IFREG; yyval.obj = new LinkedList<Entry>(); ((LinkedList<Entry>)yyval.obj).add((Entry)val_peek(0).obj); }
break;
case 41:
//#line 103 "TA.y"
{ yyval.sval =  val_peek(4).sval; yyval.ival = Conditional.IFREG; yyval.obj = val_peek(1).obj; }
break;
case 42:
//#line 104 "TA.y"
{ yyval.sval =  val_peek(2).sval; yyval.ival = Conditional.IFREG; yyval.obj = new LinkedList<Entry>(); ((LinkedList<Entry>)yyval.obj).add((Entry)val_peek(0).obj); }
break;
case 43:
//#line 107 "TA.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 44:
//#line 108 "TA.y"
{ yyval.obj = new LinkedList<Entry>(); ((LinkedList<Entry>)yyval.obj).add((Entry)val_peek(0).obj); }
break;
case 45:
//#line 111 "TA.y"
{ LinkedList<String> args = new LinkedList<String>(); args.add(val_peek(0).sval); yyval.obj = args; }
break;
case 46:
//#line 112 "TA.y"
{ LinkedList<String> args = (LinkedList<String>) val_peek(2).obj; args.add(val_peek(0).sval); yyval.obj = args; }
break;
case 47:
//#line 115 "TA.y"
{ yyval.sval = StringAllocator.allocate(val_peek(0).sval); }
break;
case 48:
//#line 116 "TA.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 49:
//#line 117 "TA.y"
{ yyval.sval = "" + val_peek(0).ival; }
break;
//#line 700 "TAParser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public TAParser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public TAParser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
