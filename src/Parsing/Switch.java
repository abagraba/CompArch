package Parsing;

import java.util.LinkedList;

import Engine.JumpAllocator;



public class Switch extends Entry {

	private LinkedList<Case>	cases;
	private int					jt;

	public Switch(LinkedList<Case> cases) {
		this.cases = cases;
		jt = JumpAllocator.allocate(cases.size());
	}

	@Override
	public void debug(int indent) {
		String t = "";
		for (int i = 0; i < indent; i++)
			t += '\t';
		System.out.println(t + "Switch [jt" + jt + "]:");
		for (Case c : cases)
			c.debug(indent + 1);
	}

	@Override
	public void codeGen(int arg) {
		System.out.println("		sll		$t0, $s0, 2");
		System.out.println("		lw		$t0, jt" + jt + "($t0)");
		System.out.println("		jr		$t0");
		int i = 0;
		for (Case c : cases) {
			System.out.println("JL" + jt + '_' + i++ + ':');
			c.codeGen(jt);
		}
		System.out.println("JD" + jt + ':');
	}
}
