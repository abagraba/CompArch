package Engine;




public abstract class Variable {
	private Register	r;

	public Variable(Register r) {
		this.r = r;
	}

	public abstract void get(int arg);

	public abstract void set(int arg);
}
