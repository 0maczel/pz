package pz.monitor.db;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import pz.monitor.db.entity.Measurement;
import pz.monitor.db.entity.Metric;
import pz.monitor.db.entity.Resource;
import pz.monitor.db.entity.Sensor;
import pz.monitor.db.helpers.MemoryDatabaseSessionProvider;
import pz.monitor.db.helpers.SessionFactoryProvider;
import pz.monitor.db.helpers.TestDataGenerator;
import pz.monitor.db.helpers.TestEntityFactory;
import pz.monitor.db.query.HibernateQueryConverter;
import pz.monitor.db.query.Is;
import pz.monitor.db.query.Query;
import pz.monitor.db.query.QueryBuilder;
import pz.monitor.db.query.QueryConverter;
import pz.monitor.db.query.QueryInitializer;

public class RepositoryTests {
	@Test
	public void shouldSaveNewMetric() throws Exception {
		try (SessionFactoryProvider sessionFactoryProvider = new MemoryDatabaseSessionProvider()) {
			Repository repository = new CRUDRepository(sessionFactoryProvider.getSessionFactory(), null);

			doInSession(sessionFactoryProvider, () -> {
				int countOfMetrics = repository.all(Metric.class).size();
				assertEquals(0, countOfMetrics);
			});

			doInSession(sessionFactoryProvider, () -> {
				repository.save(TestEntityFactory.getTestMetric());
			});

			doInSession(sessionFactoryProvider, () -> {
				int countOfMetrics = repository.all(Metric.class).size();
				assertEquals(1, countOfMetrics);
			});
		}
	}

	@Test
	public void shouldUpdateMetric() throws Exception {
		try (SessionFactoryProvider sessionFactoryProvider = new MemoryDatabaseSessionProvider()) {
			Repository repository = new CRUDRepository(sessionFactoryProvider.getSessionFactory(), null);

			Metric metricToSave = TestEntityFactory.getTestMetric();
			String newName = "New name of metric";

			doInSession(sessionFactoryProvider, () -> {
				int countOfMetrics = repository.all(Metric.class).size();
				assertEquals(0, countOfMetrics);
			});

			doInSession(sessionFactoryProvider, () -> {
				repository.save(metricToSave);
			});

			doInSession(sessionFactoryProvider, () -> {
				metricToSave.setName(newName);
				repository.save(metricToSave);
			});

			doInSession(sessionFactoryProvider, () -> {
				int countOfMetrics = repository.all(Metric.class).size();
				assertEquals(1, countOfMetrics);

				Metric m = repository.get(Metric.class, metricToSave.getId());
				assertEquals(newName, m.getName());
				assertEquals(1, m.getEntityVersion());
			});
		}
	}

	@Test
	public void shouldGetMetricById() throws Exception {
		try (SessionFactoryProvider sessionFactoryProvider = new MemoryDatabaseSessionProvider()) {
			Repository repository = new CRUDRepository(sessionFactoryProvider.getSessionFactory(), null);

			Metric metricToSave = TestEntityFactory.getTestMetric();

			doInSession(sessionFactoryProvider, () -> {
				repository.save(metricToSave);
			});

			doInSession(sessionFactoryProvider, () -> {
				Metric m = repository.get(Metric.class, metricToSave.getId());
				assertEquals(metricToSave.getName(), m.getName());
			});
		}
	}

	@Test
	public void shouldDeleteMetric() throws Exception {
		try (SessionFactoryProvider sessionFactoryProvider = new MemoryDatabaseSessionProvider()) {
			Repository repository = new CRUDRepository(sessionFactoryProvider.getSessionFactory(), null);

			Metric metric1 = TestEntityFactory.getTestMetric();
			Metric metric2 = TestEntityFactory.getTestMetric();

			doInSession(sessionFactoryProvider, () -> {
				repository.save(metric1);
				repository.save(metric2);
			});

			doInSession(sessionFactoryProvider, () -> {
				int countOfMetrics = repository.all(Metric.class).size();
				assertEquals(2, countOfMetrics);
			});

			doInSession(sessionFactoryProvider, () -> {
				repository.delete(metric1);
			});

			doInSession(sessionFactoryProvider, () -> {
				List<Metric> metricsList = repository.all(Metric.class);
				assertEquals(1, metricsList.size());
				assertEquals(metric2.getId(), metricsList.get(0).getId());
			});
		}
	}

	@Test
	public void shouldReturnAllMetrics() throws Exception {
		try (SessionFactoryProvider sessionFactoryProvider = new MemoryDatabaseSessionProvider()) {
			Repository repository = new CRUDRepository(sessionFactoryProvider.getSessionFactory(), null);

			Metric metric1 = TestEntityFactory.getTestMetric();
			Metric metric2 = TestEntityFactory.getTestMetric();
			Metric metric3 = TestEntityFactory.getTestMetric();

			doInSession(sessionFactoryProvider, () -> {
				repository.save(metric1);
				repository.save(metric2);
				repository.save(metric3);
			});

			doInSession(sessionFactoryProvider, () -> {
				List<Metric> metricsList = repository.all(Metric.class);
				assertEquals(3, metricsList.size());
				assertEquals(metric1.getId(), metricsList.get(0).getId());
				assertEquals(metric2.getId(), metricsList.get(1).getId());
				assertEquals(metric3.getId(), metricsList.get(2).getId());
			});
		}
	}

	@Test
	public void shouldReturnMetric_WithNameEqualTo_WhenExecutingQuery() throws Exception {
		try (SessionFactoryProvider sessionFactoryProvider = new MemoryDatabaseSessionProvider()) {
			TestDataGenerator.generateTestData(sessionFactoryProvider);

			doInSession(sessionFactoryProvider, () -> {
				// Arrange
				Repository repository = new CRUDRepository(sessionFactoryProvider.getSessionFactory(),
						getQueryConverter());
				QueryInitializer queryBuilder = new QueryBuilder();
				List<Metric> queryResult;

				Query<Metric> query = queryBuilder.queryFor(Metric.class).that("name", Is.equalTo("Metric 1")).build();

				// Act
				queryResult = repository.query(query);

				// Assert
				assertEquals(1, queryResult.size());
				assertEquals("Metric 1", queryResult.get(0).getName());
			});

		}
	}

	@Test
	public void shouldReturnMetrics_WithIds_GreaterThan1_AndLessThan4_WhenExecutingQuery() throws Exception {
		try (SessionFactoryProvider sessionFactoryProvider = new MemoryDatabaseSessionProvider()) {
			TestDataGenerator.generateTestData(sessionFactoryProvider);

			doInSession(sessionFactoryProvider, () -> {
				// Arrange
				Repository repository = new CRUDRepository(sessionFactoryProvider.getSessionFactory(),
						getQueryConverter());
				QueryInitializer queryBuilder = new QueryBuilder();
				List<Metric> queryResult;

				Query<Metric> query = queryBuilder.queryFor(Metric.class).that("id", Is.greaterThan(new Long(1))).and()
						.that("id", Is.lessThan(new Long(4))).build();

				// Act
				queryResult = repository.query(query);

				// Assert
				assertEquals(2, queryResult.size());
				assertEquals(new Long(2), queryResult.get(0).getId());
				assertEquals(new Long(3), queryResult.get(1).getId());
			});

		}
	}

	@Test
	public void shouldReturnResources_WithNameLikeRes_WhenExecutingQuery() throws Exception {
		try (SessionFactoryProvider sessionFactoryProvider = new MemoryDatabaseSessionProvider()) {
			TestDataGenerator.generateTestData(sessionFactoryProvider);

			doInSession(sessionFactoryProvider, () -> {
				// Arrange
				Repository repository = new CRUDRepository(sessionFactoryProvider.getSessionFactory(),
						getQueryConverter());
				QueryInitializer queryBuilder = new QueryBuilder();
				List<Resource> queryResult;

				Query<Resource> query = queryBuilder.queryFor(Resource.class).that("name", Is.like("Res%")).build();

				// Act
				queryResult = repository.query(query);

				// Assert
				assertEquals(3, queryResult.size());
			});

		}
	}

	@Test
	public void shouldReturnmeasurements_WithLimit25_WhenExecutingQuery() throws Exception {
		try (SessionFactoryProvider sessionFactoryProvider = new MemoryDatabaseSessionProvider()) {
			TestDataGenerator.generateTestData(sessionFactoryProvider);

			doInSession(sessionFactoryProvider, () -> {
				// Arrange
				Repository repository = new CRUDRepository(sessionFactoryProvider.getSessionFactory(),
						getQueryConverter());
				QueryInitializer queryBuilder = new QueryBuilder();
				List<Measurement> queryResult;

				Query<Measurement> query = queryBuilder.queryFor(Measurement.class).withMaxResults(25).build();

				// Act
				queryResult = repository.query(query);

				// Assert
				assertEquals(25, queryResult.size());
			});

		}
	}

	@Test
	public void shouldReturnMetrics_WithNameLike_AndSortedAscending_WhenExecutingQuery() throws Exception {
		try (SessionFactoryProvider sessionFactoryProvider = new MemoryDatabaseSessionProvider()) {
			TestDataGenerator.generateTestData(sessionFactoryProvider);

			doInSession(sessionFactoryProvider, () -> {
				// Arrange
				Repository repository = new CRUDRepository(sessionFactoryProvider.getSessionFactory(),
						getQueryConverter());
				QueryInitializer queryBuilder = new QueryBuilder();
				List<Metric> queryResult;

				Query<Metric> query = queryBuilder.queryFor(Metric.class).that("name", Is.like("%TO_SORT%")).and()
						.orderBy("name").asc().build();
				// Act
				queryResult = repository.query(query);

				// Assert
				assertEquals(4, queryResult.size());
				assertEquals("ascending TO_SORT", queryResult.get(0).getName());
				assertEquals("middle TO_SORT", queryResult.get(1).getName());
				assertEquals("other TO_SORT", queryResult.get(2).getName());
				assertEquals("xaml TO_SORT", queryResult.get(3).getName());
			});

		}
	}

	@Test
	public void shouldReturnMetrics_WithNameLike_AndSortedDescending_WhenExecutingQuery() throws Exception {
		try (SessionFactoryProvider sessionFactoryProvider = new MemoryDatabaseSessionProvider()) {
			TestDataGenerator.generateTestData(sessionFactoryProvider);

			doInSession(sessionFactoryProvider, () -> {
				// Arrange
				Repository repository = new CRUDRepository(sessionFactoryProvider.getSessionFactory(),
						getQueryConverter());
				QueryInitializer queryBuilder = new QueryBuilder();
				List<Metric> queryResult;

				Query<Metric> query = queryBuilder.queryFor(Metric.class).that("name", Is.like("%TO_SORT%")).and()
						.orderBy("name").desc().build();
				// Act
				queryResult = repository.query(query);

				// Assert
				assertEquals(4, queryResult.size());
				assertEquals("xaml TO_SORT", queryResult.get(0).getName());
				assertEquals("other TO_SORT", queryResult.get(1).getName());
				assertEquals("middle TO_SORT", queryResult.get(2).getName());
				assertEquals("ascending TO_SORT", queryResult.get(3).getName());
			});

		}
	}

	@Test
	public void shouldReturnSensor_WithMetricNameEqualTo_AndResourceNameEqualTo_WhenExecutingQuery() throws Exception {
		try (SessionFactoryProvider sessionFactoryProvider = new MemoryDatabaseSessionProvider()) {
			TestDataGenerator.generateTestData(sessionFactoryProvider);

			doInSession(sessionFactoryProvider, () -> {
				// Arrange
				Repository repository = new CRUDRepository(sessionFactoryProvider.getSessionFactory(),
						getQueryConverter());
				QueryInitializer queryBuilder = new QueryBuilder();
				List<Sensor> queryResult;

				Query<Sensor> query = queryBuilder.queryFor(Sensor.class).with("metric")
						.that("name", Is.equalTo("Metric 1")).and().with("resource")
						.that("name", Is.equalTo("Resource 1")).build();

				// Act
				queryResult = repository.query(query);

				// Assert
				assertEquals(1, queryResult.size());
				assertEquals("Metric 1", queryResult.get(0).getMetric().getName());
				assertEquals("Resource 1", queryResult.get(0).getResource().getName());
			});

		}
	}

	@Test
	public void shouldReturn100_WhenCountingMeasurements() throws Exception {
		try (SessionFactoryProvider sessionFactoryProvider = new MemoryDatabaseSessionProvider()) {
			TestDataGenerator.generateTestData(sessionFactoryProvider);

			doInSession(sessionFactoryProvider, () -> {
				// Arrange
				Repository repository = new CRUDRepository(sessionFactoryProvider.getSessionFactory(), null);

				// Act
				Long count = (Long) repository.count(Measurement.class);

				// Assert
				assertEquals(new Long(100), count);
			});
		}
	}

	@Test
	public void shouldReturn8_WhenCountingMetrics() throws Exception {
		try (SessionFactoryProvider sessionFactoryProvider = new MemoryDatabaseSessionProvider()) {
			TestDataGenerator.generateTestData(sessionFactoryProvider);

			doInSession(sessionFactoryProvider, () -> {
				// Arrange
				Repository repository = new CRUDRepository(sessionFactoryProvider.getSessionFactory(), null);

				// Act
				Long count = (Long) repository.count(Metric.class);

				// Assert
				assertEquals(new Long(8), count);
			});
		}
	}

	private void doInSession(SessionFactoryProvider sessionFactoryProvider, Runnable block) throws Exception {
		Session session = sessionFactoryProvider.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			block.run();
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
	}

	private QueryConverter<Criteria> getQueryConverter() {
		return new HibernateQueryConverter();
	}
}
