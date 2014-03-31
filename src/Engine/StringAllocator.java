package Engine;

import java.util.HashMap;



public class StringAllocator {

	private static HashMap<String, String>	labels	= new HashMap<String, String>();
	private static int						label	= 0;

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
			sb.append(":\n\t.asciiz(\"");
			sb.append(labels.get(s));
			sb.append("\");\n");
		}
		return sb.toString();
	}

	public static String interpret(String s) {
		return labels.get(s);
	}

}
