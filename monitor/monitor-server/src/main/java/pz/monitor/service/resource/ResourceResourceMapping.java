package pz.monitor.service.resource;

import org.springframework.stereotype.Component;

import pz.monitor.db.entity.Resource;
import pz.monitor.service.common.ResourceMapping;

@Component
public class ResourceResourceMapping implements ResourceMapping<Resource> {
	@Override
	public String getMapping() {
		return "/resources";
	}
}
