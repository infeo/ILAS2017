package misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Map.Entry;
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
	public static Entry<double [][],double[][]> read(String pathToFile) {
		Locale.setDefault(Locale.ENGLISH);
		int dimension = 256;
		int size = 1593;
		int classes = 10;
		double [][] data = new double[size][dimension];
		double [][] classification = new double[size][classes];
		try {
			File fpath = new File(pathToFile);
			Scanner scannerFile = new Scanner(fpath);
			for(int i=0;i<size;i++){
				for(int j=0;j<dimension;j++){
					data[i][j]=scannerFile.nextDouble();
				}
				
				for(int j=0;j<classes;j++){
					classification[i][j]= scannerFile.nextDouble();
				}
//				scannerFile.nextLine();
			}
			scannerFile.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("File not found");
		}
		return new SemeionEntry(data,classification);
	}
}
