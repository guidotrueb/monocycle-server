package io.monocycle.server.model.alert;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AlertCondition {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private AlertDefinition alertDefinition;

	@Enumerated(EnumType.STRING)
	private AlertConditionType type;

	@Enumerated(EnumType.STRING)
	private AlertConditionOperator operator;

	private String metric;

	private String monitoredColumn;

	private String baselineColumn;

	private String filterColumn;

	private String filterValue;

	private BigDecimal threshold;

	public AlertDefinition getAlertDefinition() {
		return alertDefinition;
	}

	public void setAlertDefinition(AlertDefinition alertDefinition) {
		this.alertDefinition = alertDefinition;
	}

	public AlertConditionType getType() {
		return type;
	}

	public void setType(AlertConditionType type) {
		this.type = type;
	}

	public AlertConditionOperator getOperator() {
		return operator;
	}

	public void setOperator(AlertConditionOperator operator) {
		this.operator = operator;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	public String getMonitoredColumn() {
		return monitoredColumn;
	}

	public void setMonitoredColumn(String monitoredColumn) {
		this.monitoredColumn = monitoredColumn;
	}

	public BigDecimal getThreshold() {
		return threshold;
	}

	public void setThreshold(BigDecimal threshold) {
		this.threshold = threshold;
	}

	public String getBaselineColumn() {
		return baselineColumn;
	}

	public void setBaselineColumn(String baselineColumn) {
		this.baselineColumn = baselineColumn;
	}

	public String getFilterColumn() {
		return filterColumn;
	}

	public void setFilterColumn(String filterColumn) {
		this.filterColumn = filterColumn;
	}

	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

	@Override
	public String toString() {

		return "AlertCondition [alertDefinition=" + alertDefinition + ", type=" + type + ", operator=" + operator + ", metric=" + metric
				+ ", monitoredColumn=" + monitoredColumn + ", threshold=" + threshold + ", baselineColumn=" + baselineColumn + ", filterColumn="
				+ filterColumn + ", filterValue=" + filterValue + "]";
	}

}
