package Parsing;

import java.util.LinkedList;



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
		System.out.println(t + "Room [" + name + "]:");
		t += '\t';
		for (Entry e : entries)
			e.debug(indent + 1);
	}

	@Override
	public void codeGen(int indent) {
		System.out.println();
		System.out.println(name + ':');
		for (Entry e : entries)
			e.codeGen(indent + 1);
	}

}
