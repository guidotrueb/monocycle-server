package io.monocycle.server.metric;

import io.monocycle.server.alert.MetricsAlertListener;
import io.monocycle.server.model.DataPoint;
import io.monocycle.server.model.Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Serie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("metricStorage")
public class MetricStorage {

	private static Logger LOGGER = LoggerFactory.getLogger(MetricStorage.class);

	private static final String[] EMPTY_STRING_ARRAY = new String[] {};

	private static final String DATABASE_NAME = "main_mcdb";

	private String host = "localhost";

	private Integer port = 8086;

	private String adminUser = "root";

	private String adminPassword = "root";

	private List<MetricStorageListener> listeners;

	private InfluxDB influxDB;

	@Autowired
	private MetricsAlertListener metricsAlertListener;

	@PostConstruct
	public void initialize() {

		listeners = new ArrayList<MetricStorageListener>();

		listeners.add(metricsAlertListener);

		influxDB = InfluxDBFactory.connect("http://" + host + ":" + port, adminUser, adminPassword);
		// influxDB.createDatabase(DATABASE_NAME, 1);

	}

	public void store(Server server, DataPoint dataPoint) {
		store(server, Collections.singletonList(dataPoint));
	}

	public void store(Server server, List<DataPoint> dataPoints) {

		List<Serie> series = new ArrayList<>();

		for (DataPoint dataPoint : dataPoints) {

			String metricName = "srv_" + server.getId() + "_" + dataPoint.getMetric();

			Serie serie = new Serie(metricName);
			serie.setColumns(dataPoint.getKeys().toArray(EMPTY_STRING_ARRAY));
			serie.setPoints(new Object[][] { dataPoint.getSimpleValues() });

			series.add(serie);

		}

		influxDB.write(DATABASE_NAME, series.toArray(new Serie[] {}), TimeUnit.SECONDS);

		for (MetricStorageListener listener : listeners) {
			listener.onStore(server, dataPoints);
		}

	}

	public List<Serie> queryLastHour(Server server, String column, String metricName) {

		String query = "select " + column + " from srv_" + server.getId() + "_" + metricName + " where time > now() - 1h group by time(1m)";
		LOGGER.debug("Executing query: {}", query);

		return influxDB.Query(DATABASE_NAME, query, TimeUnit.SECONDS);

	}

}
