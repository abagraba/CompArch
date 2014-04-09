package Testing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Parsing.TAParser;



public class Parse {

	public static void main(String[] args) {
		File in = new File("input.hnry");
		File out = new File("output.asm");

		System.out.println(in.getAbsolutePath());
		System.out.println(out.getAbsolutePath());

		if (!in.exists()) {
			System.err.println("Input does not exist.");
			return;
		}

		try {
			Output.out = new BufferedWriter(new FileWriter(out));
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			TAParser ta = new TAParser(new FileReader(in));
			ta.run();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (Output.out != null)
				Output.out.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
