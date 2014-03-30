package CodeGen;

import Memory.Register;

public class QUAD {
	private String		command;
	private Register	d;
	private Register	s;
	private Register	t;
	private Pointer		p;
	private int			i;
	private int			mask;

	private QUAD(String command, Register d, Register s, Register t, Pointer p, int i, int mask) {
		this.command = command;
		this.d = d;
		this.s = s;
		this.t = t;
		this.p = p;
		this.i = i;
		this.mask = mask;
	}

	public QUAD(String command) {
		this(command, null, null, null, null, 0, 0);
	}

	public QUAD(String command, Register d) {
		this(command, d, null, null, null, 0, 36);
	}

	public QUAD(String command, Register d, Register s) {
		this(command, d, s, null, null, 0, 36 + 2 * 6);
	}

	public QUAD(String command, Register d, int i) {
		this(command, d, null, null, null, i, 36 + 5 * 6);
	}

	public QUAD(String command, Register d, Register s, Register t) {
		this(command, d, s, t, null, 0, 36 + 2 * 6 + 3);
	}

	public QUAD(String command, Register d, Register s, int i) {
		this(command, d, s, null, null, i, 36 + 2 * 6 + 5);
	}

	public QUAD(String command, Register d, Pointer p) {
		this(command, d, null, null, p, 0, 36 + 4 * 6);
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer(command);
		for (int i = 0; i < 3 - command.length() / 2; i++)
			s.append('\t');
		for (int i = 36; i > 1; i /= 6) {
			Object o = fetch(mask / i % 6);
			if (o != null)
				s.append(o);
		}
		return s.toString();
	}

	private Object fetch(int i) {
		switch (i) {
			case 1:
				return d;
			case 2:
				return s;
			case 3:
				return t;
			case 4:
				return p;
			case 5:
				return i;
			default:
				return null;
		}
	}
}
