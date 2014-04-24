package Engine;

public class Boolean {

	public final String	name;
	public final String	register;
	public final int	bit;
	private int			check	= 0;

	public Boolean(String name, String register, int bit) {
		this.name = name;
		this.register = register;
		this.bit = bit;
	}

	public int mask() {
		return (int) Math.pow(2, bit);
	}

	public String[] labelSet() {
		return new String[] { name + '_' + bit + "true" + check, name + '_' + bit + "false" + check, name + '_' + bit + "end" + check++ };
	}

}
