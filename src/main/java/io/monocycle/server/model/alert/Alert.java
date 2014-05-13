package io.monocycle.server.model.alert;

import io.monocycle.server.model.Server;

import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Alert {

	@ManyToOne
	private Server server;
	
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date ocurrenceDate;

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getOcurrenceDate() {
		return ocurrenceDate;
	}

	public void setOcurrenceDate(Date ocurrenceDate) {
		this.ocurrenceDate = ocurrenceDate;
	}
	
}
