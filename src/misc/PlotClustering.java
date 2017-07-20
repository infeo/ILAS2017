package misc;

import java.awt.Color;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import clink.CLINK;
import clink.Cluster;

/**
 * Class containing static methods for ease the plotting of a computed clustering
 * @author Alf
 *
 */
public class PlotClustering {

	/**
	 * Makes a Dotrender with a dot-size of 4*4 px
	 * @return XYDotRender w
	 */
	public static XYDotRenderer makeRender(int dotSize) {
		XYDotRenderer dotrender = new XYDotRenderer();
		dotrender.setDotHeight(dotSize);
		dotrender.setDotWidth(dotSize);
		return dotrender;
	}

	/**
	 * Adds a lot of stuff to the plot
	 * @param plot plot to where the stuff should be added
	 * @param index the index in the plot, where the stuff should be added
	 * @param data the dataset
	 * @param x the x-axis
	 * @param y the y-axis
	 * @param render the used render
	 */
	public static void addDataToPlot(XYPlot plot, int index, XYDataset data, ValueAxis x, ValueAxis y,
			XYItemRenderer render) {
		plot.setDataset(index, data);
		plot.setDomainAxis(index, x);
		plot.setRangeAxis(index, y);
		plot.setRenderer(index, render);
	}

	/**
	 * Shows the plot ind a window named string
	 * @param plot the plot to be shown
	 * @param name name of the window
	 * @return an ApplicationFrame ("Chartpanel")
	 */
	public static ApplicationFrame makeFrame(XYPlot plot, String name) {
		JFreeChart chart = new JFreeChart(plot);
		ChartPanel chartPanel = new ChartPanel(chart);
		ApplicationFrame frame = new ApplicationFrame(name);
		frame.setContentPane(chartPanel);
		frame.pack();
		return frame;
	}
	
	/**
	 * Visualizes the given HMC up to k-1 cluster 
	 * @param upToLayer maximal layer to show clustering
	 * @param fromLayer minimal layer to show clustering
	 * @param hc the hierachical clustering (already computed)
	 */
	public static void showHierarchicalClustering(int upToLayer, int fromLayer, CLINK hc, int dotSize) {

		int realK = upToLayer-1;
		int sizeIndividual = fromLayer;
		
		// a point series to mark the single cluster centers
		XYSeries showData = new XYSeries("Eingabedaten",false);
		XYSeries showCenter = new XYSeries("Clusterzentren", false);
		

		// a point series to connect the single centers to show the hierachy
		XYSeries[] showCenters = new XYSeries[sizeIndividual - 1 - realK];

		XYSeriesCollection collData = new XYSeriesCollection();
		collData.addSeries(showData);
		collData.addSeries(showCenter);
		
		XYSeriesCollection collClst = new XYSeriesCollection();
		for (int i = 0; i < showCenters.length; i++) {
			showCenters[i] = new XYSeries("Cluster" + Integer.toString(i), false);
			collClst.addSeries(showCenters[i]);
		}

		// the point render for the centers
		XYDotRenderer dotrender1 = PlotClustering.makeRender(dotSize);

		// the line render for the center-connections
		XYLineAndShapeRenderer linerender = new XYLineAndShapeRenderer();
		linerender.setBaseShapesVisible(false);

		XYPlot plot = new XYPlot();
		plot.setDataset(0, collData);
		plot.setDomainAxis(0, new NumberAxis("x"));
		plot.setRangeAxis(0, new NumberAxis("y"));
		plot.setRenderer(0, dotrender1);
		plot.setDataset(1, collClst);
		plot.setRenderer(1, linerender);
		plot.setBackgroundPaint(new Color(220, 220, 220));

		JFreeChart chart = new JFreeChart(plot);
		ChartPanel chartPanel = new ChartPanel(chart);
//		chart.removeLegend();
		ApplicationFrame frame = new ApplicationFrame("Hierachical Means Clustering up to Layer "+upToLayer);
		frame.setContentPane(chartPanel);
		frame.pack();

		Cluster[] tmp = hc.getClustering();
		double [][] input = hc.getInput();
		
		for(double [] elem : input){
			showData.add(elem[0], elem[1]);
		}
		
		for (int i = realK; i < sizeIndividual - 1; i++) {
			Cluster c = tmp[i];
			showCenter.add(c.center[0], c.center[1]);
			showCenters[i - realK].add(c.center[0], c.center[1]);
			if (c.child1 != null) {
				showCenters[i - realK].add(c.child1.center[0], c.child1.center[1]);
				showCenters[i - realK].add(c.child2.center[0], c.child2.center[1]);
			}
		}

		frame.setVisible(true);
	}
	
}
