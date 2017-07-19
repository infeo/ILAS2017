package clst_intra;

public class EuclideanDistance implements DistanceMeasure {

	@Override
	public double dist(double[] a, double[] b) {
		double dist=0;
		for(int i=0;i<a.length;i++){
			dist+=Math.pow(b[i]-a[i],2);
		}
		return Math.sqrt(dist);
	}

}
