package pz.monitor.db.query;

public interface QueryConverter<T> {
	<Q> T convert(Query<Q> query, T target);
}
