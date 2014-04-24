package Testing;

import java.io.BufferedWriter;
import java.io.IOException;



public class Error {

	public static BufferedWriter	err	= null;

	public static void print(String s) {
		if (err != null)
			try {
				err.append(s);
				err.newLine();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		else
			System.out.println(s);
	}

	public static void flush() {
		try {
			err.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
