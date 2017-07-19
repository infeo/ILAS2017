package clink;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map.Entry;

import clst_inter.ClusterMeasure;
import clst_intra.DistanceMeasure;

/**
 * Class for computing hierarchical clusterings
 * @author Armin Schrenk
 */
public class CLINK{
	
	/**
	 * Intra cluster measure
	 */
	public DistanceMeasure d_pts;
	
	/**
	 *	input, on which a hierarchy is computed
	 */
	private double [][] data;
	
	/**
	 * the distance matrix
	 */
	private double [][] D;
	
	/**
	 * the clustering, coded as an array
	 */
	private Cluster [] clustering;
	
	/**
	 * Constructor for setting the two distance measure in cluster and inputspace
	 * @param intra measure for distances between points
	 */
	public CLINK( DistanceMeasure intra){
		d_pts = intra;
	}
	
	/**
	 * computes for the given method a hierarchical clustering accordingly to the distance measures
	 * @param input a matrix of d dimensional vectors
	 * @return true if the computation was sucessful
	 */
	public boolean cluster(double[][] input){
		int count = input.length;
		if(count == 0){
			System.err.println("Input is empty");
			return false;
		}
		int dimension = input[0].length;
		this.data = new double[count][dimension];
		this.D = new double[count][count];
		for(int i=0;i<count; i++){
			//copy the points
			data[i] = Arrays.copyOf(input[i], dimension);
			//distance to the point itself is zero
			D[i][i] = 0;
			//distance to the other points
			for(int j=i+1; j<count;j++){
				D[i][j]=d_pts.dist(input[j],input[i]);
			}
		}
		
		return computeClustering();
	}
	
	/**
	 * TODO: füge noch die singleton cluster richtig dem clustering hinzu!
	 * delegate methoh to cluster arbitrary collections
	 * @param input a collection of points of same dimension
	 * @return true if a clustering could be computed
	 */
	public boolean cluster(Collection<double[]> input){
		if (input == null)
			throw new IllegalArgumentException("no given input!");
		if (input.isEmpty())
			throw new IllegalArgumentException("input must contain at least one element");
		
		int count = input.size();
		
		//the first item returned by the iterator has by definition the correct dimension
		int dimension = input.iterator().next().length;
		
		// checks, if the input is coherent
		for (double[] elem : input) {
			if (elem.length != dimension)
				throw new IllegalArgumentException("The Points in the input set does not have the same dimension");
		}
	
		
		this.data = new double[count][dimension];
		this.D = new double[count][count];
		int l=0;
		for(double [] pt : input){
			data[l] = Arrays.copyOf(pt, dimension);
			l++;
		}
		
		for(int i=0; i<count; i++){
			D[i][i]=0;
			for(int j=i+1; j<count; j++){
				D[i][j]= d_pts.dist(data[i], data[j]);
			}
		}
		return computeClustering();
	}
	
	/**
	 * Real method for computing the clustering
	 * @return true if the computation was sucessfull
	 */
	private boolean computeClustering(){
		int n = data.length;
		clustering =new Cluster[(2*n)-1];
		for(int x=n-1;x>=0;x--){
			
			//1. find closest clusters
			double min = Double.POSITIVE_INFINITY;
			int a=-1,b=-1;
			for(int i=0;i<n;i++){
				for(int j=i+1;j<n;j++){
					if(D[i][j]<min){
						min=D[i][j];
						a=i;
						b=j;
					}
				}
			}
			
			//merge them
			clustering[x] = new Cluster(x,min , clustering[a], clustering[b]);
			
			//update the matrix
			double tmp;
			for(int i=0;i<n;i++){
				tmp=(D[a][i]+D[b][i]+Math.abs(D[a][i]-D[b][i]))/2;
				D[a][i] =tmp;
				D[i][a]=tmp;
				D[i][b]=tmp;
				D[b][i]=tmp;
			}
		}
		return true;
	}
}
