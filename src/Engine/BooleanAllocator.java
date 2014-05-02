package Engine;

import java.util.HashMap;
import java.util.LinkedList;



/**
 * Allocates unused bits in the $s registers for usage as veriables. Can allocate up to 128 booleans.
 */
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
			booleans.put(name, newBoolean(name));
		return booleans.get(name);
	}

	private static Boolean newBoolean(String name) {
		Boolean b = new Boolean(name, reg.getFirst(), bit++);
		if (bit == 32) {
			reg.removeFirst();
			bit = 0;
		}
		return b;
	}
}
