package pz.monitor.db.query;

public class Constraint {
	public ConstraintType type;
	public Object value;

	public Constraint(ConstraintType type, Object value) {
		this.type = type;
		this.value = value;
	}

	public enum ConstraintType {
		EQ, LT, GT, LIKE
	}
}
