package io.monocycle.server.controllers;

import io.monocycle.server.model.Server;
import io.monocycle.server.repository.ServerRepository;
import io.monocycle.support.ServerConstants;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/servers")
public class ServerController {

	private static Logger LOGGER = LoggerFactory.getLogger(ServerController.class);

	@Autowired
	private ServerRepository serverRepository;

	@RequestMapping(value = "/{hostname}/update", method = RequestMethod.POST)
	public String updateServer(@PathVariable("hostname") String hostname, @RequestBody Server server) {

		LOGGER.debug("Updating Server {}", hostname);

		Server currentServer = serverRepository.findByHostname(hostname);

		if (currentServer == null) {

			LOGGER.debug("Registering server: {}...", hostname);

			server.getServerSummary().setCreatedDate(new Date());
			server.getServerSummary().setUpdatedDate(new Date());

			serverRepository.save(server);

			LOGGER.debug("Server registered: {}.", hostname);

		} else {

			LOGGER.debug("Server already registered: {}...", hostname);

			currentServer.setDescription(server.getDescription());

			currentServer.getServerSummary().setCoreCount(server.getServerSummary().getCoreCount());

			currentServer.getServerSummary().getMountPoints().clear();
			currentServer.getServerSummary().getMountPoints().addAll(server.getServerSummary().getMountPoints());

			currentServer.getServerSummary().setTotalMemory(server.getServerSummary().getTotalMemory());
			currentServer.getServerSummary().setTotalSwap(server.getServerSummary().getTotalSwap());

			currentServer.getServerSummary().setVendorName(server.getServerSummary().getVendorName());
			currentServer.getServerSummary().setVendorDescription(server.getServerSummary().getVendorDescription());

			currentServer.getServerSummary().setUpdatedDate(new Date());

			serverRepository.save(currentServer);

			LOGGER.debug("Server updated: {}.", hostname);

		}

		return ServerConstants.RESPONSE_OK;

	}

	@RequestMapping(value = "/{serverId}", method = RequestMethod.GET)
	public Server getServer(@PathVariable("serverId") Long serverId) {

		return serverRepository.findById(serverId);

	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Server> getAll() {
		return serverRepository.findAll();
	}

}
