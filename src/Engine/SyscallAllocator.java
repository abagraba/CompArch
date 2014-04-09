package Engine;

import java.util.Stack;

import Testing.Output;



public class SyscallAllocator {

	private static Stack<Syscall>	stack	= new Stack<Syscall>();

	private static int				vv0		= -1;
	private static int				aa0		= -1;
	private static int				aa1		= -1;
	private static int				aa2		= -1;

	public static void call(int v0) {
		if (vv0 != v0)
			Output.print("		li	$v0, " + v0);
		set(v0, aa0, aa1, aa2);
		Output.print("		syscall");
	}

	public static void call(int v0, int a0) {
		if (vv0 != v0)
			Output.print("		li	$v0, " + v0);
		if (aa0 != a0)
			Output.print("		li	$a0, " + a0);
		set(v0, a0, aa1, aa2);
		Output.print("		syscall");
	}

	public static void call(int v0, int a0, int a1) {
		if (vv0 != v0)
			Output.print("		li	$v0, " + v0);
		if (aa0 != a0)
			Output.print("		li	$a0, " + a0);
		if (aa1 != a1)
			Output.print("		li	$a1, " + a1);
		set(v0, a0, a1, aa2);
		Output.print("		syscall");
	}

	public static void call(int v0, int a0, int a1, int a2) {
		if (vv0 != v0)
			Output.print("		li	$v0, " + v0);
		if (aa0 != a0)
			Output.print("		li	$a0, " + a0);
		if (aa1 != a1)
			Output.print("		li	$a1, " + a1);
		if (aa2 != a2)
			Output.print("		li	$a2, " + a2);
		set(v0, a0, a1, a2);
		Output.print("		syscall");
	}

	public static void call(int v0, String a0) {
		if (vv0 != v0)
			Output.print("		li	$v0, " + v0);
		Output.print("		la	$a0, " + a0);
		set(v0, -1, aa1, aa2);
		Output.print("		syscall");
	}

	public static void call(int v0, String a0, int a1, int a2) {
		if (vv0 != v0)
			Output.print("		li	$v0, " + v0);
		Output.print("		la	$a0, " + a0);
		if (aa1 != a1)
			Output.print("		li	$a1, " + a1);
		if (aa2 != a2)
			Output.print("		li	$a2, " + a2);
		set(v0, -1, a1, a2);
		Output.print("		syscall");
	}

	public static void push() {
		stack.push(new Syscall(vv0, aa0, aa1, aa2));
	}

	public static void pop() {
		Syscall s = stack.pop();
		vv0 = s.v0;
		aa0 = s.a0;
		aa1 = s.a1;
		aa2 = s.a2;
	}

	public static void invalidate() {
		vv0 = -1;
		aa0 = -1;
		aa1 = -1;
		aa2 = -1;
	}

	private static void set(int v0, int a0, int a1, int a2) {
		vv0 = v0;
		aa0 = a0;
		aa1 = a1;
		aa2 = a2;
	}

	private static class Syscall {
		protected int	v0	= -1;
		protected int	a0	= -1;
		protected int	a1	= -1;
		protected int	a2	= -1;

		public Syscall(int v0, int a0, int a1, int a2) {
			this.v0 = v0;
			this.a0 = a0;
			this.a1 = a1;
			this.a2 = a2;
		}

	}

}