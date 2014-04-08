package Parsing;

import java.util.LinkedList;

import Engine.InputAllocator;
import Engine.JumpAllocator;
import Engine.SyscallAllocator;
import Testing.Output;



public class Switch extends Entry {

	public static final String	jumpTable	= "jt";
	public static final String	jumpResolve	= "jr";
	public static final String	jumpDefault	= "jd";
	public static final String	jumpCase	= "jc";
	public static final String	jumpEnd		= "je";

	private LinkedList<Case>	intcases	= new LinkedList<Case>();
	private LinkedList<Case>	letcases	= new LinkedList<Case>();
	private int					jt;
	private boolean				essential;

	public Switch(LinkedList<Case> cases, boolean essential) {
		for (Case c : cases)
			if (c.num)
				intcases.add(c);
			else
				letcases.add(c);
		this.essential = essential;
		jt = JumpAllocator.allocate(intcases.size());
	}

	@Override
	public void debug(int indent) {
		String t = "";
		for (int i = 0; i < indent; i++)
			t += '\t';
		Output.print(t + "Switch [jt" + jt + "]:");
		for (Case c : intcases)
			c.debug(indent + 1);
	}

	/**
	 * Takes value in $t0 and jumps to the $t0 th value in the jump table
	 */
	@Override
	public void codeGen(int arg) {
		Output.print("		sll		$t0, $s0, 2");
		Output.print("		lw		$t0, " + jumpTable + jt + "($t0)");
		Output.print("		jr		$t0");
		int i = 0;
		for (Case c : intcases) {
			Output.print(jumpCase + jt + '_' + (i++ + 1) + ':');
			c.codeGen(jt);
		}
		Output.print(jumpResolve + jt + ':');
		int x = 0;
		for (Case c : letcases) {
			Output.print("		li		$t0, " + (int) c.c);
			Output.print("		beq		$s1, $t0, " + jumpResolve + jt + '_' + x++);
		}
		Output.print("		j		" + (essential ? InputAllocator.last() : jumpEnd + jt));
		x = 0;
		for (Case c : letcases) {
			SyscallAllocator.push();
			Output.print(jumpResolve + jt + '_' + x++ + ':');
			c.codeGen(jt);
			SyscallAllocator.pop();
		}
		SyscallAllocator.invalidate();
		Output.print(jumpEnd + jt + ':');
	}
}
