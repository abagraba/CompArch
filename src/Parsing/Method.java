package Parsing;

import java.util.LinkedList;

import Engine.Boolean;
import Engine.BooleanAllocator;
import Engine.ContinueAllocator;
import Engine.InputAllocator;
import Engine.StringAllocator;
import Engine.SyscallAllocator;
import Testing.Output;



/**
 * Entry that controls the contents of all the functions supported.
 */
public class Method extends Entry {

	protected static final int		print		= 0;
	protected static final int		println		= 1;
	protected static final int		printint	= 2;
	protected static final int		jump		= 3;
	protected static final int		input		= 4;
	protected static final int		cont		= 5;
	protected static final int		option		= 6;
	protected static final int		checkpoint	= 7;
	protected static final int		set			= 8;
	protected static final int		unset		= 9;

	private static final String[]	methodName	= new String[] { "Print", "Println", "PrintInt", "Jump", "Input", "Continue", "Checkpoint", "Set",
			"Unset"							};

	protected int					method;
	protected String[]				args;

	public Method(int method, LinkedList<String> args) {
		this.method = method;
		this.args = args.toArray(new String[args.size()]);
	}

	@Override
	public void debug(int indent) {
		String t = "";
		for (int i = 0; i < indent; i++)
			t += '\t';
		Output.print(t + "Call [" + methodName[method] + "] with args:");
		t += '\t';
		for (String s : args)
			Output.print(t + '[' + StringAllocator.interpret(s) + ']');
	}

	@Override
	public void codeGen(int arg) {
		int n = args.length;
		Boolean b;
		int i;

		switch (method) {
		// Print the specified text.
			case print:
				if (argCheck(n, 1))
					return;
				SyscallAllocator.call(4, args[0]);
				break;
			// Print the specified text, followed by a new line.
			// Print a new line if no text specified.
			case println:
				if (argCheck(n, 0, 1))
					return;
				if (n == 1)
					SyscallAllocator.call(4, args[0]);
				SyscallAllocator.call(4, "string0");
				break;
			// Print the specified int.
			// If none is specified, print the value in $a0
			case printint:
				if (argCheck(n, 0, 1))
					return;
				if (n == 1) {
					i = Integer.parseInt(args[0]);
					SyscallAllocator.call(1, i);
				}
				else
					SyscallAllocator.call(1);
				break;
			// Go directly to specified room/checkpoint.
			case jump:
				if (argCheck(n, 1))
					return;
				Output.print("		j		" + args[0]);
				break;
			// Poll for character input. Int arg specifies valid jump table bounds. If input is in bounds, $s0 contains
			// the target jump table address. Otherwise it directs to the miscellaneous input test.
			case input:
				if (argCheck(n, 1))
					return;
				i = Integer.parseInt(args[0]);
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
				Output.print("		add		$s0, $t0, $t1			# $s0 = [0, maxInput - 1]");
				Output.print("		j		" + le);
				Output.print(lx + ':');
				Output.print("		add		$s0, $t0, $0			# $s0 = maxInput");
				Output.print(le + ':');
				SyscallAllocator.call(4, "string0");
				break;
			// Convenience function to pause and prompt the user to press the space bar.
			case cont:
				if (argCheck(n, 0))
					return;
				SyscallAllocator.call(4, "string0");
				String label = ContinueAllocator.allocate();
				String labelend = label + "_end";
				SyscallAllocator.call(4, "string10");
				Output.print("		addi	$t9, $0, 32"); // '1' = 0
				Output.print(label + ':');
				SyscallAllocator.call(12);
				Output.print("		beq		$t9, $v0, " + labelend); // '1' = 0
				SyscallAllocator.call(4, "string0");
				Output.print("		j		" + label); // '1' = 0
				Output.print(labelend + ':');
				SyscallAllocator.invalidate();
				SyscallAllocator.call(4, "string0");
				break;
			// Convenience function to print out a nicely formatted note to the user on what keys to press.
			case option:
				if (argCheck(n, 2))
					return;
				i = -1;
				try {
					i = Integer.parseInt(args[0]);
				}
				catch (NumberFormatException e) {}
				if (i != -1) {
					SyscallAllocator.call(4, "string" + i);
					SyscallAllocator.call(4, args[1]);
					SyscallAllocator.call(4, "string0");
				}
				else {
					SyscallAllocator.call(4, StringAllocator.allocate("[" + args[0] + "]	"));
					SyscallAllocator.call(4, args[1]);
					SyscallAllocator.call(4, "string0");
				}
				break;
			// Creates a checkpoint that can be jumped to later.
			case checkpoint:
				if (argCheck(n, 1))
					return;
				Output.print(args[0] + ':');
				break;
			// Set the specified boolean to true.
			case set:
				if (argCheck(n, 1))
					return;
				b = BooleanAllocator.interpret(args[0]);
				Output.print("		ori		" + b.register + ", " + b.register + ", " + b.mask());
				break;
			// Set the specified boolean to false.
			case unset:
				if (argCheck(n, 1))
					return;
				b = BooleanAllocator.interpret(args[0]);
				Output.print("		andi	" + b.register + ", " + b.register + ", " + (Integer.MAX_VALUE - b.mask()));
				break;
			default:
				System.err.println("Unrecognized Method.");
				Output.print("		#Invalid method call.");
		}

	}

	/**
	 * Check the number of arguments passed to the method.
	 */
	private boolean argCheck(int n, int c) {
		if (n != c) {
			Output.print("		#" + methodName[method] + " received invalid number of arguments. Expected " + c + ". Received " + n + ".");
			return true;
		}
		return false;
	}

	private boolean argCheck(int n, int c, int d) {
		if (n < c || n > d) {
			Output.print("		#" + methodName[method] + " received invalid number of arguments. Expected " + c + " to " + d + ". Received " + n + ".");
			return true;
		}
		return false;
	}

}
