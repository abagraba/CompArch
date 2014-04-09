package Engine;

import java.util.HashMap;
import java.util.LinkedList;



/**
 * $t8, $t9 are reserved for variable manipulation
 */
public class VariableAllocator {
	private static LinkedList<Register>	reg			= new LinkedList<Register>();
	static {
		Register[] regs = new Register[] { new Register("$s4"), new Register("$s5"), new Register("$s6"), new Register("$s7") };
		for (Register register : regs)
			reg.add(register);
	}

	public HashMap<String, Variable>	variables	= new HashMap<String, Variable>();

	public static Variable allocate(int size) {
		return null;
	}

	public static char getHex(int i) {
		if (i < 10)
			return (char) ('0' + i);
		return (char) (i - 10 + 'A');
	}

}
