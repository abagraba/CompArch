package Engine;

public class MathAllocator {

	private static int	label	= 0;

	public static String allocate() {
		return "math" + label++;
	}

}
