package pz.monitor.data.persistence;

import java.util.function.Function;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pz.monitor.data.infrastructure.SessionProvider;

public class TransactionalUnitOfWork implements UnitOfWork {
	SessionProvider sessionProvider;
	
	public TransactionalUnitOfWork(SessionProvider sessionProvider) {
		this.sessionProvider = sessionProvider;
	}
	
	@Override
	public <T> T doWork(Function<Repository, T> work) {
		T returnValue;
		Session session = sessionProvider.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			returnValue = work.apply(new SessionRepository(session));
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}
		return returnValue;
	}
}
