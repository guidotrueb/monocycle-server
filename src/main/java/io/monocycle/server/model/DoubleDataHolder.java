package io.monocycle.server.model;

public class DoubleDataHolder implements DataHolder {

	private Double value;
	
	public DoubleDataHolder() {
	}

	public DoubleDataHolder(Double value) {
		this.value = value;
	}

	public Double getValue() {
		return value;
	}

}
