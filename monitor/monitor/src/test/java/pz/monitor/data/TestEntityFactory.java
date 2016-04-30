package pz.monitor.data;

import java.time.Duration;
import java.util.UUID;

import pz.monitor.data.model.ComplexMeasurement;
import pz.monitor.data.model.Measurement;
import pz.monitor.data.model.Metric;
import pz.monitor.data.model.Resource;
import pz.monitor.data.model.Sensor;

public class TestEntityFactory {
	public static Metric getTestMetric() {
		Metric metric = new Metric();
		metric.setName("Test metric");
		return metric;
	}
	
	public static Resource getTestResource() {
		Resource resource = new Resource();
		resource.setName("Test resource");
		return resource;
	}
	
	public static Sensor getTestSensor() {
		Sensor sensor = new Sensor();
		sensor.setExternalSystemId(UUID.randomUUID().toString());
		sensor.setMetric(getTestMetric());
		sensor.setResource(getTestResource());
		return sensor;
	}
	
	public static ComplexMeasurement getTestComplexMeasurement() {
		ComplexMeasurement complesMeasurement = new ComplexMeasurement();
		complesMeasurement.setMetric(getTestMetric());
		complesMeasurement.setResource(getTestResource());
		complesMeasurement.setWindowLength(Duration.ofMinutes(10));
		complesMeasurement.setWindowInterval(Duration.ofMinutes(1));
		return complesMeasurement;
	}
	
	public static Measurement getTestMeasurement() {
		Measurement measurement = new Measurement();
		measurement.setMetric(getTestMetric());
		measurement.setResource(getTestResource());
		measurement.setSensor(getTestSensor());
		measurement.setValue(123.456);
		return measurement;
	}
}
