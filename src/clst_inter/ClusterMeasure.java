package clst_inter;

/**
 * Interface for grouping different inter-cluster-distance-measures together
 * @author Armin Schrenk
 *
 */
public interface ClusterMeasure {

	/**
	 * Computes the distance between two clusters with a special criterion
	 * @param a a cluster
	 * @param b another cluster
	 * @return the distance between the two clusters
	 */
	public Double dist(Object a, Object b);
}
