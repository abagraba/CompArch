package CodeGen;

import java.util.LinkedList;



public class Math {
	public static int	increment	= 0;
	public static int	decrement	= 0;
	public static int	add			= 0;
	public static int	sub			= 0;
	public static int	mult		= 0;

	public Statement increment(String d, String reg) {
		final String td = d;
		final String tr = reg;
		return new MathOp() {

			@Override
			public void getQuads(LinkedList<QUAD> quads, Method method) {
				quads.add(new QUAD("ADD", method.resolve(td), method.resolve(td), method.resolve(tr)));
			}
		};
	}

	public Statement increment(String d, int i) {
		final String td = d;
		final int ti = i;
		return new MathOp() {
			@Override
			public void getQuads(LinkedList<QUAD> quads, Method method) {
				quads.add(new QUAD("ADD", method.resolve(td), method.resolve(td), ti));
			}
		};
	}

	private abstract class MathOp extends Statement {
1
		@Override
		public LinkedList<QUAD> resolve(Method method) {
			LinkedList<QUAD> quads = new LinkedList<QUAD>();
			getQuads(quads, method);
			return quads;
		}

		public abstract void getQuads(LinkedList<QUAD> quads, Method method);

	}

}
