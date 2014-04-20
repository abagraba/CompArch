package Engine;

import java.util.LinkedHashMap;



public class StringAllocator {

	private static LinkedHashMap<String, String>	labels	= new LinkedHashMap<String, String>();
	private static int								label	= 0;

	static {
		allocate("\\n");
		allocate("[1]	");
		allocate("[2]	");
		allocate("[3]	");
		allocate("[4]	");
		allocate("[5]	");
		allocate("[6]	");
		allocate("[7]	");
		allocate("[8]	");
		allocate("[9]	");
		allocate("Press space to continue...\\n");
		allocate("ERROR!!!");
		allocate("[a]	");
		allocate("[b]	");
		allocate("[c]	");
		allocate("[d]	");
		allocate("[e]	");
		allocate("[f]	");
		allocate("[g]	");
		allocate("[h]	");
		allocate("[i]	");
		allocate("[j]	");
		allocate("[k]	");
		allocate("[l]	");
		allocate("[m]	");
		allocate("[n]	");
		allocate("[o]	");
		allocate("[p]	");
		allocate("[q]	");
		allocate("[r]	");
		allocate("[s]	");
		allocate("[t]	");
		allocate("[u]	");
		allocate("[v]	");
		allocate("[w]	");
		allocate("[x]	");
		allocate("[y]	");
		allocate("[z]	");
		allocate("[ ]	");
	}

	public static String allocate(String string) {
		if (labels.containsValue(string))
			for (String s : labels.keySet())
				if (labels.get(s).equals(string))
					return s;
		labels.put("string" + label, string);
		return "string" + label++;
	}

	public static String generateMIPSData() {
		StringBuilder sb = new StringBuilder();
		for (String s : labels.keySet()) {
			sb.append(s);
			sb.append(":\n\t.asciiz \"");
			sb.append(labels.get(s));
			sb.append("\"\n");
		}
		return sb.toString();
	}

	/**
	 * Gets the label representing the address of the specified string.
	 * 
	 * @param s
	 *            string to interpret.
	 * @return label representing address of string.
	 */
	public static String interpret(String s) {
		String r = labels.get(s);
		if (r == null) {
			System.err.println("Could not resolve [\"" + s + "\"].");
			throw new NullPointerException();
		}
		return r;
	}
}
