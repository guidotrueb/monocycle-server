package io.monocycle.server.metric;

import io.monocycle.server.model.DataPoint;
import io.monocycle.server.model.Server;

import java.util.List;

public interface MetricStorageListener {

	public void onStore(Server server, List<DataPoint> dataPoints);
	
}
