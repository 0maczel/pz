package pz.monitor.service.metric;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import pz.monitor.db.entity.Metric;
import pz.monitor.db.query.Query;
import pz.monitor.db.query.QueryBuilder;
import pz.monitor.db.query.QueryInitializer;
import pz.monitor.db.query.Constraint.ConstraintType;
import pz.monitor.db.query.Query.Order;

public class MetricQueryBuilderTests {
	@Test
	public void shouldBuildQuery_WithOrderByOnly() {
		// Arrange
		QueryInitializer queryInitializer = new QueryBuilder();
		MetricQueryBuilder queryBuilder = new MetricQueryBuilderImpl(queryInitializer);

		String nameLike = null;

		// Act
		Query<Metric> query = queryBuilder.build(nameLike);

		// Assert
		assertThat(query.getOrderProperties().size(), is(1));
		assertThat(query.getOrderProperties().get(0).property, is("id"));
		assertThat(query.getOrderProperties().get(0).order, is(Order.ASC));
		assertThat(query.isWithMaxResults(), is(false));
		assertThat(query.getDirectConstraints().size(), is(0));
		assertThat(query.getIndirectConstraints().size(), is(0));
	}

	@Test
	public void shouldBuildQuery_WithLike_AndOrderBy() {
		// Arrange
		QueryInitializer queryInitializer = new QueryBuilder();
		MetricQueryBuilder queryBuilder = new MetricQueryBuilderImpl(queryInitializer);

		String nameLike = "%CPU%";

		// Act
		Query<Metric> query = queryBuilder.build(nameLike);

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
