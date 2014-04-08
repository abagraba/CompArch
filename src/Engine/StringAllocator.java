package Engine;

import java.util.HashMap;



public class StringAllocator {

	private static HashMap<String, String>	labels	= new HashMap<String, String>();
	private static int						label	= 0;

	static {
		allocate("\\n");
		allocate("ERROR!!!");
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
