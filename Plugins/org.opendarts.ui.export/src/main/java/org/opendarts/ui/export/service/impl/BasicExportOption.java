package org.opendarts.ui.export.service.impl;

import org.opendarts.core.export.IExportOptions;

// TODO: Auto-generated Javadoc
/**
 * The Class BasicExportOption.
 */
public class BasicExportOption implements IExportOptions {

	/**
	 * The Enum ImageType.
	 */
	public enum ImageType {

		/** The PNG. */
		PNG,
		/** The JPEG. */
		JPEG;
	}

	/** The export chart. */
	private boolean exportChart = true;

	/** The chart image type. */
	private ImageType chartImageType = ImageType.PNG;

	/** The chart image width. */
	private int chartImageWidth = 640;

	/** The chart image height. */
	private int chartImageHeight = 480;

	/** The zip. */
	private boolean zip;

	/** The csv separator. */
	private char csvSeparator = ';';

	/** The export stats as csv. */
	private boolean exportStatsAsCsv = true;

	/**
	 * Instantiates a new basic export option.
	 */
	public BasicExportOption() {
		super();
	}

	/**
	 * Checks if is export chart.
	 *
	 * @return true, if is export chart
	 */
	public boolean isExportChart() {
		return this.exportChart;
	}

	/**
	 * Sets the export chart.
	 *
	 * @param exportChart the new export chart
	 */
	public void setExportChart(boolean exportChart) {
		this.exportChart = exportChart;
	}

	/**
	 * Gets the chart image type.
	 *
	 * @return the chart image type
	 */
	public ImageType getChartImageType() {
		return this.chartImageType;
	}

	/**
	 * Sets the chart image type.
	 *
	 * @param chartImageType the new chart image type
	 */
	public void setChartImageType(ImageType chartImageType) {
		this.chartImageType = chartImageType;
	}

	/**
	 * Checks if is zip.
	 *
	 * @return true, if is zip
	 */
	public boolean isZip() {
		return this.zip;
	}

	/** The is chart png. */
	public boolean isChartPng() {
		return ImageType.PNG.equals(this.getChartImageType());
	}

	/**
	 * Sets the zip.
	 *
	 * @param zip the new zip
	 */
	public void setZip(boolean zip) {
		this.zip = zip;
	}

	/**
	 * Gets the csv separator.
	 *
	 * @return the csv separator
	 */
	public char getCsvSeparator() {
		return this.csvSeparator;
	}

	/**
	 * Sets the csv separator.
	 *
	 * @param csvSeparator the new csv separator
	 */
	public void setCsvSeparator(char csvSeparator) {
		this.csvSeparator = csvSeparator;
	}

	/**
	 * Checks if is export stats as csv.
	 *
	 * @return true, if is export stats as csv
	 */
	public boolean isExportStatsAsCsv() {
		return this.exportStatsAsCsv;
	}

	/**
	 * Sets the export stats as csv.
	 *
	 * @param exportStatsAsCsv the new export stats as csv
	 */
	public void setExportStatsAsCsv(boolean exportStatsAsCsv) {
		this.exportStatsAsCsv = exportStatsAsCsv;
	}

	/**
	 * Gets the chart image width.
	 *
	 * @return the chart image width
	 */
	public int getChartImageWidth() {
		return this.chartImageWidth;
	}

	/**
	 * Sets the chart image width.
	 *
	 * @param chartImageWidth the new chart image width
	 */
	public void setChartImageWidth(int chartImageWidth) {
		this.chartImageWidth = chartImageWidth;
	}

	/**
	 * Gets the chart image height.
	 *
	 * @return the chart image height
	 */
	public int getChartImageHeight() {
		return this.chartImageHeight;
	}

	/**
	 * Sets the chart image height.
	 *
	 * @param chartImageHeight the new chart image height
	 */
	public void setChartImageHeight(int chartImageHeight) {
		this.chartImageHeight = chartImageHeight;
	}

}
