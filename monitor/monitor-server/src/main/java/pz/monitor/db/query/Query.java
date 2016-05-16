package pz.monitor.db.query;

import java.util.ArrayList;
import java.util.List;

public class Query<T> {
	private Class<T> type;
	private int maxResults = 0;
	private boolean withMaxResults = false;
	private List<DirectConstraintEntry> directConstraints = new ArrayList<>();
	private List<IndirectConstraintEntry> indirectConstraints = new ArrayList<>();
	private List<OrderEntry> orderProperties = new ArrayList<>();

	public Query(Class<T> type) {
		this.type = type;
	}

	public Class<T> getType() {
		return type;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public boolean isWithMaxResults() {
		return withMaxResults;
	}

	public void setWithMaxResults(boolean withMaxResults) {
		this.withMaxResults = withMaxResults;
	}

	public void addDirectConstraint(String propertyName, Constraint constraint) {
		getDirectConstraints().add(new DirectConstraintEntry(propertyName, constraint));
	}

	public List<DirectConstraintEntry> getDirectConstraints() {
		return directConstraints;
	}

	public void addOrder(String propertyName, Order order) {
		getOrderProperties().add(new OrderEntry(propertyName, order));
	}

	public List<OrderEntry> getOrderProperties() {
		return orderProperties;
	}

	public void addIndirectConstraint(String referencePropertyName, String propertyName, Constraint constraint) {
		getIndirectConstraints().add(new IndirectConstraintEntry(referencePropertyName, propertyName, constraint));
	}

	public List<IndirectConstraintEntry> getIndirectConstraints() {
		return indirectConstraints;
	}

	public class DirectConstraintEntry {
		public String propertyName;
		public Constraint constraint;

		public DirectConstraintEntry(String propertyName, Constraint constraint) {
			this.propertyName = propertyName;
			this.constraint = constraint;
		}
	}

	public class IndirectConstraintEntry extends DirectConstraintEntry {
		public String referencePropertyName;

		public IndirectConstraintEntry(String referencePropertyName, String propertyName, Constraint constraint) {
			super(propertyName, constraint);
			this.referencePropertyName = referencePropertyName;
		}

	}

	public class OrderEntry {
		public String property;
		public Order order;

		public OrderEntry(String property, Order order) {
			this.property = property;
			this.order = order;
		}
	}

	public enum Order {
		ASC, DESC
	}
}
