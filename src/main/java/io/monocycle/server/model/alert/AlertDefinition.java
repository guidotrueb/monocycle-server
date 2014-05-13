package io.monocycle.server.model.alert;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class AlertDefinition {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String description;

	@OneToMany(mappedBy = "alertDefinition", cascade = CascadeType.ALL)
	private Set<AlertCondition> conditions;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private Set<AlertNotificationType> notificationTypes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<AlertCondition> getConditions() {
		return conditions;
	}

	public void setConditions(Set<AlertCondition> conditions) {
		this.conditions = conditions;
	}

	public Set<AlertNotificationType> getNotificationTypes() {
		return notificationTypes;
	}

	public void setNotificationTypes(Set<AlertNotificationType> notificationTypes) {
		this.notificationTypes = notificationTypes;
	}

	@Override
	public String toString() {
		return "AlertDefinition [description=" + description + ", conditions=" + conditions + ", notificationTypes=" + notificationTypes + "]";
	}

}
