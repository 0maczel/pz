package pz.monitor.data.infrastructure;

import org.hibernate.Session;

public interface SessionProvider extends AutoCloseable {
	Session openSession();
}
