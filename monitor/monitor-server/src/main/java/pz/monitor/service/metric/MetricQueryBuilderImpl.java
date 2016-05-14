package pz.monitor.service.metric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pz.monitor.db.entity.Metric;
import pz.monitor.db.query.Is;
import pz.monitor.db.query.Query;
import pz.monitor.db.query.QueryConstraint;
import pz.monitor.db.query.QueryInitializer;

@Component
public class MetricQueryBuilderImpl implements MetricQueryBuilder {
	private QueryInitializer queryInitializer;
	
	@Autowired
	public MetricQueryBuilderImpl(QueryInitializer queryInitializer) {
		this.queryInitializer = queryInitializer;
	}
	
	@Override
	public Query<Metric> build(String nameLike) {
		QueryConstraint<Metric> queryConstraint = queryInitializer.queryFor(Metric.class);
		if(nameLike != null) queryConstraint = queryConstraint.that("name", Is.like(nameLike)).and();
		Query<Metric> query = queryConstraint.orderBy("id").asc().build();
		return query;
	}

}
