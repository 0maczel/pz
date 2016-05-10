package pz.monitor.service.measurement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.junit.Test;

import pz.monitor.db.entity.Measurement;
import pz.monitor.db.query.Constraint.ConstraintType;
import pz.monitor.db.query.Query;
import pz.monitor.db.query.Query.Order;
import pz.monitor.db.query.QueryBuilder;
import pz.monitor.db.query.QueryInitializer;

public class MeasurementQueryBuilderTests {
	@Test
	public void shouldBuildQuery_WithOrderByOnly() {
		// Arrange
		QueryInitializer queryInitializer = new QueryBuilder();
		MeasurementQueryBuilder queryBuilder = new MeasurementQueryBuilder(queryInitializer);

		String resourceLike = null;
		String metricLike = null;
		Timestamp fromDate = null;
		Timestamp toDate = null;
		Long limit = null;

		// Act
		Query<Measurement> query = queryBuilder.build(resourceLike, metricLike, fromDate, toDate, limit);

		// Assert
		assertEquals(1, query.getOrderProperties().size());
		assertEquals("creationTimestamp", query.getOrderProperties().get(0).property);
		assertEquals(Order.DESC, query.getOrderProperties().get(0).order);
		assertFalse(query.isWithMaxResults());
		assertTrue(query.getDirectConstraints().isEmpty());
		assertTrue(query.getIndirectConstraints().isEmpty());
	}

	@Test
	public void shouldBuildQuery_WithLimit_AndOrderBy() {
		// Arrange
		QueryInitializer queryInitializer = new QueryBuilder();
		MeasurementQueryBuilder queryBuilder = new MeasurementQueryBuilder(queryInitializer);

		String resourceLike = null;
		String metricLike = null;
		Timestamp fromDate = null;
		Timestamp toDate = null;
		Long limit = new Long(25);

		// Act
		Query<Measurement> query = queryBuilder.build(resourceLike, metricLike, fromDate, toDate, limit);

		// Assert
		assertEquals(1, query.getOrderProperties().size());
		assertEquals("creationTimestamp", query.getOrderProperties().get(0).property);
		assertEquals(Order.DESC, query.getOrderProperties().get(0).order);
		assertTrue(query.isWithMaxResults());
		assertEquals(25, query.getMaxResults());
		assertTrue(query.getDirectConstraints().isEmpty());
		assertTrue(query.getIndirectConstraints().isEmpty());
	}

	@Test
	public void shouldBuildQuery_WithDates_AndOrderBy() {
		// Arrange
		QueryInitializer queryInitializer = new QueryBuilder();
		MeasurementQueryBuilder queryBuilder = new MeasurementQueryBuilder(queryInitializer);

		String resourceLike = null;
		String metricLike = null;
		Timestamp fromDate = Timestamp.valueOf(LocalDateTime.now());
		Timestamp toDate = Timestamp.valueOf(LocalDateTime.now());
		Long limit = null;

		// Act
		Query<Measurement> query = queryBuilder.build(resourceLike, metricLike, fromDate, toDate, limit);

		// Assert
		assertEquals(1, query.getOrderProperties().size());
		assertEquals("creationTimestamp", query.getOrderProperties().get(0).property);
		assertEquals(Order.DESC, query.getOrderProperties().get(0).order);
		assertFalse(query.isWithMaxResults());
		assertTrue(query.getIndirectConstraints().isEmpty());
		assertEquals(2, query.getDirectConstraints().size());
		assertEquals(fromDate,
				query.getDirectConstraints().stream()
						.filter(e -> e.propertyName.equals("creationTimestamp")
								&& e.constraint.type == ConstraintType.GT)
						.collect(Collectors.toList()).get(0).constraint.value);
		assertEquals(toDate,
				query.getDirectConstraints().stream()
						.filter(e -> e.propertyName.equals("creationTimestamp")
								&& e.constraint.type == ConstraintType.LT)
						.collect(Collectors.toList()).get(0).constraint.value);
	}

	@Test
	public void shouldBuildQuery_WithLikes_AndOrderBy() {
		// Arrange
		QueryInitializer queryInitializer = new QueryBuilder();
		MeasurementQueryBuilder queryBuilder = new MeasurementQueryBuilder(queryInitializer);

		String resourceLike = "%zeus%";
		String metricLike = "%CPU%";
		Timestamp fromDate = null;
		Timestamp toDate = null;
		Long limit = null;

		// Act
		Query<Measurement> query = queryBuilder.build(resourceLike, metricLike, fromDate, toDate, limit);

		// Assert
		assertEquals(1, query.getOrderProperties().size());
		assertEquals("creationTimestamp", query.getOrderProperties().get(0).property);
		assertEquals(Order.DESC, query.getOrderProperties().get(0).order);
		assertFalse(query.isWithMaxResults());
		assertTrue(query.getDirectConstraints().isEmpty());
		assertEquals(2, query.getIndirectConstraints().size());
		assertEquals(resourceLike,
				query.getIndirectConstraints().stream()
						.filter(e -> e.referencePropertyName.equals("resource") && e.propertyName.equals("name")
								&& e.constraint.type == ConstraintType.LIKE)
						.collect(Collectors.toList()).get(0).constraint.value);
		assertEquals(metricLike,
				query.getIndirectConstraints().stream()
						.filter(e -> e.referencePropertyName.equals("metric") && e.propertyName.equals("name")
								&& e.constraint.type == ConstraintType.LIKE)
						.collect(Collectors.toList()).get(0).constraint.value);
	}
}
