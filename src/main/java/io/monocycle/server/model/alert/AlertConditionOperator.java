package io.monocycle.server.model.alert;

public enum AlertConditionOperator {

	EQUAL("="), NOT_EQUAL("!="), LESS_THAN("<"), LESS_OR_EQUAL("<="), GREATER_THAN(">"), GREATER_OR_EQUAL(">=");

	private String description;

	private AlertConditionOperator(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
