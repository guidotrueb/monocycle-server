package io.monocycle.server.model;

public class StringDataHolder implements DataHolder {

	private String value;

	public StringDataHolder() {
		// Default constructor
	}

	public StringDataHolder(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
