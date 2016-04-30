package pz.monitor.data.infrastructure;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class AbstractSessionProvider implements SessionProvider {
	private final SessionFactory sessionFactory;
	
	protected AbstractSessionProvider() {
		Configuration configuration = new Configuration();
		configure(configuration);
		sessionFactory = configuration.buildSessionFactory();
	}
	
	protected abstract void configure(Configuration configuration);

	public void close() throws Exception {
		sessionFactory.close();
	}

	public Session openSession() {
		return sessionFactory.openSession();
	}

}
