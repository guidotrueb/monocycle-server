package io.monocycle.server.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DataPoint {

	private static final String DEFAULT_VALUE_KEY = "value";

	private String metric;

	private Map<String, DataHolder> valuesMap;

	public DataPoint() {
		valuesMap = new LinkedHashMap<String, DataHolder>();
	}

	public DataPoint(String metric) {
		this();
		this.metric = metric;
	}

	public DataPoint(String metric, DataHolder value) {
		this();
		this.metric = metric;
		setValue(value);
	}

	@JsonIgnore
	public Collection<String> getKeys() {
		return getValuesMap().keySet();
	}

	@JsonIgnore
	public Collection<DataHolder> getValues() {
		return getValuesMap().values();
	}

	@JsonIgnore
	public Object[] getSimpleValues() {

		Collection<DataHolder> values = getValues();

		Object[] simpleValues = new Object[values.size()];

		int i = 0;
		for (DataHolder value : values) {
			simpleValues[i++] = value.getValue();
		}

		return simpleValues;

	}

	@JsonIgnore
	public void setValue(DataHolder value) {
		valuesMap.put(DEFAULT_VALUE_KEY, value);
	}

	public void addValue(String key, DataHolder value) {
		valuesMap.put(key, value);
	}

	@JsonIgnore
	public DataHolder getValue() {
		return valuesMap.get(DEFAULT_VALUE_KEY);
	}

	@JsonIgnore
	public DataHolder getValue(String key) {
		return valuesMap.get(key);
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric.toLowerCase();
	}

	public Map<String, DataHolder> getValuesMap() {
		return valuesMap;
	}

	public void setValuesMap(Map<String, DataHolder> valuesMap) {
		this.valuesMap = valuesMap;
	}

	@Override
	public String toString() {
		return "DataPoint [metric=" + metric + ", valuesMap=" + valuesMap + "]";
	}

}
