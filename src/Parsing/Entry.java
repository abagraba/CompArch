package Parsing;

import Engine.JumpAllocator;
import Engine.StringAllocator;
import Engine.SyscallAllocator;
import Testing.Output;



public abstract class Entry {

	public void codeGen() {
		Output.print(".data");
		Output.print("########################################################################################\n####							String Allocation									####\n########################################################################################");
		Output.print(StringAllocator.generateMIPSData());
		Output.print("########################################################################################\n####								Jump Tables										####\n########################################################################################");
		Output.print(JumpAllocator.generateMIPSData());
		Output.print(".text");
		Output.print(".globl	main");
		codeGen(-1);
		Output.print("########################################################################################\n####									ERROR										####\n########################################################################################");
		Output.print("error:");
		SyscallAllocator.call(4, "string1");
	}

	public void debug() {
		debug(-1);
	}

	public abstract void debug(int indent);

	public abstract void codeGen(int arg);
}
