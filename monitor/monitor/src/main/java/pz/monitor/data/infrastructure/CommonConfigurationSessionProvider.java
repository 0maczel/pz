package pz.monitor.data.infrastructure;

import org.hibernate.cfg.Configuration;

import pz.monitor.data.model.ComplexMeasurement;
import pz.monitor.data.model.Entity;
import pz.monitor.data.model.Measurement;
import pz.monitor.data.model.Metric;
import pz.monitor.data.model.Resource;
import pz.monitor.data.model.Sensor;

public abstract class CommonConfigurationSessionProvider extends AbstractSessionProvider {
	@Override
	protected void configure(Configuration configuration) {
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect")
				.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbc.JDBCDriver")
				.setProperty("hibernate.connection.username", "SA")
				.setProperty("hibernate.connection.password", "")
				.setProperty("hibernate.hbm2ddl.auto", "update");

		configuration.addAnnotatedClass(Entity.class)
			.addAnnotatedClass(Metric.class)
			.addAnnotatedClass(Resource.class)
			.addAnnotatedClass(Sensor.class)
			.addAnnotatedClass(ComplexMeasurement.class)
			.addAnnotatedClass(Measurement.class);
	}
}
