package Engine;

public class InputAllocator {

	private static int	label	= 0;

	/**
	 * Allocates unique labels for use in input requests.
	 */

	public static String allocate() {
		return "input" + label++;
	}

	// Used by the switchr functions to return to the last input request if invalid input is given.
	public static String last() {
		if (label == 0)
			throw new NullPointerException();
		return "input" + (label - 1);
	}

}
