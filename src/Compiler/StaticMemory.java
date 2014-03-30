package Compiler;

import java.util.HashMap;

import Memory.Register;



public class StaticMemory {

	private int							memory		= 0;
	private HashMap<String, Integer>	variables	= new HashMap<String, Integer>();
	private static String				space		= ",\t";

	public void callProcedure() {
		Program.writeCommand("addi", Register.fp, space, Register.sp, space, Register.zero);
		// DOSHIT
		Program.writeCommand("addi", Register.sp, space, Register.fp, space, Register.zero);
		Program.writeCommand("jr", Register.ra);
	}

	public int getVariableAddress(String name) {
		if (!variables.containsKey(name)) {
			Debug.log("Creating new variable [" + name + "] in static memory slot " + memory);
			variables.put(name, memory);
			memory += 4;
		}
		return variables.get(name);
	}

	/**
	 * Stores the value of the target register into specified variable.
	 * 
	 * @param r
	 *            target register.
	 */
	public void setVariable(Register r, String var) {
		int address = getVariableAddress(var);
		Program.writeCommand("li", Register.ctmp, space, address);
		Program.writeCommand("sw", r, space, "0(", Register.ctmp, ")");
	}

	/**
	 * Loads the value of the specified variable into target register.
	 * 
	 * @param r
	 *            target register.
	 */
	public void getVariable(Register r, String var) {
		int address = getVariableAddress(var);
		Program.writeCommand("li", Register.ctmp, space, address);
		Program.writeCommand("lw", r, space, "0(", Register.ctmp, ")");
	}

	/**
	 * Pushes the value of the target register onto stack.
	 * 
	 * @param r
	 *            target register.
	 */
	public void stackPush(Register r) {
		Program.writeCommand("addi", Register.sp, space, Register.sp, space, "4");
		Program.writeCommand("sw", r, space, "0(", Register.sp, ")");
	}

	/**
	 * Pops the top of the stack into the target register.
	 * 
	 * @param r
	 *            target register.
	 */
	public void stackPop(Register r) {
		Program.writeCommand("lw", r, space, "0(", Register.sp, ")");
		Program.writeCommand("addi", Register.sp, space, Register.sp, space, "-4");
	}

	public static void main(String[] args) {
		StaticMemory sm = new StaticMemory();
		sm.stackPush(Register.a0);
		sm.stackPop(Register.a0);

		Program.debug();
	}

}
