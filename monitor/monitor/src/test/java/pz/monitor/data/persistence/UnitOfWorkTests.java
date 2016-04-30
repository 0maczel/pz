package pz.monitor.data.persistence;

import junit.framework.TestCase;
import pz.monitor.data.infrastructure.MemoryDatabaseSessionProvider;
import pz.monitor.data.infrastructure.SessionProvider;

public class UnitOfWorkTests extends TestCase{
	public void test_SomeTest() throws Exception {
		try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider()) {
			UnitOfWork unitOfWork = new TransactionalUnitOfWork(sessionProvider);
			unitOfWork.doWork(repository -> {
				return 5;
			});
		}
	}
}
