package pz.monitor.data.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.hibernate.Session;
import org.hibernate.Transaction;

import junit.framework.TestCase;
import pz.monitor.data.TestEntityFactory;
import pz.monitor.data.infrastructure.MemoryDatabaseSessionProvider;
import pz.monitor.data.infrastructure.SessionProvider;
import pz.monitor.db.entity.ComplexMeasurement;
import pz.monitor.db.entity.Measurement;
import pz.monitor.db.entity.Metric;
import pz.monitor.db.entity.Resource;
import pz.monitor.db.entity.Sensor;

public class ModelMappingTests extends TestCase
{

    public void test_ShouldReturnEntitiVersion5_WhenUpdatedFiveTimes() throws Exception
    {
        try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider())
        {
            Metric metric = TestEntityFactory.getTestMetric();

            doInSession(sessionProvider, session -> {
                session.save(metric);
            });
            for (int i = 0; i < 5; i++)
            {
                doInSession(sessionProvider, session -> {
                    session.update(metric);
                });
            }

            assertEquals(5, metric.getEntityVersion());
        }
    }

    public void test_ShouldCreationTimestampAndUpdateTimestampBeAutoInitialized() throws Exception
    {
        try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider())
        {
            Metric metric = TestEntityFactory.getTestMetric();
            Timestamp preCreateTimeStamp = Timestamp.valueOf(LocalDateTime.now());
            Thread.sleep(10);

            doInSession(sessionProvider, session -> {
                session.save(metric);
            });

            doInSession(sessionProvider, session -> {
                Metric m = session.load(Metric.class, metric.getId());

                assertTrue(m.getCreationTimestamp().after(preCreateTimeStamp));
                assertTrue(m.getUpdateTimestamp().after(preCreateTimeStamp));
            });
        }
    }

    public void test_ShouldUpdateTimestampBeAutoUpdated() throws Exception
    {
        try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider())
        {
            Metric metric = TestEntityFactory.getTestMetric();
            List<Timestamp> timestampsList = new ArrayList<Timestamp>();
            timestampsList.add(Timestamp.valueOf(LocalDateTime.now()));
            Thread.sleep(10);

            doInSession(sessionProvider, session -> {
                session.save(metric);
                timestampsList.add(metric.getUpdateTimestamp());
            });

            for (int i = 0; i < 5; i++)
            {
                Thread.sleep(10);
                doInSession(sessionProvider, session -> {
                    session.update(metric);
                });
                doInSession(sessionProvider, session -> {
                    Metric m = session.load(Metric.class, metric.getId());
                    timestampsList.add(m.getUpdateTimestamp());
                });
            }

            for (int i = 1; i < timestampsList.size(); i++)
            {
                assertTrue(timestampsList.get(i).after(timestampsList.get(i - 1)));
            }
        }
    }

    public void test_ShouldSaveAndLoadTheMetricClass() throws Exception
    {
        try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider())
        {
            Metric metricToSave = TestEntityFactory.getTestMetric();

            doInSession(sessionProvider, session -> {
                session.save(metricToSave);
            });

            assertEquals(Long.valueOf(1L), metricToSave.getId());

            doInSession(sessionProvider, session -> {
                Metric m = session.load(Metric.class, metricToSave.getId());

                assertEquals(metricToSave.getId(), m.getId());
                assertEquals(metricToSave.getEntityVersion(), m.getEntityVersion());
                assertEquals(metricToSave.getCreationTimestamp(), m.getCreationTimestamp());
                assertEquals(metricToSave.getUpdateTimestamp(), m.getUpdateTimestamp());
                assertEquals(metricToSave.getName(), m.getName());
            });
        }
    }

    public void test_ShouldSaveAndLoadTheResourceClass() throws Exception
    {
        try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider())
        {
            Resource resourceToSave = TestEntityFactory.getTestResource();

            doInSession(sessionProvider, session -> {
                session.save(resourceToSave);
            });

            assertEquals(Long.valueOf(1L), resourceToSave.getId());

            doInSession(sessionProvider, session -> {
                Resource r = session.load(Resource.class, resourceToSave.getId());

                assertEquals(resourceToSave.getId(), r.getId());
                assertEquals(resourceToSave.getEntityVersion(), r.getEntityVersion());
                assertEquals(resourceToSave.getCreationTimestamp(), r.getCreationTimestamp());
                assertEquals(resourceToSave.getUpdateTimestamp(), r.getUpdateTimestamp());
                assertEquals(resourceToSave.getName(), r.getName());
            });
        }
    }

    public void test_ShouldSaveAndLoadTheSensorClass() throws Exception
    {
        try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider())
        {
            Sensor sensorToSave = TestEntityFactory.getTestSensor();

            doInSession(sessionProvider, session -> {
                session.save(sensorToSave);
            });

            doInSession(sessionProvider, session -> {
                Sensor s = session.load(Sensor.class, sensorToSave.getId());

                assertEquals(sensorToSave.getId(), s.getId());
                assertEquals(sensorToSave.getEntityVersion(), s.getEntityVersion());
                assertEquals(sensorToSave.getCreationTimestamp(), s.getCreationTimestamp());
                assertEquals(sensorToSave.getUpdateTimestamp(), s.getUpdateTimestamp());
                assertEquals(sensorToSave.getExternalSystemId(), s.getExternalSystemId());
                assertEquals(sensorToSave.getMetric().getId(), s.getMetric().getId());
                assertEquals(sensorToSave.getMetric().getName(), s.getMetric().getName());
                assertEquals(sensorToSave.getResource().getId(), s.getResource().getId());
                assertEquals(sensorToSave.getResource().getName(), s.getResource().getName());
            });
        }
    }

    public void test_ShouldSaveAndLoadTheComplexMeasurementClass() throws Exception
    {
        try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider())
        {
            ComplexMeasurement complexMeasurementToSave = TestEntityFactory.getTestComplexMeasurement();

            doInSession(sessionProvider, session -> {
                session.save(complexMeasurementToSave);
            });

            doInSession(sessionProvider, session -> {
                ComplexMeasurement cm = session.load(ComplexMeasurement.class, complexMeasurementToSave.getId());

                assertEquals(complexMeasurementToSave.getId(), cm.getId());
                assertEquals(complexMeasurementToSave.getEntityVersion(), cm.getEntityVersion());
                assertEquals(complexMeasurementToSave.getCreationTimestamp(), cm.getCreationTimestamp());
                assertEquals(complexMeasurementToSave.getUpdateTimestamp(), cm.getUpdateTimestamp());
                assertEquals(complexMeasurementToSave.getWindowLength(), cm.getWindowLength());
                assertEquals(complexMeasurementToSave.getWindowInterval(), cm.getWindowInterval());
                assertEquals(complexMeasurementToSave.getMetric().getId(), cm.getMetric().getId());
                assertEquals(complexMeasurementToSave.getMetric().getName(), cm.getMetric().getName());
                assertEquals(complexMeasurementToSave.getResource().getId(), cm.getResource().getId());
                assertEquals(complexMeasurementToSave.getResource().getName(), cm.getResource().getName());
            });
        }
    }

    public void test_ShouldSaveAndLoadTheMeasurementClass() throws Exception
    {
        try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider())
        {
            Measurement neasurementToSave = TestEntityFactory.getTestMeasurement();

            doInSession(sessionProvider, session -> {
                session.save(neasurementToSave);
            });

            doInSession(sessionProvider, session -> {
                Measurement m = session.load(Measurement.class, neasurementToSave.getId());

                assertEquals(neasurementToSave.getId(), m.getId());
                assertEquals(neasurementToSave.getEntityVersion(), m.getEntityVersion());
                assertEquals(neasurementToSave.getCreationTimestamp(), m.getCreationTimestamp());
                assertEquals(neasurementToSave.getUpdateTimestamp(), m.getUpdateTimestamp());
                assertEquals(neasurementToSave.getValue(), m.getValue());
                assertEquals(neasurementToSave.getMetric().getId(), m.getMetric().getId());
                assertEquals(neasurementToSave.getMetric().getName(), m.getMetric().getName());
                assertEquals(neasurementToSave.getResource().getId(), m.getResource().getId());
                assertEquals(neasurementToSave.getResource().getName(), m.getResource().getName());
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
