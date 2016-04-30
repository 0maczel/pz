package pz.monitor.data.persistence;

import java.util.function.Consumer;
import java.util.function.Function;

public interface UnitOfWork {
	void doWork(Consumer<Repository> work);
	<T> T doWork(Function<Repository, T> work);
}
