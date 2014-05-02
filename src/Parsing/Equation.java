package Parsing;

import Engine.MathAllocator;
import Testing.Output;



public class Equation extends Entry {

	public static final int	eq	= 0;
	public static final int	inc	= 1;
	public static final int	dec	= 2;
	public static final int	equ	= 3;
	public static final int	lt	= 4;
	public static final int	gt	= 5;

	private int				op;
	private String			dest;
	private String			src1;
	private String			src2;
	private int				isrc;

	public Equation(int op, String dest, String src1, String src2) {
		this.op = op;
		this.dest = dest;
		this.src1 = src1;
		this.src2 = src2;
	}

	public Equation(int op, String dest, String src1, int src2) {
		this.op = op;
		this.dest = dest;
		this.src1 = src1;
		isrc = src2;
	}

	public Equation(int op, String dest, String src) {
		this.op = op;
		this.dest = dest;
		src1 = src;
	}

	public Equation(int op, String dest, int src) {
		this.op = op;
		this.dest = dest;
		isrc = src;
	}

	@Override
	public void debug(int indent) {

	}

	@Override
	public void codeGen(int arg) {
		switch (op) {
			case eq:
				if (src1 == null)
					Output.print("		addi	" + dest + ", $0, " + isrc);
				else
					Output.print("		add		" + dest + ", $0, " + src1);
				break;
			case inc:
				if (src1 == null)
					Output.print("		addi	" + dest + ", " + dest + ", " + isrc);
				else
					Output.print("		add		" + dest + ", " + dest + ", " + src1);
				break;
			case dec:
				if (isrc < 0)
					isrc *= -1;
				if (src1 == null)
					Output.print("		addi	" + dest + ", " + dest + ", " + isrc);
				else
					Output.print("		sub		" + dest + ", " + dest + ", " + src1);
				break;
			case equ:
				String label = MathAllocator.allocate();
				String labele = label + "end";
				if (src2 == null)
					Output.print("		xori	" + dest + ", " + src1 + ", " + isrc);
				else
					Output.print("		xor		" + dest + ", " + src1 + ", " + src2);
				Output.print("		beq		" + dest + ", $0, " + label);
				Output.print("		add		" + dest + ", $0, $0");
				Output.print("		j		" + labele);
				Output.print(label + ':');
				Output.print("		addi	" + dest + ", $0, 1");
				Output.print(labele + ':');
				break;
			case lt:
				if (src2 == null)
					Output.print("		slti	" + dest + ", " + src1 + ", " + isrc);
				else
					Output.print("		slt		" + dest + ", " + src1 + ", " + src2);
				break;
			case gt:
				if (src2 == null) {
					Output.print("		addi	$1, $0, " + isrc);
					Output.print("		slti	" + dest + ", $1, " + src1);
				}
				else
					Output.print("		slt		" + dest + ", " + src2 + ", " + src1);
				break;
		}

	}
}
