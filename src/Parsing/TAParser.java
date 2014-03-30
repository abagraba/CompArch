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
import java.util.LinkedList;
import java.io.Reader;
import java.io.IOException;

import java.util.HashMap;

import CodeGen.*;
import Compiler.*;
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
public final static short ID=257;
public final static short ROOM=258;
public final static short CASE=259;
public final static short NUM=260;
public final static short STRING=261;
public final static short WORD=262;
public final static short METHOD=263;
public final static short SWITCH=264;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    3,    3,    3,    3,    4,    6,
    6,    7,    5,    5,    8,    8,    8,    9,   10,   10,
   11,   11,   11,
};
final static short yylen[] = {                            2,
    1,    1,    2,    5,    1,    1,    2,    2,    4,    1,
    2,    2,    0,    2,    0,    1,    1,    4,    1,    3,
    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,    0,    3,    0,    0,    0,    5,
    0,    0,    4,    7,    0,    0,   16,   14,   17,   13,
    0,   10,    0,    0,    9,   11,   23,   21,   22,    0,
   19,   18,    0,   20,
};
final static short yydgoto[] = {                          2,
    3,    4,    9,   17,   11,   21,   22,   18,   19,   30,
   31,
};
final static short yysindex[] = {                      -245,
 -246,    0, -245,    0, -106,    0, -244, -105, -125,    0,
 -257, -240,    0,    0, -257,  -19,    0,    0,    0,    0,
 -124,    0, -251, -257,    0,    0,    0,    0,    0,  -36,
    0,    0, -251,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,   22,    0,    0,    0, -123,    0, -239,    0,
 -102,    0,    0,    0, -100,    0,    0,    0,    0,    0,
    0,    0,    0, -122,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   23,    0,    5,   -5,    0,    6,    0,    0,    0,
   -4,
};
final static int YYTABLESIZE=140;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         13,
   25,   13,   12,   15,   32,   16,    8,   33,   27,   28,
   29,   10,    1,   14,   24,    5,    7,   12,   20,    8,
   23,    1,    6,   13,    8,    6,   26,    0,   34,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   20,    0,   12,    0,    8,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                        125,
  125,  125,  125,    9,   41,  263,  264,   44,  260,  261,
  262,    7,  258,    9,   20,  262,  123,  123,  259,  264,
   40,    0,  125,  263,  125,    3,   21,   -1,   33,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  259,   -1,  259,   -1,  264,  263,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=264;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'",null,null,"','",
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
null,null,null,null,null,null,"ID","ROOM","CASE","NUM","STRING","WORD","METHOD",
"SWITCH",
};
final static String yyrule[] = {
"$accept : accept",
"accept : file",
"file : room",
"file : file room",
"room : ROOM WORD '{' roomdata '}'",
"roomdata : switch",
"roomdata : commands",
"roomdata : roomdata switch",
"roomdata : roomdata commands",
"switch : SWITCH '{' caseBlock '}'",
"caseBlock : case",
"caseBlock : caseBlock case",
"case : CASE commands",
"commands :",
"commands : commands command",
"command :",
"command : switch",
"command : method",
"method : METHOD '(' args ')'",
"args : arg",
"args : args ',' arg",
"arg : STRING",
"arg : WORD",
"arg : NUM",
};

//#line 74 "TA.y"
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

	
//#line 265 "TAParser.java"
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
//#line 25 "TA.y"
{ ((LogEntry)val_peek(0).obj).printLog(-1); }
break;
case 2:
//#line 28 "TA.y"
{ yyval.obj = new LogEntry("File"); ((LogEntry)yyval.obj).addSubEntry(val_peek(0).obj);}
break;
case 3:
//#line 29 "TA.y"
{ yyval.obj = val_peek(1).obj; ((LogEntry)yyval.obj).addSubEntry(val_peek(0).obj);}
break;
case 4:
//#line 32 "TA.y"
{ yyval.obj = val_peek(1).obj; ((LogEntry)yyval.obj).addEntry("ROOM: " + val_peek(3).sval); }
break;
case 5:
//#line 35 "TA.y"
{ yyval.obj = new LogEntry(); ((LogEntry)yyval.obj).addSubEntry(val_peek(0).obj); }
break;
case 6:
//#line 36 "TA.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 7:
//#line 37 "TA.y"
{ yyval.obj = val_peek(1).obj; ((LogEntry)yyval.obj).addSubEntries(val_peek(0).obj); }
break;
case 8:
//#line 38 "TA.y"
{ yyval.obj = val_peek(1).obj; ((LogEntry)yyval.obj).addSubEntries(val_peek(0).obj); }
break;
case 9:
//#line 41 "TA.y"
{ yyval.obj = val_peek(1).obj; ((LogEntry)yyval.obj).addEntry("SWITCH block:"); }
break;
case 10:
//#line 44 "TA.y"
{ yyval.obj = new LogEntry(); ((LogEntry)yyval.obj).addSubEntry(val_peek(0).obj); }
break;
case 11:
//#line 45 "TA.y"
{ yyval.obj = val_peek(1).obj; ((LogEntry)yyval.obj).addSubEntry(val_peek(0).obj); }
break;
case 12:
//#line 48 "TA.y"
{ yyval.obj = val_peek(0).obj; ((LogEntry)yyval.obj).addEntry("If [" + (char)(val_peek(1).ival) + "] is pressed:"); }
break;
case 13:
//#line 51 "TA.y"
{ yyval.obj = new LogEntry(); }
break;
case 14:
//#line 52 "TA.y"
{ yyval.obj = val_peek(1).obj; ((LogEntry)val_peek(1).obj).addSubEntry(val_peek(0).obj);}
break;
case 15:
//#line 55 "TA.y"
{  }
break;
case 16:
//#line 56 "TA.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 17:
//#line 57 "TA.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 18:
//#line 60 "TA.y"
{ yyval.obj = val_peek(1).obj; ((LogEntry)yyval.obj).addEntry("Calling [" + method[val_peek(3).ival] + "] with args:"); }
break;
case 19:
//#line 63 "TA.y"
{ yyval.obj = new LogEntry(); ((LogEntry)yyval.obj).addSubEntry(new LogEntry('[' + val_peek(0).sval + ']')); }
break;
case 20:
//#line 64 "TA.y"
{ yyval.obj = val_peek(2).obj; ((LogEntry)yyval.obj).addSubEntry(new LogEntry('[' + val_peek(0).sval + ']')); }
break;
case 21:
//#line 67 "TA.y"
{ yyval.sval = '"' + val_peek(0).sval + '"'; }
break;
case 22:
//#line 68 "TA.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 23:
//#line 69 "TA.y"
{ yyval.sval = "" + val_peek(0).ival; }
break;
//#line 506 "TAParser.java"
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
