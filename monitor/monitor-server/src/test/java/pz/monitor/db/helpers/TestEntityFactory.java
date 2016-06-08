package pz.monitor.db.helpers;

import java.time.Duration;
import java.util.UUID;

import pz.monitor.db.entity.ComplexMeasurement;
import pz.monitor.db.entity.Measurement;
import pz.monitor.db.entity.Metric;
import pz.monitor.db.entity.Resource;
import pz.monitor.db.entity.Sensor;

public class TestEntityFactory {
	public static Metric getTestMetric() {
		Metric metric = new Metric();
		metric.setName("Test_metric_" + UUID.randomUUID().toString());
		return metric;
	}

	public static Resource getTestResource() {
		Resource resource = new Resource();
		resource.setName("Test_resource_" + UUID.randomUUID().toString());
		return resource;
	}

	public static Sensor getTestSensor() {
		Sensor sensor = new Sensor();
		sensor.setMetric(getTestMetric());
		sensor.setResource(getTestResource());
		return sensor;
	}

	public static ComplexMeasurement getTestComplexMeasurement() {
		ComplexMeasurement complesMeasurement = new ComplexMeasurement();
//		complesMeasurement.setMetric(getTestMetric());
//		complesMeasurement.setResource(getTestResource());
//		complesMeasurement.setWindowLength(Duration.ofMinutes(10));
//		complesMeasurement.setWindowInterval(Duration.ofMinutes(1));
		return complesMeasurement;
	}

	public static Measurement getTestMeasurement() {
		Metric metric = getTestMetric();
		Resource resource = getTestResource();
		Sensor sensor = getTestSensor();
		sensor.setMetric(metric);
		sensor.setResource(resource);

		Measurement measurement = new Measurement();
		measurement.setMetric(metric);
		measurement.setResource(resource);
		measurement.setSensor(sensor);
		measurement.setValue(123.456);
		return measurement;
	}
}
