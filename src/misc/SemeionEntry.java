package misc;

import java.util.Map;
import java.util.Map.Entry;

/**
 * Eine schnellere Implementierung der myEntry-Klasse (weniger Objektaufrufe)
 * @author Armin Schrenk
 *
 */
public class SemeionEntry implements Entry<double[][], double[][]> {
	
	private double[][] key;
	private double[][] value;

	public SemeionEntry(double[][] key, double[][] value) {
		this.key = key;
		this.value = value;
	}

	public SemeionEntry(Map.Entry<double[][],double[][]> entry) {
		if(entry.getKey()==null || entry.getValue() ==null)
			throw new IllegalArgumentException("Null-Werte sind nicht erlaubt");
		this.key = entry.getKey();
		this.value = entry.getValue();
	}

	public double[][] setKey(double[][] key) {
		double[][] tmp = this.key;
		this.key = key;
		return tmp;
	}

	public double[][] setValue(double[][] value) {
		double[][] tmp = this.value;
		this.value = value;
		return tmp;
	}

	public double[][] getValue() {
		return this.value;
	}

	public double[][] getKey() {
		return this.key;
	}
	
	public double[][] getInput(){
		return getKey();
	}

	public double[][] getClassification(){
		return getValue();
	}

}
