package Testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import Parsing.TAParser;



public class ParseTest {

	public static void main(String[] args) {
		try {
			TAParser ta = new TAParser(new FileReader(new File("src/Testing/test.txt")));
			ta.run();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
