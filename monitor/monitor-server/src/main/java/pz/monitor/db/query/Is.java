package pz.monitor.db.query;

import pz.monitor.db.query.Constraint.ConstraintType;

public class Is {
	public static Constraint equalTo(Object value) {
		return new Constraint(ConstraintType.EQ, value);
	}

	public static Constraint lessThan(Object value) {
		return new Constraint(ConstraintType.LT, value);
	}

	public static Constraint greaterThan(Object value) {
		return new Constraint(ConstraintType.GT, value);
	}

	public static Constraint like(Object value) {
		return new Constraint(ConstraintType.LIKE, value);
	}
}
