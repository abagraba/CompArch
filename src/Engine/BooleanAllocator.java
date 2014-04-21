package Engine;

import java.util.HashMap;
import java.util.LinkedList;



public class BooleanAllocator {

	private static HashMap<String, Boolean>	booleans	= new HashMap<String, Boolean>();
	private static LinkedList<String>		reg			= new LinkedList<String>();
	private static int						bit			= 0;
	static {
		String[] regs = new String[] { "$s4", "$s5", "$s6", "$s7" };
		for (String register : regs)
			reg.add(register);
	}

	public static Boolean interpret(String name) {
		if (!booleans.containsKey(name))
			booleans.put(name, newBoolean());
		return booleans.get(name);
	}

	private static Boolean newBoolean() {
		Boolean b = new Boolean(reg.getFirst(), bit++);
		if (bit == 32) {
			reg.removeFirst();
			bit = 0;
		}
		return b;
	}
}
