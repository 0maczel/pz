package pz.monitor.db.query;

import pz.monitor.db.entity.Entity;

public interface QueryInitializer {
	<T extends Entity> QueryConstraint<T> queryFor(Class<T> type);
}
