package pz.monitor.db.query;

public interface QueryConstraint<T> extends QueryPropertyConstraint<T> {
	QueryPropertyConstraint<T> with(String referencePropertyName);
	QueryFinalizer<T> withMaxResults(int count);
	QueryOrder<T> orderBy(String propertyName);
}
