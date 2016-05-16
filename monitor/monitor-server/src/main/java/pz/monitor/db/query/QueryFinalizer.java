package pz.monitor.db.query;

public interface QueryFinalizer<T> {
	Query<T> build();

	QueryConstraint<T> and();
}
