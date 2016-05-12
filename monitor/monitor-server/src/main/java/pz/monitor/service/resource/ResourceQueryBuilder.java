package pz.monitor.service.resource;

import pz.monitor.db.entity.Resource;
import pz.monitor.db.query.Query;

public interface ResourceQueryBuilder {
	Query<Resource> build(String nameLike);
}
