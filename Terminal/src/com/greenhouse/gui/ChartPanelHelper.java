package com.greenhouse.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialRange;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.general.ValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

public class ChartPanelHelper {

	public static ChartPanel getDialChart(double temperature) {

		ValueDataset dataset = new DefaultValueDataset(temperature);
		DialPlot plot;
		JFreeChart chart;

		plot = new DialPlot(dataset);

		StandardDialFrame dialFrame = new StandardDialFrame();
		dialFrame.setBackgroundPaint(Color.lightGray);
		dialFrame.setForegroundPaint(Color.darkGray);
		plot.setDialFrame(dialFrame);

		GradientPaint gp = new GradientPaint(new Point(), new Color(255, 255,
				255), new Point(), new Color(170, 170, 220));
		DialBackground db = new DialBackground(gp);
		db.setGradientPaintTransformer(new StandardGradientPaintTransformer(
				GradientPaintTransformType.VERTICAL));
		plot.setBackground(db);

		DialTextAnnotation annotation1 = new DialTextAnnotation("Temperature");
		annotation1.setFont(new Font("Dialog", Font.BOLD, 14));
		annotation1.setRadius(0.7);

		plot.addLayer(annotation1);

		DialValueIndicator dvi = new DialValueIndicator(0);
		plot.addLayer(dvi);

		StandardDialScale scale = new StandardDialScale(-40, 60, -120, -300, 5,5);
		scale.setTickRadius(0.88);
		scale.setTickLabelOffset(0.15);
		scale.setTickLabelFont(new Font("Dialog", Font.PLAIN, 14));
		plot.addScale(0, scale);

		StandardDialRange range = new StandardDialRange(40.0, 60.0, Color.red);
		range.setInnerRadius(0.52);
		range.setOuterRadius(0.55);
		plot.addLayer(range);

		StandardDialRange range2 = new StandardDialRange(10.0, 40.0,
				Color.orange);
		range2.setInnerRadius(0.52);
		range2.setOuterRadius(0.55);
		plot.addLayer(range2);

		StandardDialRange range3 = new StandardDialRange(-40.0, 10.0,
				Color.green);
		range3.setInnerRadius(0.52);
		range3.setOuterRadius(0.55);
		plot.addLayer(range3);

		DialPointer needle = new DialPointer.Pointer();
		plot.addLayer(needle);

		DialCap cap = new DialCap();
		cap.setRadius(0.10);
		plot.setCap(cap);

		chart = new JFreeChart(plot);

		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}
}
