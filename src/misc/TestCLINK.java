package misc;

import clink.CLINK;
import clst_intra.EuclideanDistance;

/**
 * Example use case class for the CLINK algorithm
 * @author Armin Schrenk
 *
 */
public class TestCLINK {

	/**
	 * Main Method
	 * @param args Array of arguments
	 * 		only one is accepted, the path to the data file
	 */
	public static void main(String[] args) {
		if(args.length!=1){
			System.err.println("Please only specifiy the path to the data file");
			throw new IllegalArgumentException("Only one argument is allowed. Please only specifiy the path to the data file");
		}
		
		easyTest();
		
//		double [][] data = SemeionParser.read(args[0]);
//		CLINK c = new CLINK(new EuclideanDistance());
//		c.cluster(data);
		
//		System.out.println("Done");
	}
	
	public static void easyTest(){
		double [][] data = {{1,1},{2,1},{2,6},{4,6},{3,7},{8,2},{9,3},{10,4},{11,5},{12,6}};
		CLINK c = new CLINK(new EuclideanDistance());
		c.cluster(data);
		System.out.println("Done");
	}

}
