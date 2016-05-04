package pz.monitor.data.persistence;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.function.Consumer;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import pz.monitor.data.TestEntityFactory;
import pz.monitor.data.infrastructure.MemoryDatabaseSessionProvider;
import pz.monitor.data.infrastructure.SessionProvider;
import pz.monitor.db.Repository;
import pz.monitor.db.entity.Metric;

public class RepositoryTests
{
    @Test
    public void shouldSaveNewMetric() throws Exception
    {
        try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider())
        {
            doInSession(sessionProvider, session -> {
                Repository repository = new TestCRUDRepository(session);
                int countOfMetrics = repository.all(Metric.class).size();
                assertEquals(0, countOfMetrics);
            });

            doInSession(sessionProvider, session -> {
                Repository repository = new TestCRUDRepository(session);
                repository.save(TestEntityFactory.getTestMetric());
            });

            doInSession(sessionProvider, session -> {
                Repository repository = new TestCRUDRepository(session);
                int countOfMetrics = repository.all(Metric.class).size();
                assertEquals(1, countOfMetrics);
            });
        }
    }

    @Test
    public void shouldUpdateMetric() throws Exception
    {
        try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider())
        {
            Metric metricToSave = TestEntityFactory.getTestMetric();
            String newName = "New name of metric";

            doInSession(sessionProvider, session -> {
                Repository repository = new TestCRUDRepository(session);
                int countOfMetrics = repository.all(Metric.class).size();
                assertEquals(0, countOfMetrics);
            });

            doInSession(sessionProvider, session -> {
                Repository repository = new TestCRUDRepository(session);
                repository.save(metricToSave);
            });

            doInSession(sessionProvider, session -> {
                Repository repository = new TestCRUDRepository(session);
                metricToSave.setName(newName);
                repository.save(metricToSave);
            });

            doInSession(sessionProvider, session -> {
                Repository repository = new TestCRUDRepository(session);
                int countOfMetrics = repository.all(Metric.class).size();
                assertEquals(1, countOfMetrics);

                Metric m = repository.get(Metric.class, metricToSave.getId());
                assertEquals(newName, m.getName());
                assertEquals(1, m.getEntityVersion());
            });
        }
    }

    @Test
    public void shouldGetMetricById() throws Exception
    {
        try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider())
        {
            Metric metricToSave = TestEntityFactory.getTestMetric();

            doInSession(sessionProvider, session -> {
                Repository repository = new TestCRUDRepository(session);
                repository.save(metricToSave);
            });

            doInSession(sessionProvider, session -> {
                Repository repository = new TestCRUDRepository(session);
                Metric m = repository.get(Metric.class, metricToSave.getId());

                assertEquals(metricToSave.getName(), m.getName());
            });
        }
    }

    @Test
    public void shouldDeleteMetric() throws Exception
    {
        try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider())
        {
            Metric metric1 = TestEntityFactory.getTestMetric();
            Metric metric2 = TestEntityFactory.getTestMetric();

            doInSession(sessionProvider, session -> {
                Repository repository = new TestCRUDRepository(session);
                repository.save(metric1);
                repository.save(metric2);
            });

            doInSession(sessionProvider, session -> {
                Repository repository = new TestCRUDRepository(session);
                int countOfMetrics = repository.all(Metric.class).size();
                assertEquals(2, countOfMetrics);
            });

            doInSession(sessionProvider, session -> {
                Repository repository = new TestCRUDRepository(session);
                repository.delete(metric1);
            });

            doInSession(sessionProvider, session -> {
                Repository repository = new TestCRUDRepository(session);
                List<Metric> metricsList = repository.all(Metric.class);
                assertEquals(1, metricsList.size());
                assertEquals(metric2.getId(), metricsList.get(0).getId());
            });
        }
    }

    @Test
    public void shouldReturnAllMetrics() throws Exception
    {
        try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider())
        {
            Metric metric1 = TestEntityFactory.getTestMetric();
            Metric metric2 = TestEntityFactory.getTestMetric();
            Metric metric3 = TestEntityFactory.getTestMetric();

            doInSession(sessionProvider, session -> {
                Repository repository = new TestCRUDRepository(session);
                repository.save(metric1);
                repository.save(metric2);
                repository.save(metric3);
            });

            doInSession(sessionProvider, session -> {
                Repository repository = new TestCRUDRepository(session);
                List<Metric> metricsList = repository.all(Metric.class);
                assertEquals(3, metricsList.size());
                assertEquals(metric1.getId(), metricsList.get(0).getId());
                assertEquals(metric2.getId(), metricsList.get(1).getId());
                assertEquals(metric3.getId(), metricsList.get(2).getId());
            });
        }
    }

    private void doInSession(SessionProvider sessionProvider, Consumer<Session> block) throws Exception
    {
        Session session = sessionProvider.openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            block.accept(session);
            tx.commit();
        }
        catch (Exception e)
        {
            if (tx != null)
                tx.rollback();
            throw e;
        }
        finally
        {
            session.close();
        }
    }
}
