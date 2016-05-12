package pz.monitor.service.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pz.monitor.db.entity.Resource;
import pz.monitor.db.query.Is;
import pz.monitor.db.query.Query;
import pz.monitor.db.query.QueryConstraint;
import pz.monitor.db.query.QueryInitializer;

@Component
public class ResourceQueryBuilderImpl implements ResourceQueryBuilder {
	private QueryInitializer queryInitializer;
	
	@Autowired
	public ResourceQueryBuilderImpl(QueryInitializer queryInitializer) {
		this.queryInitializer = queryInitializer;
	}
	
	@Override
	public Query<Resource> build(String nameLike) {
		QueryConstraint<Resource> queryConstraint = queryInitializer.queryFor(Resource.class);
		if(nameLike != null) queryConstraint = queryConstraint.that("name", Is.like(nameLike)).and();
		Query<Resource> query = queryConstraint.orderBy("id").asc().build();
		return query;
	}
}
