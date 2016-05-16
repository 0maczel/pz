package pz.monitor.db.helpers;

import java.util.Random;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pz.monitor.db.CRUDRepository;
import pz.monitor.db.Repository;
import pz.monitor.db.entity.Measurement;
import pz.monitor.db.entity.Metric;
import pz.monitor.db.entity.Resource;
import pz.monitor.db.entity.Sensor;

public class TestDataGenerator {
	public static void generateTestData(SessionFactoryProvider sessionFactoryProvider) throws Exception {
		Repository repository = new CRUDRepository(sessionFactoryProvider.getSessionFactory(), null);

		// Test Metrics
		Metric metric1 = TestEntityFactory.getTestMetric();
		metric1.setName("Metric 1");

		Metric metric2 = TestEntityFactory.getTestMetric();
		metric2.setName("Metric 2");

		Metric metric3 = TestEntityFactory.getTestMetric();
		metric3.setName("Metric 3");

		Metric metric4 = TestEntityFactory.getTestMetric();
		metric4.setName("Metric 4");

		Metric metricToSortMiddle1 = TestEntityFactory.getTestMetric();
		metricToSortMiddle1.setName("middle TO_SORT");

		Metric metricToSortFirst = TestEntityFactory.getTestMetric();
		metricToSortFirst.setName("ascending TO_SORT");

		Metric metricToSortLast = TestEntityFactory.getTestMetric();
		metricToSortLast.setName("xaml TO_SORT");

		Metric metricToSortMiddle2 = TestEntityFactory.getTestMetric();
		metricToSortMiddle2.setName("other TO_SORT");

		// Test Resources
		Resource resource1 = TestEntityFactory.getTestResource();
		resource1.setName("Resource 1");

		Resource resource2 = TestEntityFactory.getTestResource();
		resource2.setName("Resource 2");

		Resource resource3 = TestEntityFactory.getTestResource();
		resource3.setName("Resource 3");

		Resource resource4 = TestEntityFactory.getTestResource();
		resource4.setName("Zeus");

		// Test Sensors
		Sensor sensor1 = TestEntityFactory.getTestSensor();
		sensor1.setMetric(metric1);
		sensor1.setResource(resource1);

		Sensor sensor2 = TestEntityFactory.getTestSensor();
		sensor2.setMetric(metric2);
		sensor2.setResource(resource2);

		Sensor sensor3 = TestEntityFactory.getTestSensor();
		sensor3.setMetric(metric3);
		sensor3.setResource(resource3);

		doInSession(sessionFactoryProvider, () -> {
			repository.save(metric1);
			repository.save(metric2);
			repository.save(metric3);
			repository.save(metric4);

			repository.save(metricToSortMiddle1);
			repository.save(metricToSortFirst);
			repository.save(metricToSortLast);
			repository.save(metricToSortMiddle2);

			repository.save(resource1);
			repository.save(resource2);
			repository.save(resource3);
			repository.save(resource4);

			repository.save(sensor1);
			repository.save(sensor2);
			repository.save(sensor3);

			for (int i = 0; i < 100; i++) {
				Measurement m = getRandomMeasurement(metric1, resource1, sensor1);
				repository.save(m);
			}
		});
	}

	private static Measurement getRandomMeasurement(Metric metric, Resource resource, Sensor sensor) {
		Measurement measurement = new Measurement();
		measurement.setMetric(metric);
		measurement.setResource(resource);
		measurement.setSensor(sensor);

		Random r = new Random();
		measurement.setValue(r.nextDouble());

		return measurement;
	}

	private static void doInSession(SessionFactoryProvider sessionFactoryProvider, Runnable block) throws Exception {
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
}
