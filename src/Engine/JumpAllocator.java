package Engine;

import java.util.ArrayList;



public class JumpAllocator {

	private static ArrayList<Integer>	tables	= new ArrayList<Integer>();
	private static int					label	= 0;

	public static int allocate(int jumps) {
		tables.add(jumps);
		return label++;
	}

	public static String generateMIPSData() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tables.size(); i++) {
			sb.append("jt" + i);
			sb.append(":\n\t.word ");
			for (int l = 0; l < tables.size(); l++)
				sb.append("JL" + i + '_' + l + ", ");
			sb.append("JD" + i);
			sb.append("\n");
		}
		return sb.toString();
	}
}
