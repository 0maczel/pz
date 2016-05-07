package pz.monitor.db.helpers;

import org.hibernate.SessionFactory;

public interface SessionFactoryProvider extends AutoCloseable {
	SessionFactory getSessionFactory();
}
