package pz.monitor.service.sensor;

import pz.monitor.db.entity.Sensor;
import pz.monitor.db.query.Query;

public interface SensorQueryBuilder {
	Query<Sensor> build(String resourceName, String metricName);
}
