package io.monocycle.support;

import org.influxdb.dto.Serie;

public class SeriesUtils {

	private SeriesUtils() {
		// Private Constructor
	}

	public static Object[][] serieToTimeSeriesArray(Serie serie, int valueColumn) {

		Object[][] points = serie.getPoints();
		Object[][] timeSeries = new Object[points.length][2];

		for (int i = 0; i < timeSeries.length; i++) {

			timeSeries[i][0] = ((Double) (points[i][0])).longValue() * 1000;
			timeSeries[i][1] = points[i][valueColumn];

		}

		return timeSeries;

	}

}
