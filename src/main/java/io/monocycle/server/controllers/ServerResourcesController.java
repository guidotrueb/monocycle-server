package io.monocycle.server.controllers;

import io.monocycle.server.metric.MetricStorage;
import io.monocycle.server.model.Server;
import io.monocycle.server.repository.ServerRepository;

import java.util.List;

import org.influxdb.dto.Serie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/servers")
public class ServerResourcesController {

	private static Logger LOGGER = LoggerFactory.getLogger(ServerResourcesController.class);

	@Autowired
	private MetricStorage metricStorage;

	@Autowired
	private ServerRepository serverRepository;

	@RequestMapping(value = "/{serverId}/cpu")
	public Object[][] getDashboardCpu(@PathVariable("serverId") Long serverId) {

		LOGGER.debug("getDashboardCpu for {}", serverId);

		Server server = serverRepository.findById(serverId);

		List<Serie> metrics = metricStorage.queryLastHour(server, "cpu", "cpu");
		Serie serie = metrics.get(0);

		Object[][] points = serie.getPoints();
		Object[][] timeSeries = new Object[points.length][2];

		for (int i = 0; i < timeSeries.length; i++) {

			timeSeries[i][0] = ((Double) (points[i][0])).longValue() * 1000;
			timeSeries[i][1] = points[i][2];

		}

		return timeSeries;

	}

	@RequestMapping(value = "/{serverId}/memory")
	public Object[][] getDashboardMemory(@PathVariable("serverId") Long serverId) {

		LOGGER.debug("getDashboardMemory for {}", serverId);

		Server server = serverRepository.findById(serverId);

		List<Serie> metrics = metricStorage.queryLastHour(server, "memory_used", "memory");

		Serie serie = metrics.get(0);

		Object[][] points = serie.getPoints();
		Object[][] timeSeries = new Object[points.length][2];

		for (int i = 0; i < timeSeries.length; i++) {

			timeSeries[i][0] = ((Double) (points[i][0])).longValue() * 1000;
			timeSeries[i][1] = ((Double) (points[i][2])).longValue() / 1024 / 1024;

		}

		return timeSeries;

	}

}