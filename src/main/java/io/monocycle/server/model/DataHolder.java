package io.monocycle.server.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = DoubleDataHolder.class, name = "double"),
		@JsonSubTypes.Type(value = StringDataHolder.class, name = "string") })
public interface DataHolder {

	Serializable getValue();

}
