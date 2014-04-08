package Engine;

public class InputAllocator {

	private static int	label	= 0;

	public static String allocate() {
		return "input" + label++;
	}

	public static String last() {
		if (label == 0)
			throw new NullPointerException();
		return "input" + (label - 1);
	}

}
