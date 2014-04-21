package Engine;




public class Boolean {

	public final String	register;
	public final int	bit;
	private int			check	= 0;

	public Boolean(String register, int bit) {
		this.register = register;
		this.bit = bit;
	}

	public int mask() {
		return (int) Math.pow(2, bit);
	}

	public String[] labelSet() {
		return new String[] { register + bit + "true" + check, register + bit + "false" + check, register + bit + "end" + check++ };
	}

}
