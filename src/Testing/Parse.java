package Testing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import Parsing.TAParser;



public class Parse {

	public static void main(String[] args) {
		LinkedList<File> in = new LinkedList<File>();
		File out = new File("output.asm");
		File err = new File("log.txt");

		try {
			Output.out = new BufferedWriter(new FileWriter(out));
			Error.err = new BufferedWriter(new FileWriter(err));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		File[] files = new File(".").listFiles();
		for (File file : files)
			if (file.getName().matches(".+\\.hnry")) {
				Error.print("File " + file.getName() + " found.");
				in.add(file);
			}

		if (in.isEmpty()) {
			Error.print("No .hnry files found.");
			Error.flush();
			return;
		}

		Error.print("Parsing....");
		Error.flush();
		TAParser ta = new TAParser(in.toArray(new File[in.size()]));
		ta.run();

		Output.flush();
		Error.print("Compilation complete");
		Error.flush();
	}
}
