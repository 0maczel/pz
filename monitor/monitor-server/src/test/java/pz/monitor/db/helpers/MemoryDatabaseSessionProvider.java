package pz.monitor.db.helpers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import pz.monitor.db.entity.ComplexMeasurement;
import pz.monitor.db.entity.Entity;
import pz.monitor.db.entity.Measurement;
import pz.monitor.db.entity.Metric;
import pz.monitor.db.entity.Resource;
import pz.monitor.db.entity.Sensor;

public final class MemoryDatabaseSessionProvider implements SessionProvider, SessionFactoryProvider {
	private final SessionFactory sessionFactory;

	public MemoryDatabaseSessionProvider() {
		Configuration configuration = new Configuration();
		configure(configuration);
		sessionFactory = configuration.buildSessionFactory();
	}

	private void configure(Configuration configuration) {
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect")
				.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbc.JDBCDriver")
				.setProperty("hibernate.connection.username", "SA").setProperty("hibernate.connection.password", "")
				.setProperty("hibernate.hbm2ddl.auto", "create-drop")
				.setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:database/monitor;shutdown=true")
				.setProperty("hibernate.current_session_context_class", "thread");

		configuration.addAnnotatedClass(Entity.class).addAnnotatedClass(Metric.class).addAnnotatedClass(Resource.class)
				.addAnnotatedClass(Sensor.class).addAnnotatedClass(ComplexMeasurement.class)
				.addAnnotatedClass(Measurement.class);
	}

	public void close() throws Exception {
		sessionFactory.close();
	}

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public Session openSession() {
		return sessionFactory.openSession();
	}
}
