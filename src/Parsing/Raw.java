package Parsing;

import Testing.Output;



public class Raw extends Entry {

	String	s;

	public Raw(String s) {
		this.s = s;
	}

	@Override
	public void debug(int indent) {

	}

	@Override
	public void codeGen(int arg) {
		Output.print(s);
	}

}
