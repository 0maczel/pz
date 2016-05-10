package pz.monitor.application;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pz.monitor.db.Repository;
import pz.monitor.db.entity.Measurement;
import pz.monitor.db.entity.Metric;
import pz.monitor.db.entity.Resource;
import pz.monitor.db.entity.Sensor;

@Component
@Transactional
public class QaDataGenerator implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	Repository repository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(QaDataGenerator.class);
	
	void generateQaData() {
		LOGGER.info("Generating test data...");
		
		Random random = new Random();
		
		// Metrics
		Metric cpuUtilization = new Metric();
		cpuUtilization.setName("CPU Utilization");
		
		Metric ramUtilization = new Metric();
		ramUtilization.setName("RAM Utilization");
		
		// Resources
		Resource pc1 = new Resource();
		pc1.setName("PC 1");
		
		Resource pc2 = new Resource();
		pc2.setName("PC 2");
		
		Resource zeus = new Resource();
		zeus.setName("Zeus");
		
		// Sensors
		Sensor cpuSensorAtPc1 = new Sensor();
		cpuSensorAtPc1.setMetric(cpuUtilization);
		cpuSensorAtPc1.setResource(pc1);
		
		Sensor ramSensorAtPc1 = new Sensor();
		ramSensorAtPc1.setMetric(ramUtilization);
		ramSensorAtPc1.setResource(pc1);
		
		// Measurements for cpuSensorAtPc1
		LocalDateTime dateTime = LocalDateTime.now();
		for(int i=0; i<1000; i++) {
			Measurement measurement = new Measurement();
			measurement.setSensor(cpuSensorAtPc1);
			measurement.setMetric(cpuSensorAtPc1.getMetric());
			measurement.setResource(cpuSensorAtPc1.getResource());
			measurement.setValue(random.nextDouble());
			
			repository.save(measurement);
			setPropertyByName(measurement, "creationTimestamp", Timestamp.valueOf(dateTime.minusMinutes(i)));
			repository.save(measurement);
		}
		
		LOGGER.info("Test data generated successfully");
	}
	
	private boolean setPropertyByName(Object object, String fieldName, Object fieldValue) {
	    Class<?> clazz = object.getClass();
	    while (clazz != null) {
	        try {
	            Field field = clazz.getDeclaredField(fieldName);
	            field.setAccessible(true);
	            field.set(object, fieldValue);
	            return true;
	        } catch (NoSuchFieldException e) {
	            clazz = clazz.getSuperclass();
	        } catch (Exception e) {
	            throw new IllegalStateException(e);
	        }
	    }
	    return false;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		generateQaData();
	}
}
