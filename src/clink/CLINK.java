package clink;

import java.util.Arrays;
import java.util.Collection;

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
	 * @param k the k-clustering, after if its computed the algorithm stops
	 * @return true if the computation was sucessful
	 */
	public boolean cluster(double[][] input, int k){
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
		
		return computeClustering(k);
	}
	
	/**
	 * delegate methoh to cluster arbitrary collections
	 * @param input a collection of points of same dimension
	 * @param k the k-clustering, after if its computed the algorithm stops
	 * @return true if a clustering could be computed
	 */
	public boolean cluster(Collection<double[]> input, int k){
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
		return computeClustering(k);
	}
	
	/**
	 * Real method for computing the clustering
	 * @param k the number of clusters in current CLustering after which the algorithm stops
	 * @return true if the computation was sucessfull
	 */
	private boolean computeClustering(int k){
		int n = data.length;
		clustering =new Cluster[n-k];
		Cluster[] currClustering = new Cluster[n];
		//keep track which entry of the matrix corresponds to wich cluster
		int [] matrixToCluster = new int [n];
		
		//add the singleton cluster
		for(int i=0;i<n;i++){
			currClustering[i]= new Cluster(-i-1,data[i]);
			matrixToCluster[i]=i;
		}
		
		for(int x=n-1;x>k-1;x--){
			
			//1. find closest clusters
			double min = Double.POSITIVE_INFINITY;
			int a=-1,b=-1;
			for(int i=0;i<D.length;i++){
				for(int j=i+1;j<D.length;j++){
					if(D[i][j]<min){
						min=D[i][j];
						a=i;
						b=j;
					}
				}
			}
			
			//merge them
			int a2 = matrixToCluster[a];
			int b2 = matrixToCluster[b];
			clustering[x-k] = new Cluster(x-k, min,currClustering[a2],currClustering[b2]);
			currClustering[a2]= clustering[x-k];
			currClustering[b2]= clustering[x-k];
			
			//update the matrix
			double [][] D_tmp = new double[x][x];
			for(int i=0;i<x;i++){
				for(int j=i+1;j<x; j++) {
					if(i==a){
						if(j<b) {
							D_tmp[i][j] = (D[a][j]+D[b][j]+Math.abs(D[a][j]-D[b][j]))/2;
						}
						else{
							D_tmp[i][j] = (D[a][j+1]+D[b][j+1]+Math.abs(D[a][j+1]-D[b][j+1]))/2;
						}
					}
					else if(i<b){
						if(j<b) {
							D_tmp[i][j] = D[i][j];
						}
						else {
							D_tmp[i][j] = D[i][j+1];
						}
					}
					else {
						if(j<b) {
							D_tmp[i][j] = D[i+1][j];
						}
						else {
							D_tmp[i][j] = D[i+1][j+1];
						}
					}
				}
			}
			D = D_tmp;
			
			//update the matrixToCluster vector
			for(int i=b;i< x; i++) {
				matrixToCluster[i]=matrixToCluster[i+1];
			}
		}
		return true;
	}
	
	public Cluster [] getClustering() {
		return clustering;
	}
	
	public double[][] getInput(){
		return data;
	}
}
