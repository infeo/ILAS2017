package misc;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import clink.CLINK;
import clink.Cluster;
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
		
//		easyTest();
		
		SemeionEntry data = (SemeionEntry) SemeionParser.read(args[0]);
		CLINK c = new CLINK(new EuclideanDistance());
		c.cluster(data.getInput(),10);
		System.out.println("Computing Clustering : Done");
		computeConfusion(c, 10, data.getClassification());
		
	}
	
	/**
	 * A little test method with the input {{1,1},{2,1},{2,6},{4,6},{3,7},{8,2},{9,3}}
	 */
	public static void easyTest(){
		double [][] data = {{1,1},{2,1},{2,6},{4,6},{3,7},{8,2},{9,3}}; //,{10,4},{11,5},{12,6}};
		double [][] classification = {{1,0,0},{1,0,0},{0,1,0},{0,1,0},{0,1,0},{0,0,1},{0,0,1}};
		CLINK c = new CLINK(new EuclideanDistance());
		c.cluster(data, 3);
		System.out.println("Done");
		computeConfusion(c, 3, classification);
		PlotClustering.showHierarchicalClustering(1, c.getClustering().length+1, c, 4);
	}

	/**
	 * Computes the confusion matrix and prints it in a file
	 */
	public static void computeConfusion(CLINK c, int k, double[][] classification){
		int numOfClasses = classification[0].length;
		Cluster [] hC = Arrays.copyOf(c.getClustering(), c.getClustering().length);
		int[][] confusion = new int [k][numOfClasses];
		
		int x=0;
		for(int i=0;i<k;i++){
			//find first entry not null
			while(hC[x]== null){
				x++;
			}
			// traverse tree
			Cluster curr;
			Deque<Integer> deq = new ArrayDeque<Integer>();
			deq.push(x);
			while(!deq.isEmpty()){
				int currIndex = Math.abs(deq.pop());
				int tmp;
				curr = hC[currIndex];
				//we check if the left child is a point
				tmp = curr.child1.index;
				if(tmp<0){
					for(int j=0;j<numOfClasses;j++){
						confusion[i][j]+=classification[-(tmp+1)][j];
					}
				}
				else{
					deq.push(tmp);
				}
				// and we check the right child
				tmp = curr.child2.index;
				if(tmp<0){
					for(int j=0;j<numOfClasses;j++){
						confusion[i][j]+=classification[-(tmp+1)][j];
					}
				}
				else{
					deq.push(tmp);
				}
				
				// an we delete the reference in the array
				hC[currIndex]=null;
			}
		}
		
		for(int i=0;i<confusion.length;i++){
			System.out.println(Arrays.toString(confusion[i]));
		}
		
		System.out.println("Computing Confusion matrix:Done");
		
	}
	
}
