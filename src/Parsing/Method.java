package Parsing;

import java.util.LinkedList;

import Engine.StringAllocator;



public class Method extends Entry {

	protected static final int		print	= 0;
	protected static final int		jump	= 1;
	protected static final int		input	= 2;

	private static final String[]	name	= new String[] { "Print", "Jump", "Input" };

	protected int					i;
	protected String[]				args;

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
			case input:
				if (args.length != 1)
					System.out.println("		#Invalid number of arguments. Expected 1.");
				int i = Integer.parseInt(args[0]);
				String L1 = LabelAllocator.allocate();
				String L2 = LabelAllocator.allocate();
				System.out.println("		li		$t0, " + i);
				System.out.println("		li		$v0, 12");
				System.out.println("		syscall");
				System.out.println("		addi	$v0, $v0, -48");
				System.out.println("		bltz	$v0, " + L1);
				System.out.println("		sub		$t1, $v0, $t0");
				System.out.println("		bgez	$t1, " + L1);
				System.out.println("		add		$s0, $v0, $0");
				System.out.println("		j		" + L2);
				System.out.println(L1 + ':');
				System.out.println("		add		$s0, $t0, $0");
				System.out.println(L2 + ':');
				break;
			default:
				System.out.println("		#Invalid method call.");
		}

	}
}
