package Parsing;

import Engine.JumpAllocator;
import Engine.StringAllocator;



public abstract class Entry {

	public void codeGen() {
		System.out.println(".data");
		System.out.println("input:");
		System.out.println("	.space	1");
		System.out.println(StringAllocator.generateMIPSData());
		System.out.println(JumpAllocator.generateMIPSData());
		System.out.println(".text");
		System.out.println(".globl	main");
		codeGen(-1);
	}

	public void debug() {
		debug(-1);
	}

	public abstract void debug(int indent);

	public abstract void codeGen(int arg);
}
