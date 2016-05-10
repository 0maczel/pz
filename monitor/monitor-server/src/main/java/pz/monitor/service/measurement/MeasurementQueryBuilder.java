package pz.monitor.service.measurement;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pz.monitor.db.entity.Measurement;
import pz.monitor.db.query.Is;
import pz.monitor.db.query.Query;
import pz.monitor.db.query.QueryConstraint;
import pz.monitor.db.query.QueryInitializer;

@Component
public class MeasurementQueryBuilder {
	private QueryInitializer queryInitializer;

	@Autowired
	public MeasurementQueryBuilder(QueryInitializer queryInitializer) {
		this.queryInitializer = queryInitializer;
	}

	Query<Measurement> build(String resourceLike, String metricLike, Timestamp fromDate, Timestamp toDate, Long limit) {		
		QueryConstraint<Measurement> queryConstraint = queryInitializer.queryFor(Measurement.class);
		if(resourceLike != null) queryConstraint = queryConstraint.with("resource").that("name", Is.like(resourceLike)).and();
		if(metricLike != null) queryConstraint = queryConstraint.with("metric").that("name", Is.like(metricLike)).and();
		if(fromDate != null) queryConstraint = queryConstraint.that("creationTimestamp", Is.greaterThan(fromDate)).and();
		if(toDate != null) queryConstraint = queryConstraint.that("creationTimestamp", Is.lessThan(toDate)).and();
		if(limit != null) queryConstraint = queryConstraint.withMaxResults(limit.intValue()).and();
		
		Query<Measurement> query = queryConstraint.orderBy("creationTimestamp").desc().build();
		return query;
	}
}
