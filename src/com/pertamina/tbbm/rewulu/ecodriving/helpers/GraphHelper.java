package com.pertamina.tbbm.rewulu.ecodriving.helpers;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.widget.LinearLayout;

public class GraphHelper {
	private XYSeries xySeries;
	private GraphicalView chartView;
	private XYMultipleSeriesDataset dataset;
	private XYMultipleSeriesRenderer renderer;
	int graphMax = 10;
	int graphStart = 0;

	public GraphHelper(Context context, LinearLayout container) {
		// TODO Auto-generated constructor stub
		xySeries = new XYSeries("s/km");
		
		// Now we add our series
		dataset = new XYMultipleSeriesDataset();
		xySeries.add(0, 0);
		dataset.addSeries(xySeries);
		// addSeries(0.2);
		XYSeriesRenderer xySeriesRenderer = new XYSeriesRenderer();
		xySeriesRenderer.setLineWidth(5);
		xySeriesRenderer.setColor(Color.BLUE);
		// Include low and max value
		xySeriesRenderer.setDisplayBoundingPoints(true);
		// we add point markers
		xySeriesRenderer.setPointStyle(PointStyle.POINT);
		xySeriesRenderer.setPointStrokeWidth(7);

		// Finaly we create the multiple series renderer to control the graph
		renderer = new XYMultipleSeriesRenderer();
		renderer.addSeriesRenderer(xySeriesRenderer);

		// We want to avoid black border
		renderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent
		// Disable Pan on two axis
		renderer.setPanEnabled(true, false);
		renderer.setXTitle("jarak (km)");
		renderer.setYTitle("waktu (detik)");

		renderer.setXAxisMin(0);
		renderer.setXAxisMax(5);
		renderer.setXLabels(6);
		renderer.setXRoundedLabels(true);

		renderer.setYAxisMin(0);
		renderer.setYAxisMax(200);
		renderer.setYLabels(5);
		renderer.setShowGrid(true);
		renderer.setGridColor(Color.WHITE);
		renderer.setInScroll(true);
		renderer.setAxesColor(Color.BLACK);
		renderer.setLabelsColor(Color.BLACK);
		renderer.setXLabelsColor(Color.RED);
		renderer.setYLabelsColor(0, Color.RED);

		renderer.setFitLegend(true);
		renderer.setLabelsTextSize(18);
		renderer.setAxisTitleTextSize(24);
		renderer.setLegendTextSize(24);
		renderer.setMargins(new int[] { 20, 48, 16, 16 });

		renderer.setYLabelsAlign(Align.LEFT);
		chartView = ChartFactory.getLineChartView(context, dataset, renderer);
		container.addView(chartView);
	}


	public void addSeriesChart(double x, double y) {
		// TODO Auto-generated method stub
		int maxX = (int) xySeries.getMaxX();
		dataset.clear();
		xySeries.add(x, y);
		dataset.addSeries(xySeries);
		if (maxX + 0.5d > renderer.getXAxisMax()) {
			// renderer.setXLabels(7);
			renderer.setXAxisMin(maxX - 1);
			renderer.setXAxisMax(maxX + 4);
		}
		int maxY = (int) xySeries.getMaxY();
		if (maxY > renderer.getYAxisMax()) {
			renderer.setYAxisMin(0);
			renderer.setYAxisMax(maxY + 10);
		}
		chartView.repaint();
	}
}
