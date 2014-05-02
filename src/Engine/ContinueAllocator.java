package Engine;

public class ContinueAllocator {

	private static int	label	= 0;

	public static String allocate() {
		return "continue" + label++;
	}

}
