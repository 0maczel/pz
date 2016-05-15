package pz.monitor.service.sensor;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.stream.Collectors;

import org.junit.Test;

import pz.monitor.db.entity.Sensor;
import pz.monitor.db.query.Constraint.ConstraintType;
import pz.monitor.db.query.Query;
import pz.monitor.db.query.QueryBuilder;
import pz.monitor.db.query.QueryInitializer;
import pz.monitor.db.query.Query.Order;

public class SensorQueryBuilderTests {
	@Test
	public void shouldBuildQuery_WithOrderByOnly() {
		// Arrange
		QueryInitializer queryInitializer = new QueryBuilder();
		SensorQueryBuilder queryBuilder = new SensorQueryBuilderImpl(queryInitializer);

		String resourceName = null;
		String metricName = null;

		// Act
		Query<Sensor> query = queryBuilder.build(resourceName, metricName);

		// Assert
		assertThat(query.getOrderProperties().size(), is(1));
		assertThat(query.getOrderProperties().get(0).property, is("id"));
		assertThat(query.getOrderProperties().get(0).order, is(Order.ASC));
		assertThat(query.isWithMaxResults(), is(false));
		assertThat(query.getDirectConstraints().size(), is(0));
		assertThat(query.getIndirectConstraints().size(), is(0));
	}

	@Test
	public void shouldBuildQuery_WithResourceName_AndWithMetricName_AndOrderBy() {
		// Arrange
		QueryInitializer queryInitializer = new QueryBuilder();
		SensorQueryBuilder queryBuilder = new SensorQueryBuilderImpl(queryInitializer);

		String resourceName = "Zeus";
		String metricName = "CPU";

		// Act
		Query<Sensor> query = queryBuilder.build(resourceName, metricName);

		// Assert
		assertThat(query.getOrderProperties().size(), is(1));
		assertThat(query.getOrderProperties().get(0).property, is("id"));
		assertThat(query.getOrderProperties().get(0).order, is(Order.ASC));
		assertThat(query.isWithMaxResults(), is(false));
		assertThat(query.getDirectConstraints().size(), is(0));
		assertThat(query.getIndirectConstraints().size(), is(2));
		assertThat(query.getIndirectConstraints().stream()
				.filter(e -> e.referencePropertyName.equals("resource") && e.propertyName.equals("name")
						&& e.constraint.type == ConstraintType.EQ)
				.collect(Collectors.toList()).get(0).constraint.value, is(resourceName));
		assertThat(query.getIndirectConstraints().stream()
				.filter(e -> e.referencePropertyName.equals("metric") && e.propertyName.equals("name")
						&& e.constraint.type == ConstraintType.EQ)
				.collect(Collectors.toList()).get(0).constraint.value, is(metricName));
	}
}
