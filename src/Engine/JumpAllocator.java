package Engine;

import java.util.ArrayList;

import Parsing.Switch;



public class JumpAllocator {

	private static ArrayList<Integer>	tables	= new ArrayList<Integer>();
	private static int					label	= 0;

	/**
	 * Allocates unique labels for use in generating jump tables.
	 */
	public static int allocate(int jumps) {
		tables.add(jumps);
		return label++;
	}

	public static String generateMIPSData() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tables.size(); i++) {
			sb.append(Switch.jumpTable + i);
			sb.append(":\n\t.word ");
			for (int l = 0; l < tables.get(i); l++)
				sb.append(Switch.jumpCase + i + '_' + (l + 1) + ", ");
			sb.append(Switch.jumpResolve + i);
			sb.append("\n");
		}
		return sb.toString();
	}
}
