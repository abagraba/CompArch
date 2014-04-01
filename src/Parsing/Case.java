package Parsing;

import java.util.LinkedList;



public class Case extends Entry {
	private boolean				def			= true;
	private char				c;
	private LinkedList<Entry>	commands	= new LinkedList<Entry>();

	public Case(int c, LinkedList<Entry> commands) {
		this.c = (char) c;
		this.commands = commands;
		Entry e = commands.get(commands.size() - 1);
		if (e instanceof Method)
			def = ((Method) e).i != Method.jump;
	}

	@Override
	public void debug(int indent) {
		String t = "";
		for (int i = 0; i < indent; i++)
			t += '\t';
		System.out.println(t + "If [" + c + "]:");
		for (Entry e : commands)
			e.debug(indent + 1);
	}

	@Override
	public void codeGen(int arg) {
		for (Entry e : commands)
			e.codeGen(0);
		if (def)
			System.out.println("		j		JD" + arg);
	}
}
