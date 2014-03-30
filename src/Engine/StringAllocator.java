package Engine;

import java.util.HashMap;



public class StringAllocator {

	private static HashMap<String, String>	labels	= new HashMap<String, String>();
	private static int						label	= 0;

	public String allocate(String string) {
		if (!labels.containsKey(string))
			labels.put(string, "string" + label++);
		return labels.get(string);
	}

	public String generateMIPS() {
		StringBuilder sb = new StringBuilder();
		sb.append(".data\n");
		for (String s : labels.keySet()) {
			sb.append(labels.get(s));
			sb.append(":\n\t.asciiz(");
			sb.append(s);
			sb.append(");");
		}
		return sb.toString();
	}

}
