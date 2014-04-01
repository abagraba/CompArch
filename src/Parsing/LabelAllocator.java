package Parsing;

public class LabelAllocator {
	private static int	label	= 0;

	public static String allocate() {
		return "label" + label++;
	}
}
