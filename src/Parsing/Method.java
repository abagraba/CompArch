package Parsing;

import java.util.LinkedList;

import Engine.InputAllocator;
import Engine.StringAllocator;
import Engine.SyscallAllocator;
import Testing.Output;



public class Method extends Entry {

	protected static final int		print	= 0;
	protected static final int		println	= 1;
	protected static final int		jump	= 2;
	protected static final int		input	= 3;

	private static final String[]	name	= new String[] { "Print", "Println", "Jump", "Input" };

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
		Output.print(t + "Call [" + name[i] + "] with args:");
		t += '\t';
		for (String s : args)
			Output.print(t + '[' + StringAllocator.interpret(s) + ']');
	}

	@Override
	public void codeGen(int arg) {
		int n = args.length;
		switch (i) {
			case print:
				if (n != 1) {
					Output.print("		#" + name[i] + " received invalid number of arguments. Expected 1. Received " + n + ".");
					return;
				}
				SyscallAllocator.call(4, args[0]);
				break;
			case println:
				if (n != 1 && n != 0) {
					Output.print("		#" + name[i] + " received invalid number of arguments. Expected 0 or 1. Received " + n + ".");
					return;
				}
				if (n == 1)
					SyscallAllocator.call(4, args[0]);
				SyscallAllocator.call(4, "string0");
				break;
			case jump:
				if (n != 1) {
					Output.print("		#" + name[i] + " received invalid number of arguments. Expected 1. Received " + n + ".");
					return;
				}
				Output.print("		j		" + args[0]);
				break;
			case input:
				if (n != 1) {
					Output.print("		#" + name[i] + " received invalid number of arguments. Expected 1. Received " + n + ".");
					return;
				}
				int i = Integer.parseInt(args[0]);
				String l = InputAllocator.allocate();
				String lx = l + "_x";
				String le = l + "_end";
				Output.print(l + ':');
				Output.print("		li		$t0, " + i + "					# $t0 = Upper Bound of input."); // $t0 = Upper Bound
				SyscallAllocator.call(12);
				Output.print("		add		$s1, $v0, $0			# s1 = input");
				Output.print("		addi	$v0, $v0, -49"); // '1' = 0
				Output.print("		bltz	$v0, " + lx + "			# Jump to " + lx + " if $v0 < 0.");
				Output.print("		sub		$t1, $v0, $t0");
				Output.print("		bgez	$t1, " + lx + "			# Jump to " + lx + " if $v0 > max Input.");
				Output.print("		add		$s0, $v0, $t0			# $s0 = [1, maxInput]");
				Output.print("		addi	$s0, $s0, -1			# $s0 = [0, maxInput - 1]");
				Output.print("		j		" + le);
				Output.print(lx + ':');
				Output.print("		add		$s0, $t0, $0			# $s0 = maxInput");
				Output.print(le + ':');
				SyscallAllocator.call(4, "string0");
				break;
			default:
				Output.print("		#Invalid method call.");
		}

	}
}
