package pz.monitor.service.common;

import pz.monitor.db.entity.Entity;

public interface ResourceMapping<E extends Entity> {
	String getMapping();
}
