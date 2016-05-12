package pz.monitor.service.measurement;

import java.sql.Timestamp;

import pz.monitor.db.entity.Measurement;
import pz.monitor.db.query.Query;

public interface MeasurementQueryBuilder {
	Query<Measurement> build(String resourceLike, String metricLike, Timestamp fromDate, Timestamp toDate, Long limit);
}