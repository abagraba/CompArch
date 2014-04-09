package Engine;

public class Boolean extends Variable {
	private int	bit;

	public Boolean(Register r, int bit) {
		super(r);
		this.bit = bit;
	}

	@Override
	public void get(int arg) {

	}

	@Override
	public void set(int arg) {

	}
}
