package io.monocycle.server.alert;

import io.monocycle.server.metric.MetricStorageListener;
import io.monocycle.server.model.DataPoint;
import io.monocycle.server.model.DoubleDataHolder;
import io.monocycle.server.model.Server;
import io.monocycle.server.model.alert.AlertCondition;
import io.monocycle.server.model.alert.AlertConditionType;
import io.monocycle.server.model.alert.AlertDefinition;
import io.monocycle.server.model.alert.AlertNotificationType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MetricsAlertListener implements MetricStorageListener {

	private static Logger LOGGER = LoggerFactory.getLogger(MetricsAlertListener.class);
	
	public List<AlertDefinition> alerts;
	
	@Autowired
	private JavaMailSender javaMailSender;

	public MetricsAlertListener() {
		alerts = new ArrayList<AlertDefinition>();		
	}

	@Override
	public void onStore(Server server, List<DataPoint> dataPoints) {

		LOGGER.debug("Processing Datapoints for alerts...");
		
		for (DataPoint data : dataPoints) {
			processAlerts(server, data);
		}

	}

	private void processAlerts(Server server, DataPoint data) {

		for (AlertDefinition alert : alerts) {

			LOGGER.debug("Processing alert: {}", alert);
			boolean fire = true;

			for (AlertCondition condition : alert.getConditions()) {

				LOGGER.debug("Processing condition: {}", condition);
				
				if (fire == false || !data.getMetric().equals(condition.getMetric())) {
					
					LOGGER.debug("Metric don't match, ignoring: {} vs {}.", data.getMetric(), condition.getMetric());
					 
					fire = false;
					break;
					
				}
				
				if (!data.getValue(condition.getFilterColumn()).getValue().equals(condition.getFilterValue())) {
					
					LOGGER.debug("Filtered away... {} vs {}", data.getValue(condition.getFilterColumn()), condition.getFilterValue());
					 
					fire = false;
					break;
					
				}

				Double value = ((DoubleDataHolder) data.getValue(condition.getMonitoredColumn())).getValue();

				if (AlertConditionType.BASELINE.equals(condition.getType())) {

					Double baselineValue = ((DoubleDataHolder) data.getValue(condition.getBaselineColumn())).getValue();
					value = value / baselineValue * 100;

				}

				switch (condition.getOperator()) {
				case EQUAL:
					if (value != condition.getThreshold().doubleValue()) {
						fire = false;
					}
					break;
				case GREATER_OR_EQUAL:
					if (value < condition.getThreshold().doubleValue()) {
						fire = false;
					}
					break;
				case GREATER_THAN:
					if (value <= condition.getThreshold().doubleValue()) {
						fire = false;
					}
					break;
				case LESS_OR_EQUAL:
					if (value > condition.getThreshold().doubleValue()) {
						fire = false;
					}
					break;
				case LESS_THAN:
					if (value >= condition.getThreshold().doubleValue()) {
						fire = false;
					}
					break;
				case NOT_EQUAL:
					if (value == condition.getThreshold().doubleValue()) {
						fire = false;
					}
					break;
				}

			}

			if (fire) {
				
				for (AlertNotificationType notificationType : alert.getNotificationTypes()) {
					if (AlertNotificationType.EMAIL.equals(notificationType)) {
						processAlertEmailNotification(alert, data);
					}
				}
				

			}

		}

	}

	// TODO Implement this...
	private void processAlertEmailNotification(AlertDefinition alert, DataPoint data) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom("from@gmail.com");
		message.setTo("brcosta@gmail.com");
		message.setSubject("Monocycle Alert: " + new Date());
		message.setText(alert.toString() + "\n\n" + data.toString());
		
		javaMailSender.send(message);
		
	}

}
