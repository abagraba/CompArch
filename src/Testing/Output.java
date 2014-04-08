package Testing;

import java.io.BufferedWriter;
import java.io.IOException;



public class Output {

	public static BufferedWriter	out	= null;

	public static void print(String s) {
		if (out != null)
			try {
				out.append(s);
				out.newLine();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		else
			System.out.println(s);
	}
}
