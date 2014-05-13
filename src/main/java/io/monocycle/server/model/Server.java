package io.monocycle.server.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Server {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String hostname;

	private String description;

	@ManyToOne(cascade = CascadeType.ALL)
	private ServerSummary serverSummary;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ServerSummary getServerSummary() {
		return serverSummary;
	}

	public void setServerSummary(ServerSummary serverSummary) {
		this.serverSummary = serverSummary;
	}

	@Override
	public String toString() {
		return "Server [id=" + id + ", hostname=" + hostname + ", description=" + description + ", serverSummary=" + serverSummary + "]";
	}

}
