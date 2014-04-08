package Testing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Parsing.TAParser;



public class ParseTest {

	public static void main(String[] args) {
		try {
			Output.out = new BufferedWriter(new FileWriter(new File("src/Testing/output.asm")));
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			TAParser ta = new TAParser(new FileReader(new File("src/Testing/sample.txt")));
			ta.run();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			if (Output.out != null)
				Output.out.flush();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
