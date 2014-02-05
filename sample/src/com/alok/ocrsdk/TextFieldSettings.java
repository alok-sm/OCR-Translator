package com.alok.ocrsdk;
public class TextFieldSettings {
	public String asUrlParams() {
		return String.format("language=%s&textType=%s", language, textType);
	}
public void setLanguage(String newLanguage) {
		language = newLanguage;
	}

	public String getLanguage() {
		return language;
	}

	public String getTextType() {
		return textType;
	}

	public void setTextType(String newTextType) {
		textType = newTextType;
	}

	private String language = "English";
	private String textType = "normal,handprinted";
}
