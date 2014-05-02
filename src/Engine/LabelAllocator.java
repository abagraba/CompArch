package Engine;

public class LabelAllocator {
	private static int	label	= 0;

	/**
	 * Allocates unique labels.
	 */
	public static String allocate() {
		return "label" + label++;
	}
}
