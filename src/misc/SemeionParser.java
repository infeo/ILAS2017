package misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Locale;
import java.util.Scanner;

/**
 * class for parsing the Semeion Handwritten Digit Data Set from the UCI
 * machinelearning repository
 * {@linkplain "http://archive.ics.uci.edu/ml/datasets/semeion+handwritten+digit"}
 * 
 * @author Armin Schrenk
 *
 */
public class SemeionParser {

	/**
	 * Method for parsing the file
	 * @param pathToFile path to the Semeion Handwritten Digit file
	 * @return the parsed data as a double matrix
	 */
	public static double [][] read(String pathToFile) {
		Locale.setDefault(Locale.ENGLISH);
		int dimension = 256;
		int size = 1593;
		double [][] data = new double[size][dimension];
		try {
			File fpath = new File(pathToFile);
			Scanner scannerFile = new Scanner(fpath);
			for(int i=0;i<size;i++){
				for(int j=0;j<dimension;j++){
					data[i][j]=scannerFile.nextDouble();
				}
				//currently the class is discarded
				scannerFile.nextLine();
			}
			scannerFile.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("File not found");
		}
		return data;
	}
}
