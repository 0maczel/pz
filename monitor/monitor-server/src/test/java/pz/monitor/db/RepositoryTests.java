package pz.monitor.db;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import pz.monitor.db.entity.Metric;
import pz.monitor.db.helpers.MemoryDatabaseSessionProvider;
import pz.monitor.db.helpers.SessionFactoryProvider;
import pz.monitor.db.helpers.TestEntityFactory;

public class RepositoryTests
{
    @Test
    public void shouldSaveNewMetric() throws Exception
    {
        try (SessionFactoryProvider sessionFactoryProvider = new MemoryDatabaseSessionProvider())
        {
        	Repository repository = new CRUDRepository(sessionFactoryProvider.getSessionFactory());
        	
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
    public void shouldUpdateMetric() throws Exception
    {
        try (SessionFactoryProvider sessionFactoryProvider = new MemoryDatabaseSessionProvider())
        {
        	Repository repository = new CRUDRepository(sessionFactoryProvider.getSessionFactory());
        	
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
    public void shouldGetMetricById() throws Exception
    {
        try (SessionFactoryProvider sessionFactoryProvider = new MemoryDatabaseSessionProvider())
        {
        	Repository repository = new CRUDRepository(sessionFactoryProvider.getSessionFactory());
        	
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
    public void shouldDeleteMetric() throws Exception
    {
        try (SessionFactoryProvider sessionFactoryProvider = new MemoryDatabaseSessionProvider())
        {
        	Repository repository = new CRUDRepository(sessionFactoryProvider.getSessionFactory());
        	
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
    public void shouldReturnAllMetrics() throws Exception
    {
        try (SessionFactoryProvider sessionFactoryProvider = new MemoryDatabaseSessionProvider())
        {
        	Repository repository = new CRUDRepository(sessionFactoryProvider.getSessionFactory());
        	
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

    private void doInSession(SessionFactoryProvider sessionFactoryProvider, Runnable block) throws Exception
    {
        Session session = sessionFactoryProvider.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            block.run();
            tx.commit();
        }
        catch (Exception e)
        {
            if (tx != null)
                tx.rollback();
            throw e;
        }
    }
}
