package pz.monitor.data.infrastructure;

import org.hibernate.cfg.Configuration;

import pz.monitor.db.entity.ComplexMeasurement;
import pz.monitor.db.entity.Entity;
import pz.monitor.db.entity.Measurement;
import pz.monitor.db.entity.Metric;
import pz.monitor.db.entity.Resource;
import pz.monitor.db.entity.Sensor;

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
