package pz.monitor.db.query;

public interface QueryPropertyConstraint<T> {
	QueryFinalizer<T> that(String propertyName, Constraint constraint);
}
