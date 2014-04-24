package Testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



public class LineCount {

	public static void main(String[] args) {
		File root = new File("src/");
		int j = count(root, ".*\\.java");
		int jc = count(root, "TA(Parser||Lexer||ParserVal)\\.java");
		int h = count(root, ".*\\.hnry");
		int m = count(root, ".*\\.asm");
		int c = count(root, ".*\\.(y||flex)");

		System.out.println(j - jc + " lines of java code.");
		System.out.println(c + " lines of Compiler Specification.");
		System.out.println(h + " lines of hnry code.");

		System.out.println();

		System.out.println(m + " lines of MIPS.");
		System.out.println(jc + " lines of Compiler auto-generated code.");
	}

	public static int count(File f, String regex) {
		if (!f.exists()) {
			System.err.println(f + " does not exist.");
			return 0;
		}
		if (!f.isDirectory()) {
			if (f.getName().matches(regex))
				return countLines(f, regex);
			return 0;
		}
		int lines = 0;
		File[] fa = f.listFiles();
		for (File ff : fa)
			lines += count(ff, regex);
		return lines;
	}

	public static int countLines(File f, String regex) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		}
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		int i = 0;
		try {
			while (br.readLine() != null)
				i++;
		}
		catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		return i;
	}
}
