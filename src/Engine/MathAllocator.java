package Engine;

public class MathAllocator {

	private static int	label	= 0;

	/**
	 * Allocates unique labels for use in mathematical oprations.
	 */
	public static String allocate() {
		return "math" + label++;
	}

}
