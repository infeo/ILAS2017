package clink;

import java.util.Arrays;

/**
 * Class for representing a cluster in a hierarchical clustering. This class is for external classes to get acess to the computed hierarchy
 * @author Armin Schrenk
 *
 */
public class Cluster {

	/**
	 * The index of the cluster in the hierachy-array
	 */
	public int index;
	
	/**
	 * The cluster size (number of points)
	 */
	public int size;
	
	/**
	 *  The cluster center
	 */
	public double[] center;
	
	/**
	 * One predecessor of the cluster
	 */
	public Cluster child1;
	
	/**
	 * Otherone predecessor of the cluster
	 */
	public Cluster child2;
	
	/**
	 * distance of childs, the higher the distance the higher the heterogenousity
	 */
	public double heterogeneousity;
	
	/**
	 * Construct a singleton cluster
	 * @param index Index in the clustering array
	 * @param point the point which this cluster represents
	 */
	public Cluster(int index, double [] point) {
		this.index =index;
		child1=null;
		child2=null;
		heterogeneousity =0;
		center = Arrays.copyOf(point, point.length);
		size = 1;
	}
	
	/**
	 * Constructs a Hierarchical Cluster out of its two childs.
	 * @param index Index in the hierarchy array
	 * @param hetero the distance of the to merged clusters
	 * @param child1 One predecessor of the cluster
	 * @param child2 Otherone predecessor of the cluster
	 */
	public Cluster(int index, double hetero, Cluster child1, Cluster child2){
		this.index=index;
		this.size= child1.size +child2.size;
		this.heterogeneousity = hetero;
		this.child1=child1;
		this.child2=child2;
		
		this.center = new double[child1.center.length];
		
		for(int i=0;i<center.length;i++) {
			this.center[i]=(child1.size*child1.center[i]+child2.size*child2.center[i])/size;
		}
		
	}
}
