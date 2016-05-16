package pz.monitor.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.Test;

import pz.monitor.db.entity.Measurement;
import pz.monitor.db.entity.Metric;
import pz.monitor.db.query.Constraint.ConstraintType;
import pz.monitor.db.query.Is;
import pz.monitor.db.query.Query;
import pz.monitor.db.query.Query.Order;
import pz.monitor.db.query.QueryBuilder;
import pz.monitor.db.query.QueryInitializer;

public class QueryTests {
	@Test
	public void shouldBuildQueryWithMaxResults() {
		// Arrange
		QueryInitializer queryBuilder = new QueryBuilder();

		// Act
		Query<Metric> query = queryBuilder.queryFor(Metric.class).withMaxResults(25).build();

		// Assert
		assertEquals(Metric.class, query.getType());
		assertTrue(query.isWithMaxResults());
		assertEquals(25, query.getMaxResults());
	}

	@Test
	public void shouldBuildQueryWithSingleDirectConstraint() {
		// Arrange
		QueryInitializer queryBuilder = new QueryBuilder();

		// Act
		Query<Metric> query = queryBuilder.queryFor(Metric.class).that("name", Is.equalTo("CPU")).build();

		// Assert
		assertEquals(Metric.class, query.getType());
		assertFalse(query.isWithMaxResults());
		assertEquals(1, query.getDirectConstraints().size());
		assertEquals("name", query.getDirectConstraints().get(0).propertyName);
		assertEquals(ConstraintType.EQ, query.getDirectConstraints().get(0).constraint.type);
		assertEquals("CPU", query.getDirectConstraints().get(0).constraint.value);
	}

	@Test
	public void shouldBuildQueryWithTreeDirectConstraints() {
		// Arrange
		QueryInitializer queryBuilder = new QueryBuilder();

		// Act
		Query<Metric> query = queryBuilder.queryFor(Metric.class).that("name", Is.equalTo("CPU")).and()
				.that("id", Is.greaterThan(10)).and().that("version", Is.lessThan(1)).build();

		// Assert
		assertEquals(Metric.class, query.getType());

		assertEquals(3, query.getDirectConstraints().size());
		assertEquals("name", query.getDirectConstraints().get(0).propertyName);
		assertEquals(ConstraintType.EQ, query.getDirectConstraints().get(0).constraint.type);
		assertEquals("CPU", query.getDirectConstraints().get(0).constraint.value);

		assertEquals("id", query.getDirectConstraints().get(1).propertyName);
		assertEquals(ConstraintType.GT, query.getDirectConstraints().get(1).constraint.type);
		assertEquals(10, query.getDirectConstraints().get(1).constraint.value);

		assertEquals("version", query.getDirectConstraints().get(2).propertyName);
		assertEquals(ConstraintType.LT, query.getDirectConstraints().get(2).constraint.type);
		assertEquals(1, query.getDirectConstraints().get(2).constraint.value);
	}

	@Test
	public void shouldBuildQueryWithOrderAscendingByName_AndDescendingByCreationTimestamp_AndWithLimitOf10Results() {
		// Arrange
		QueryInitializer queryBuilder = new QueryBuilder();

		// Act
		Query<Metric> query = queryBuilder.queryFor(Metric.class).orderBy("name").asc().and()
				.orderBy("creationTimestamp").desc().and().withMaxResults(10).build();

		// Assert
		assertEquals(Metric.class, query.getType());

		assertTrue(query.isWithMaxResults());
		assertEquals(10, query.getMaxResults());

		assertEquals("name", query.getOrderProperties().get(0).property);
		assertEquals(Order.ASC, query.getOrderProperties().get(0).order);

		assertEquals("creationTimestamp", query.getOrderProperties().get(1).property);
		assertEquals(Order.DESC, query.getOrderProperties().get(1).order);
	}

	@Test
	public void shouldBuildQueryWithTwoIndirectConstraints() {
		// Arrange
		QueryInitializer queryBuilder = new QueryBuilder();

		// Act
		Query<Measurement> query = queryBuilder.queryFor(Measurement.class).with("metric")
				.that("name", Is.like("%CPU%")).and().with("resource").that("name", Is.like("%zeus%")).build();

		// Assert
		assertEquals(Measurement.class, query.getType());

		assertEquals(2, query.getIndirectConstraints().size());
		assertEquals("metric", query.getIndirectConstraints().get(0).referencePropertyName);
		assertEquals("name", query.getIndirectConstraints().get(0).propertyName);
		assertEquals(ConstraintType.LIKE, query.getIndirectConstraints().get(0).constraint.type);
		assertEquals("%CPU%", query.getIndirectConstraints().get(0).constraint.value);

		assertEquals("resource", query.getIndirectConstraints().get(1).referencePropertyName);
		assertEquals("name", query.getIndirectConstraints().get(1).propertyName);
		assertEquals(ConstraintType.LIKE, query.getIndirectConstraints().get(1).constraint.type);
		assertEquals("%zeus%", query.getIndirectConstraints().get(1).constraint.value);
	}

	@Test
	public void shouldBuildQueryWithTwoDirectConstraints_AndWithTwoIndirectConstraints_AndWithOrderAscendingAndDescending_AndWithLimit() {
		// Arrange
		QueryInitializer queryBuilder = new QueryBuilder();
		Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

		// Act
		// Controversial as 'that' after 'and' returns to 'queryFor' class
		// level, not stays at referenced type level
		Query<Measurement> query = queryBuilder.queryFor(Measurement.class).that("id", Is.greaterThan(50)).and()
				.with("metric").that("name", Is.equalTo("CPU")).and().that("creationTimestamp", Is.lessThan(timestamp))
				.and().with("resource").that("name", Is.like("zeus%")).and().orderBy("creationTimestamp").desc().and()
				.orderBy("value").asc().and().withMaxResults(150).build();

		// Assert
		assertEquals(Measurement.class, query.getType());

		assertEquals(2, query.getDirectConstraints().size());
		assertEquals("id", query.getDirectConstraints().get(0).propertyName);
		assertEquals(ConstraintType.GT, query.getDirectConstraints().get(0).constraint.type);
		assertEquals(50, query.getDirectConstraints().get(0).constraint.value);
		assertEquals("creationTimestamp", query.getDirectConstraints().get(1).propertyName);
		assertEquals(ConstraintType.LT, query.getDirectConstraints().get(1).constraint.type);
		assertEquals(timestamp, query.getDirectConstraints().get(1).constraint.value);

		assertEquals(2, query.getIndirectConstraints().size());
		assertEquals("metric", query.getIndirectConstraints().get(0).referencePropertyName);
		assertEquals("name", query.getIndirectConstraints().get(0).propertyName);
		assertEquals(ConstraintType.EQ, query.getIndirectConstraints().get(0).constraint.type);
		assertEquals("CPU", query.getIndirectConstraints().get(0).constraint.value);
		assertEquals("resource", query.getIndirectConstraints().get(1).referencePropertyName);
		assertEquals("name", query.getIndirectConstraints().get(1).propertyName);
		assertEquals(ConstraintType.LIKE, query.getIndirectConstraints().get(1).constraint.type);
		assertEquals("zeus%", query.getIndirectConstraints().get(1).constraint.value);

		assertEquals("creationTimestamp", query.getOrderProperties().get(0).property);
		assertEquals(Order.DESC, query.getOrderProperties().get(0).order);
		assertEquals("value", query.getOrderProperties().get(1).property);
		assertEquals(Order.ASC, query.getOrderProperties().get(1).order);

		assertTrue(query.isWithMaxResults());
		assertEquals(150, query.getMaxResults());
	}
}
