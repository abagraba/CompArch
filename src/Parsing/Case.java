package Parsing;

import java.util.LinkedList;

import Testing.Output;



public class Case extends Entry {
	private boolean				def			= true;
	boolean						num			= true;
	public char					c;
	private LinkedList<Entry>	commands	= new LinkedList<Entry>();

	public Case(int c, LinkedList<Entry> commands) {
		this.c = (char) c;
		num = '0' < c && c <= '9';
		this.commands = commands;
		Entry e = commands.get(commands.size() - 1);
		if (e instanceof Method)
			def = ((Method) e).method != Method.jump;
	}

	@Override
	public void debug(int indent) {
		String t = "";
		for (int i = 0; i < indent; i++)
			t += '\t';
		Output.print(t + "If [" + c + "]:");
		for (Entry e : commands)
			e.debug(indent + 1);
	}

	@Override
	public void codeGen(int arg) {
		for (Entry e : commands)
			e.codeGen(0);
		if (def)
			Output.print("		j		" + Switch.jumpEnd + arg);
	}
}

// The Linus Programming Interface by Kerrisk, M.
