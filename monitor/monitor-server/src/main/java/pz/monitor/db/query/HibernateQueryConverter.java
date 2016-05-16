package pz.monitor.db.query;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

@Component
public class HibernateQueryConverter implements QueryConverter<Criteria> {

	@Override
	public <Q> Criteria convert(Query<Q> query, Criteria target) {
		applyDirectConstraints(query, target);
		applyIndirectConstraints(query, target);
		applyOrder(query, target);
		applyLimit(query, target);
		return target;
	}

	private <Q> void applyDirectConstraints(Query<Q> query, Criteria target) {
		for (Query<Q>.DirectConstraintEntry entry : query.getDirectConstraints()) {
			Criterion restriction = null;
			switch (entry.constraint.type) {
			case EQ:
				restriction = Restrictions.eq(entry.propertyName, entry.constraint.value);
				break;
			case LT:
				restriction = Restrictions.lt(entry.propertyName, entry.constraint.value);
				break;
			case GT:
				restriction = Restrictions.gt(entry.propertyName, entry.constraint.value);
				break;
			case LIKE:
				restriction = Restrictions.like(entry.propertyName, entry.constraint.value);
				break;
			}
			target.add(restriction);
		}
	}

	private <Q> void applyIndirectConstraints(Query<Q> query, Criteria target) {
		Map<String, List<Query<Q>.IndirectConstraintEntry>> byProperty = query.getIndirectConstraints().stream()
				.collect(Collectors.groupingBy(entry -> entry.referencePropertyName));

		for (String referencePropertyName : byProperty.keySet()) {
			Criteria rooted = target.createCriteria(referencePropertyName);

			Criterion restriction = null;
			List<Query<Q>.IndirectConstraintEntry> constraints = byProperty.get(referencePropertyName);
			for (Query<Q>.IndirectConstraintEntry entry : constraints) {
				switch (entry.constraint.type) {
				case EQ:
					restriction = Restrictions.eq(entry.propertyName, entry.constraint.value);
					break;
				case LT:
					restriction = Restrictions.lt(entry.propertyName, entry.constraint.value);
					break;
				case GT:
					restriction = Restrictions.gt(entry.propertyName, entry.constraint.value);
					break;
				case LIKE:
					restriction = Restrictions.like(entry.propertyName, entry.constraint.value);
					break;
				}
				rooted.add(restriction);
			}
		}
	}

	private <Q> void applyOrder(Query<Q> query, Criteria target) {
		for (Query<Q>.OrderEntry entry : query.getOrderProperties()) {
			Order order = null;
			switch (entry.order) {
			case ASC:
				order = Order.asc(entry.property);
				break;
			case DESC:
				order = Order.desc(entry.property);
				break;
			}
			target.addOrder(order);
		}
	}

	private <Q> void applyLimit(Query<Q> query, Criteria target) {
		if (query.isWithMaxResults()) {
			target.setMaxResults(query.getMaxResults());
		}
	}
}
