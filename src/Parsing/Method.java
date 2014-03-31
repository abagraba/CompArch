package Parsing;

import java.util.LinkedList;

import Engine.StringAllocator;



public class Method extends Entry {

	private static final int		print	= 0;
	private static final int		jump	= 1;

	private static final String[]	name	= new String[] { "Print", "Jump" };

	private int						i;
	String[]						args;

	public Method(int i, LinkedList<String> args) {
		this.i = i;
		this.args = args.toArray(new String[args.size()]);
	}

	@Override
	public void debug(int indent) {
		String t = "";
		for (int i = 0; i < indent; i++)
			t += '\t';
		System.out.println(t + "Call [" + name[i] + "] with args:");
		t += '\t';
		for (String s : args)
			System.out.println(t + '[' + StringAllocator.interpret(s) + ']');
	}

	@Override
	public void codeGen(int arg) {
		switch (i) {
			case print:
				if (args.length != 1)
					System.out.println("		#Invalid number of arguments. Expected 1.");
				System.out.println("		li		$v0, 1");
				System.out.println("		la		$a0, " + args[0]);
				System.out.println("		syscall");
				break;
			case jump:
				if (args.length != 1)
					System.out.println("		#Invalid number of arguments. Expected 1.");
				System.out.println("		j		" + args[0]);
				break;
			default:
				System.out.println("		#Invalid method call.");
		}

	}
}
