package clink;

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
	public long size;
	
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
	 * Constructs a Cluster with the given attributes.
	 * @param index Index in the hierarchy array
	 * @param hetero the distance of the to merged clusters
	 * @param child1 One predecessor of the cluster
	 * @param child2 Otherone predecessor of the cluster
	 */
	public Cluster(int index, double hetero, Cluster child1, Cluster child2){
		this.index=index;
		if(child1==null || child2==null){
			size=1;
		}
		else{
			size= child1.size +child2.size;
		}
		this.heterogeneousity = hetero;
		this.child1=child1;
		this.child2=child2;
	}
}
