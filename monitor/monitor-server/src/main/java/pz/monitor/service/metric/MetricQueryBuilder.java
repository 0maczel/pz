package pz.monitor.service.metric;

import pz.monitor.db.entity.Metric;
import pz.monitor.db.query.Query;

public interface MetricQueryBuilder {
	Query<Metric> build(String nameLike);
}
