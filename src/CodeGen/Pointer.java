package CodeGen;

import Memory.Register;

public class Pointer {
	public Register	r;
	public int		offset;

	public Pointer(Register r, int offset) {
		this.r = r;
		this.offset = offset;
	}

	public Pointer(Register r) {
		this(r, 0);
	}

	@Override
	public String toString() {
		return offset + "(" + r + ")";
	}

}
