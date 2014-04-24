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
		File err = new File("log.txt");

		try {
			Output.out = new BufferedWriter(new FileWriter(out));
			Error.err = new BufferedWriter(new FileWriter(err));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		if (!in.exists()) {
			Error.print("[input.hnry] does not exist.");
			Error.flush();
			return;
		}

		try {
			TAParser ta = new TAParser(new FileReader(in));
			ta.run();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		Output.flush();
		Error.print("Compilation complete");
		Error.flush();
	}
}
