package pz.monitor.db.query;

import org.springframework.stereotype.Component;

import pz.monitor.db.entity.Entity;
import pz.monitor.db.query.Query.Order;

@Component
public class QueryBuilder implements QueryInitializer {
	@SuppressWarnings("rawtypes")
	private Query query = null;

	@Override
	public <T extends Entity> QueryConstraint<T> queryFor(Class<T> type) {
		query = new Query<T>(type);
		return new QueryConstraintImpl<T>();
	}

	private void validate() {
		if (query == null)
			throw new RuntimeException("Use 'queryFor' to initialize new query build session.");
	}

	private class QueryConstraintImpl<T> implements QueryConstraint<T> {
		@Override
		public QueryFinalizer<T> that(String propertyName, Constraint constraint) {
			validate();
			query.addDirectConstraint(propertyName, constraint);
			return new QueryFinalizerImpl<T>();
		}

		@Override
		public QueryPropertyConstraint<T> with(String referencePropertyName) {
			return new QueryPropertyConstraintImpl<T>(referencePropertyName);
		}

		@Override
		public QueryFinalizer<T> withMaxResults(int count) {
			validate();
			query.setWithMaxResults(true);
			query.setMaxResults(count);
			return new QueryFinalizerImpl<T>();
		}

		@Override
		public QueryOrder<T> orderBy(String propertyName) {
			return new QueryOrderImpl<T>(propertyName);
		}
	}

	private class QueryPropertyConstraintImpl<T> implements QueryPropertyConstraint<T> {
		private String referencePropertyName;

		public QueryPropertyConstraintImpl(String referencePropertyName) {
			this.referencePropertyName = referencePropertyName;
		}

		@Override
		public QueryFinalizer<T> that(String propertyName, Constraint constraint) {
			validate();
			query.addIndirectConstraint(referencePropertyName, propertyName, constraint);
			return new QueryFinalizerImpl<T>();
		}
	}

	private class QueryFinalizerImpl<T> implements QueryFinalizer<T> {
		@Override
		public Query<T> build() {
			validate();
			@SuppressWarnings("unchecked")
			Query<T> out = query;
			query = null;
			return out;
		}

		@Override
		public QueryConstraint<T> and() {
			return new QueryConstraintImpl<T>();
		}
	}

	private class QueryOrderImpl<T> implements QueryOrder<T> {
		private String orderProperty;

		public QueryOrderImpl(String orderProperty) {
			this.orderProperty = orderProperty;
		}

		@Override
		public QueryFinalizer<T> asc() {
			validate();
			query.addOrder(orderProperty, Order.ASC);
			return new QueryFinalizerImpl<T>();
		}

		@Override
		public QueryFinalizer<T> desc() {
			validate();
			query.addOrder(orderProperty, Order.DESC);
			return new QueryFinalizerImpl<T>();
		}

	}
}
