package pz.monitor.service.sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pz.monitor.db.entity.Sensor;
import pz.monitor.db.query.Is;
import pz.monitor.db.query.Query;
import pz.monitor.db.query.QueryConstraint;
import pz.monitor.db.query.QueryInitializer;

@Component
public class SensorQueryBuilderImpl implements SensorQueryBuilder {
	private QueryInitializer queryInitializer;

	@Autowired
	public SensorQueryBuilderImpl(QueryInitializer queryInitializer) {
		this.queryInitializer = queryInitializer;
	}

	@Override
	public Query<Sensor> build(String resourceName, String metricName) {
		QueryConstraint<Sensor> queryConstraint = queryInitializer.queryFor(Sensor.class);
		if (resourceName != null)
			queryConstraint = queryConstraint.with("resource").that("name", Is.equalTo(resourceName)).and();
		if (metricName != null)
			queryConstraint = queryConstraint.with("metric").that("name", Is.equalTo(metricName)).and();
		Query<Sensor> query = queryConstraint.orderBy("id").asc().build();
		return query;
	}

}
