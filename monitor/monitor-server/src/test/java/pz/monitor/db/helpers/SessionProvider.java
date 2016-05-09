package pz.monitor.db.helpers;

import org.hibernate.Session;

public interface SessionProvider extends AutoCloseable {
	Session openSession();
}
