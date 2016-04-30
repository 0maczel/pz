package pz.monitor.data.persistence;

import java.util.function.Function;

public interface UnitOfWork {
	<T> T doWork(Function<Repository, T> work);
}
