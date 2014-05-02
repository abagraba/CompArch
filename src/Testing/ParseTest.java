package Testing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Parsing.TAParser;



public class ParseTest {

	private static File[]	poemSource	= new File[] { new File("src/Testing/poetic.hnry") };
	private static File[]	baseSource	= new File[] { new File("src/Testing/original.hnry"), new File("src/Testing/lab.hnry"),
			new File("src/Testing/mystery.hnry") };

	public static void main(String[] args) {
		try {
			Output.out = new BufferedWriter(new FileWriter(new File("src/Testing/output.asm")));
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		TAParser ta = new TAParser(poemSource);
		ta.run();
		try {
			if (Output.out != null)
				Output.out.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
