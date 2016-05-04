package pz.monitor.data.persistence;

import java.util.function.Consumer;
import java.util.function.Function;

import pz.monitor.db.Repository;

public interface UnitOfWork {
	void doWork(Consumer<Repository> work);
	<T> T doWork(Function<Repository, T> work);
}
