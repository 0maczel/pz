package pz.monitor.service.metric;

import org.springframework.stereotype.Component;

import pz.monitor.db.entity.Metric;
import pz.monitor.service.common.ResourceMapping;

@Component
public class MetricResourceMapping implements ResourceMapping<Metric> {
	@Override
	public String getMapping() {
		return "/metrics";
	}
}
