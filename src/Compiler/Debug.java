package Compiler;

public class Debug {

	private static boolean	debug	= true;

	public static void log(String s) {
		if (debug)
			System.out.println(s);
	}

	public static void kill(String string) {
		System.out.println(string);
		System.exit(-1);
	}

}
