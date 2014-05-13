package io.monocycle.server.controllers;

import io.monocycle.server.metric.MetricStorage;
import io.monocycle.server.model.DataPoint;
import io.monocycle.server.model.Server;
import io.monocycle.server.repository.ServerRepository;
import io.monocycle.support.ServerConstants;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/collector")
public class DataPointCollectorController {

	@Autowired
	private MetricStorage metricStorage;

	@Autowired
	private ServerRepository serverRepository;

	@RequestMapping(value = "/{hostname}", method = RequestMethod.POST)
	public String collectDataPoint(@PathVariable("hostname") String hostname, @RequestBody List<DataPoint> dataPoints) {

		Server server = serverRepository.findByHostname(hostname);
		metricStorage.store(server, dataPoints);

		return ServerConstants.RESPONSE_OK;

	}

}
