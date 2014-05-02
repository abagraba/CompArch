package Parsing;

import java.util.LinkedList;

import Engine.Boolean;
import Engine.BooleanAllocator;
import Testing.Output;



public class Conditional extends Entry {

	public static final int		IFTRUE	= 0;
	public static final int		IFFALSE	= 1;
	public static final int		IFREG	= 2;
	public static final int		IFNREG	= 3;

	protected static final int	TRUE	= 0;
	protected static final int	FALSE	= 1;
	protected static final int	END		= 2;

	private String				bool;
	private LinkedList<Entry>	iff;
	private LinkedList<Entry>	elsee;
	private int					type;

	public Conditional(String bool, LinkedList<Entry> iff, LinkedList<Entry> elsee, int type) {
		this.bool = bool;
		this.iff = iff;
		this.elsee = elsee;
		this.type = type;
	}

	@Override
	public void debug(int indent) {

	}

	@Override
	public void codeGen(int arg) {
		Boolean b = BooleanAllocator.interpret(bool);
		String[] sa = b.labelSet();
		switch (type) {
			case IFTRUE:
				Output.print("		andi	$t0, " + b.register + ", " + b.mask());
				Output.print("		beq		$t0, $0, " + (elsee != null ? sa[FALSE] : sa[END]));
				for (Entry e : iff)
					e.codeGen(0);
				if (elsee != null) {
					Output.print("		j		" + sa[END]);
					Output.print(sa[FALSE] + ":");
					for (Entry e : elsee)
						e.codeGen(0);
				}
				Output.print(sa[END] + ":");
				break;
			case IFFALSE:
				Output.print("		andi	$t0, " + b.register + ", " + b.mask());
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
				break;
			case IFREG:
				Output.print("		bne		$t0, " + bool + ", " + (elsee != null ? sa[FALSE] : sa[END]));
				break;
		}
	}

}
