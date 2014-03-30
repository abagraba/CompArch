package CodeGen;

import java.util.HashMap;
import java.util.LinkedList;

import Compiler.SymbolTable;
import Memory.Register;
import Memory.Variable;



public class Method {

	public String						name;
	private LinkedList<QUAD>			quads		= new LinkedList<QUAD>();
	private HashMap<String, Register>	namespace	= new HashMap<String, Register>();
	private Register[]					argreg		= new Register[] { Register.a0, Register.a1, Register.a2, Register.a3 };
	private Register[]					tmpreg		= new Register[] { Register.t0, Register.t1, Register.t2, Register.t3, Register.t4, Register.t5,
			Register.t6, Register.t7, Register.t8	};

	public Method(String name, LinkedList<String> args) throws Exception {
		this.name = name;
		int i = 0;
		if (args != null)
			for (String arg : args) {
				if (i >= argreg.length)
					throw new Exception("Too many method arguments.");
				namespace.put(arg, argreg[i++]);
			}
	}

	public void addStmt(Statement stmt) {
		quads.addAll(stmt.resolve(this));
	}

	public Variable resolve(String name) {
		if (namespace.containsKey(name))
			return namespace.get(name);
		return SymbolTable.resolve(name);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder(name);
		return s.toString();
	}

}
