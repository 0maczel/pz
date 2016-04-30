package pz.monitor.data.persistence;

import java.util.List;

import junit.framework.TestCase;
import pz.monitor.data.TestEntityFactory;
import pz.monitor.data.infrastructure.MemoryDatabaseSessionProvider;
import pz.monitor.data.infrastructure.SessionProvider;
import pz.monitor.data.model.Metric;

public class UnitOfWorkTests extends TestCase{
	public void test_ShouldSaveMetric_WhenDoneAsWork() throws Exception {
		try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider()) {
			UnitOfWork unitOfWork = new TransactionalUnitOfWork(sessionProvider);
			Metric metricToSave = TestEntityFactory.getTestMetric();
			
			unitOfWork.doWork(repository -> {
				int countOfMetrics = repository.all(Metric.class).size();
				assertEquals(0, countOfMetrics);
			});
			
			unitOfWork.doWork(repository -> {
				repository.save(metricToSave);
			});
			
			unitOfWork.doWork(repository -> {
				int countOfMetrics = repository.all(Metric.class).size();
				assertEquals(1, countOfMetrics);
			});
		}
	}
	
	public void test_ShouldReturnListOfTenMetrics_WhenReturnedInDoWork() throws Exception {
		try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider()) {
			UnitOfWork unitOfWork = new TransactionalUnitOfWork(sessionProvider);
			
			unitOfWork.doWork(repository -> {
				for(int i = 0; i<10; i++) {
					Metric metricToSave = TestEntityFactory.getTestMetric();
					repository.save(metricToSave);					
				}
			});
			
			List<Metric> metricsList = unitOfWork.doWork(repository -> {
				return repository.all(Metric.class);
			});
			
			assertEquals(10, metricsList.size());
		}
	}
	
	public void test_ShouldSaveNothing_WhenExceptionThrownInDoWork() throws Exception {
		try (SessionProvider sessionProvider = new MemoryDatabaseSessionProvider()) {
			UnitOfWork unitOfWork = new TransactionalUnitOfWork(sessionProvider);
			
			unitOfWork.doWork(repository -> {
				int countOfMetrics = repository.all(Metric.class).size();
				assertEquals(0, countOfMetrics);
			});
			
			try {
				unitOfWork.doWork(repository -> {
					Metric metricToSave = TestEntityFactory.getTestMetric();
					repository.save(metricToSave);
					
					metricToSave = TestEntityFactory.getTestMetric();
					repository.save(metricToSave);
					
					metricToSave = TestEntityFactory.getTestMetric();
					repository.save(metricToSave);
					
					throwException();
				});				
			}
			catch(Exception ex) {}
			
			unitOfWork.doWork(repository -> {
				int countOfMetrics = repository.all(Metric.class).size();
				assertEquals(0, countOfMetrics);
			});
		}
	}
	
	private void throwException() {
		throw new RuntimeException();
	}
}
