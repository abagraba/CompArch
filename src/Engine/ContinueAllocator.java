package Engine;

public class ContinueAllocator {

	/**
	 * Allocates unique labels for use in the continue convenience method.
	 */

	private static int	label	= 0;

	public static String allocate() {
		return "continue" + label++;
	}

}
