package Engine;

public class Register {
	private int		bitsAllocated	= 0;
	public String	name;

	public Register(String name) {
		this.name = name;
	}

	public int allocate(int bits) {
		if (bitsAllocated + bits > 32)
			return -1;
		int i = bitsAllocated;
		bitsAllocated += bits;
		return i;
	}

	@Override
	public String toString() {
		return name;
	}
}
