package Parsing;

import java.util.LinkedList;

import Engine.Boolean;
import Engine.BooleanAllocator;
import Testing.Output;



public class Conditional extends Entry {

	protected static final int	TRUE	= 0;
	protected static final int	FALSE	= 1;
	protected static final int	END		= 2;

	private Boolean				bool;
	private LinkedList<Entry>	iff;
	private LinkedList<Entry>	elsee;
	private boolean				t;

	public Conditional(String bool, LinkedList<Entry> iff, LinkedList<Entry> elsee, boolean t) {
		this.bool = BooleanAllocator.interpret(bool);
		this.iff = iff;
		this.elsee = elsee;
		this.t = t;
	}

	@Override
	public void debug(int indent) {

	}

	@Override
	public void codeGen(int arg) {
		String[] sa = bool.labelSet();
		Output.print("		andi	$t0, " + bool.register + ", " + bool.mask());
		if (t)
			Output.print("		beq		$t0, $0, " + (elsee != null ? sa[FALSE] : sa[END]));
		else
			Output.print("		bne		$t0, $0, " + (elsee != null ? sa[FALSE] : sa[END]));
		for (Entry e : iff)
			e.codeGen(0);
		if (elsee != null) {
			Output.print("		j		" + sa[END]);
			Output.print(sa[FALSE] + ":");
			for (Entry e : elsee)
				e.codeGen(0);
		}
		Output.print(sa[END] + ":");
	}

}
