package Parsing;

import java.util.LinkedList;

import Testing.Output;



public class Room extends Entry {

	private String				name;
	private LinkedList<Entry>	entries;

	public Room(String name, LinkedList<Entry> entries) {
		this.name = name;
		this.entries = entries;
	}

	@Override
	public void debug(int indent) {
		String t = "";
		for (int i = 0; i < indent; i++)
			t += '\t';
		Output.print(t + "Room [" + name + "]:");
		t += '\t';
		for (Entry e : entries)
			e.debug(indent + 1);
	}

	@Override
	public void codeGen(int indent) {
		Output.print("");
		Output.print(name + ':');
		for (Entry e : entries)
			e.codeGen(indent + 1);
	}

}
