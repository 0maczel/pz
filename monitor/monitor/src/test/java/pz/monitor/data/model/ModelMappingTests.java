package pz.monitor.data.model;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.hibernate.Session;
import org.hibernate.Transaction;

import junit.framework.TestCase;
import pz.monitor.data.infrastructure.MemoryDatabaseSessionProvider;
import pz.monitor.data.infrastructure.SessionProvider;
import pz.monitor.data.model.ComplexMeasurement;
import pz.monitor.data.model.Measurement;
import pz.monitor.data.model.Metric;
import pz.monitor.data.model.Resource;
import pz.monitor.data.model.Sensor;

public class ModelMappingTests extends TestCase {

	public void test_ShouldReturnEntitiVersion5_WhenUpdatedFiveTimes() throws Exception {
		try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider()) {
			Metric metric = getTestMetric();

			doInSession(sessionProvider, session -> {
				session.save(metric);
			});
			for (int i = 0; i < 5; i++) {
				doInSession(sessionProvider, session -> {
					session.update(metric);
				});
			}

			assertEquals(5, metric.getEntityVersion());
		}
	}
	
	public void test_ShouldCreationTimestampAndUpdateTimestampBeAutoInitialized() throws Exception {
		try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider()) {
			Metric metric = getTestMetric();
			Timestamp preCreateTimeStamp = Timestamp.valueOf(LocalDateTime.now());

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
	
	public void test_ShouldUpdateTimestampBeAutoUpdated() throws Exception {
		try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider()) {
			Metric metric = getTestMetric();
			List<Timestamp> timestampsList = new ArrayList<Timestamp>();
			timestampsList.add(Timestamp.valueOf(LocalDateTime.now()));

			doInSession(sessionProvider, session -> {
				session.save(metric);
				timestampsList.add(metric.getUpdateTimestamp());
			});
			
			for (int i = 0; i < 5; i++) {
				doInSession(sessionProvider, session -> {
					session.update(metric);
				});
				doInSession(sessionProvider, session -> {
					Metric m = session.load(Metric.class, metric.getId());
					timestampsList.add(m.getUpdateTimestamp());
				});
			}

			for(int i = 1; i < timestampsList.size(); i++) {
				assertTrue(timestampsList.get(i).after(timestampsList.get(i-1)));				
			}
		}
	}

	public void test_ShouldSaveAndLoadTheMetricClass() throws Exception {
		try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider()) {
			Metric metricToSave = getTestMetric();

			doInSession(sessionProvider, session -> {
				session.save(metricToSave);
			});

			assertEquals(1, metricToSave.getId());

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
	
	public void test_ShouldSaveAndLoadTheResourceClass() throws Exception {
		try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider()) {
			Resource resourceToSave = getTestResource();

			doInSession(sessionProvider, session -> {
				session.save(resourceToSave);
			});

			assertEquals(1, resourceToSave.getId());

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
	
	public void test_ShouldSaveAndLoadTheSensorClass() throws Exception {
		try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider()) {
			Sensor sensorToSave = getTestSensor();
			
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
	
	public void test_ShouldSaveAndLoadTheComplexMeasurementClass() throws Exception {
		try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider()) {
			ComplexMeasurement complexMeasurementToSave = getTestComplexMeasurement();
			
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
	
	public void test_ShouldSaveAndLoadTheMeasurementClass() throws Exception {
		try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider()) {
			Measurement neasurementToSave = getTestMeasurement();
			
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
	
	private Metric getTestMetric() {
		Metric metric = new Metric();
		metric.setName("Test metric");
		return metric;
	}
	
	private Resource getTestResource() {
		Resource resource = new Resource();
		resource.setName("Test resource");
		return resource;
	}
	
	private Sensor getTestSensor() {
		Sensor sensor = new Sensor();
		sensor.setExternalSystemId(UUID.randomUUID().toString());
		sensor.setMetric(getTestMetric());
		sensor.setResource(getTestResource());
		return sensor;
	}
	
	private ComplexMeasurement getTestComplexMeasurement() {
		ComplexMeasurement complesMeasurement = new ComplexMeasurement();
		complesMeasurement.setMetric(getTestMetric());
		complesMeasurement.setResource(getTestResource());
		complesMeasurement.setWindowLength(Duration.ofMinutes(10));
		complesMeasurement.setWindowInterval(Duration.ofMinutes(1));
		return complesMeasurement;
	}
	
	private Measurement getTestMeasurement() {
		Measurement measurement = new Measurement();
		measurement.setMetric(getTestMetric());
		measurement.setResource(getTestResource());
		measurement.setSensor(getTestSensor());
		measurement.setValue(123.456);
		return measurement;
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
