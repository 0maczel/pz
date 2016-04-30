package pz.monitor.data.persistence;

import java.util.List;
import java.util.function.Consumer;

import org.hibernate.Session;
import org.hibernate.Transaction;

import junit.framework.TestCase;
import pz.monitor.data.TestEntityFactory;
import pz.monitor.data.infrastructure.MemoryDatabaseSessionProvider;
import pz.monitor.data.infrastructure.SessionProvider;
import pz.monitor.data.model.Metric;

public class RepositoryTests extends TestCase {
	public void test_ShouldSaveNewMetric() throws Exception {
		try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider()) {
			doInSession(sessionProvider, session -> {
				Repository repository = new CRUDRepository(session);
				int countOfMetrics = repository.all(Metric.class).size();
				assertEquals(0, countOfMetrics);
			});
			
			doInSession(sessionProvider, session -> {
				Repository repository = new CRUDRepository(session);
				repository.save(TestEntityFactory.getTestMetric());
			});
			
			doInSession(sessionProvider, session -> {
				Repository repository = new CRUDRepository(session);
				int countOfMetrics = repository.all(Metric.class).size();
				assertEquals(1, countOfMetrics);
			});
		}
	}
	
	public void test_ShouldUpdateMetric() throws Exception {
		try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider()) {
			Metric metricToSave = TestEntityFactory.getTestMetric();
			String newName = "New name of metric";
			
			doInSession(sessionProvider, session -> {
				Repository repository = new CRUDRepository(session);
				int countOfMetrics = repository.all(Metric.class).size();
				assertEquals(0, countOfMetrics);
			});
			
			doInSession(sessionProvider, session -> {
				Repository repository = new CRUDRepository(session);
				repository.save(metricToSave);
			});
			
			doInSession(sessionProvider, session -> {
				Repository repository = new CRUDRepository(session);
				metricToSave.setName(newName);
				repository.save(metricToSave);
			});
			
			doInSession(sessionProvider, session -> {
				Repository repository = new CRUDRepository(session);
				int countOfMetrics = repository.all(Metric.class).size();
				assertEquals(1, countOfMetrics);
				
				Metric m = repository.get(Metric.class, metricToSave.getId());
				assertEquals(newName, m.getName());
				assertEquals(1, m.getEntityVersion());
			});
		}
	}
	
	public void test_ShouldGetMetricById() throws Exception {
		try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider()) {
			Metric metricToSave = TestEntityFactory.getTestMetric();
			
			doInSession(sessionProvider, session -> {
				Repository repository = new CRUDRepository(session);
				repository.save(metricToSave);
			});
			
			doInSession(sessionProvider, session -> {
				Repository repository = new CRUDRepository(session);
				Metric m = repository.get(Metric.class, metricToSave.getId());
				
				assertEquals(metricToSave.getName(), m.getName());
			});
		}
	}
	
	public void test_ShouldDeleteMetric() throws Exception {
		try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider()) {
			Metric metric1 = TestEntityFactory.getTestMetric();
			Metric metric2 = TestEntityFactory.getTestMetric();
			
			doInSession(sessionProvider, session -> {
				Repository repository = new CRUDRepository(session);
				repository.save(metric1);
				repository.save(metric2);
			});
			
			doInSession(sessionProvider, session -> {
				Repository repository = new CRUDRepository(session);
				int countOfMetrics = repository.all(Metric.class).size();
				assertEquals(2, countOfMetrics);
			});
			
			doInSession(sessionProvider, session -> {
				Repository repository = new CRUDRepository(session);
				repository.delete(metric1);
			});
			
			doInSession(sessionProvider, session -> {
				Repository repository = new CRUDRepository(session);
				List<Metric> metricsList = repository.all(Metric.class);
				assertEquals(1, metricsList.size());
				assertEquals(metric2.getId(), metricsList.get(0).getId());
			});
		}
	}
	
	public void test_ShouldReturnAllMetrics() throws Exception {
		try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider()) {
			Metric metric1 = TestEntityFactory.getTestMetric();
			Metric metric2 = TestEntityFactory.getTestMetric();
			Metric metric3 = TestEntityFactory.getTestMetric();
			
			doInSession(sessionProvider, session -> {
				Repository repository = new CRUDRepository(session);
				repository.save(metric1);
				repository.save(metric2);
				repository.save(metric3);
			});
			
			doInSession(sessionProvider, session -> {
				Repository repository = new CRUDRepository(session);
				List<Metric> metricsList = repository.all(Metric.class);
				assertEquals(3, metricsList.size());
				assertEquals(metric1.getId(), metricsList.get(0).getId());
				assertEquals(metric2.getId(), metricsList.get(1).getId());
				assertEquals(metric3.getId(), metricsList.get(2).getId());
			});
		}
	}
	
	private void doInSession(SessionProvider sessionProvider, Consumer<Session> block) throws Exception {
		Session session = sessionProvider.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			block.accept(session);
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
}
