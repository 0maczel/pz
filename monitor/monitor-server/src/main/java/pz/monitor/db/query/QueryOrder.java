package pz.monitor.db.query;

public interface QueryOrder<T> {
	QueryFinalizer<T> asc();
	QueryFinalizer<T> desc();
}
