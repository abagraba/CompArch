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
		int t = 1 + (name.length() - 3) / 4;
		int l = (20 - t) / 2;
		int r = 20 - t - l;

		String data = "####";
		for (int i = 0; i < l; i++)
			data += '\t';
		data += name;
		for (int i = 0; i < r; i++)
			data += '\t';
		data += "####";
		Output.print("########################################################################################");
		Output.print(data);
		Output.print("########################################################################################");
		Output.print(name + ':');
		for (Entry e : entries)
			e.codeGen(indent + 1);
	}

}
