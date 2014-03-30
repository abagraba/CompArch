package Compiler;

import java.util.HashMap;
import java.util.Stack;

import CodeGen.Method;
import Memory.RAM;
import Memory.Register;
import Memory.Variable;



public class SymbolTable {

	private static HashMap<String, RAM>		variables	= new HashMap<String, RAM>();
	private static HashMap<String, Method>	methods		= new HashMap<String, Method>();

	private static Stack<Method>			namespaces	= new Stack<Method>();
	private static Method					namespace;

	private static Register[]				tmps		= new Register[] { Register.t4, Register.t5, Register.t6, Register.t7 };
	private static int						tmp			= 0;

	public static Register getTmp() {
		if (tmp >= tmps.length)
			Debug.kill("Not enough temps to allocate. [" + tmp + "]");
		return tmps[tmp++];
	}

	public static void clearTmps() {
		tmp = 0;
	}

	public static void startMethod(Method method) {
		methods.put(method.name, method);
		if (namespace != null)
			namespaces.push(namespace);
		namespace = method;
	}

	public static void endMethod() {
		if (!namespaces.isEmpty())
			namespace = namespaces.pop();
		else
			namespace = null;
	}

	public static Variable resolve(String name) {
		if (namespace != null)
			return namespace.resolve(name);
		if (variables.containsKey(name))
			return variables.get(name);
		return null;
	}

	public static void debugTable() {
		System.out.println("Variables:");
		for (String var : variables.keySet())
			System.out.println('\t' + var);
		System.out.println("Methods:");
		for (Method method : methods.values()) {
			String s = '\t' + method.toString();
			System.out.println(s + "()");
		}
	}

}
