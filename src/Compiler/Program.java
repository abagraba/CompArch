package Compiler;

public class Program {

	public static boolean		emulate	= false;
	private static StringBuffer	buffer	= new StringBuffer();

	public static void writeCommand(String command, Object... args) {
		buffer.append(command);
		for (int i = 0; i < 2 - command.length() / 4; i++)
			buffer.append('\t');
		for (Object arg : args)
			buffer.append(arg);
		buffer.appendCodePoint('\n');
	}

	public static void debug() {
		System.out.println(buffer);
	}

}
