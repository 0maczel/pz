package pz.monitor.service.resource;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import pz.monitor.db.entity.Resource;
import pz.monitor.db.query.Constraint.ConstraintType;
import pz.monitor.db.query.Query;
import pz.monitor.db.query.Query.Order;
import pz.monitor.db.query.QueryBuilder;
import pz.monitor.db.query.QueryInitializer;

public class ResourceQueryBuilderTests {
	@Test
	public void shouldBuildQuery_WithOrderByOnly() {
		// Arrange
		QueryInitializer queryInitializer = new QueryBuilder();
		ResourceQueryBuilder queryBuilder = new ResourceQueryBuilderImpl(queryInitializer);
		
		String nameLike = null;
		
		// Act
		Query<Resource> query = queryBuilder.build(nameLike); 
		
		// Assert
		assertThat(query.getOrderProperties().size(), is(1));
		assertThat(query.getOrderProperties().get(0).property, is("id"));
		assertThat(query.getOrderProperties().get(0).order, is(Order.ASC));
		assertThat(query.isWithMaxResults(), is(false));
		assertThat(query.getDirectConstraints().size(), is(0));
		assertThat(query.getIndirectConstraints().size(), is(0));
	}
	
	@Test
	public void shouldBuildQuery_WithLike_AndOrderByOnly() {
		// Arrange
		QueryInitializer queryInitializer = new QueryBuilder();
		ResourceQueryBuilder queryBuilder = new ResourceQueryBuilderImpl(queryInitializer);
		
		String nameLike = "%zeus%";
		
		// Act
		Query<Resource> query = queryBuilder.build(nameLike); 
		
		// Assert
		assertThat(query.getOrderProperties().size(), is(1));
		assertThat(query.getOrderProperties().get(0).property, is("id"));
		assertThat(query.getOrderProperties().get(0).order, is(Order.ASC));
		assertThat(query.isWithMaxResults(), is(false));
		assertThat(query.getDirectConstraints().size(), is(1));
		assertThat(query.getDirectConstraints().get(0).propertyName, is("name"));
		assertThat(query.getDirectConstraints().get(0).constraint.value, is(nameLike));
		assertThat(query.getDirectConstraints().get(0).constraint.type, is(ConstraintType.LIKE));
		assertThat(query.getIndirectConstraints().size(), is(0));
	}
}
