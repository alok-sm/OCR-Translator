
package com.alok.ocrsdk;

public class BusCardSettings {

	public String asUrlParams() {
		return String.format("language=%s&exportFormat=%s", language, outputFormat);
	}

	public enum OutputFormat {
		vCard, xml, csv
	}

	public void setOutputFormat(OutputFormat format) {
		outputFormat = format;
	}

	public OutputFormat getOutputFormat() {
		return outputFormat;
	}

	public void setLanguage(String newLanguage) {
		language = newLanguage;
	}

	public String getLanguage() {
		return language;
	}

	private String language = "English";
	private OutputFormat outputFormat = OutputFormat.vCard;
}
