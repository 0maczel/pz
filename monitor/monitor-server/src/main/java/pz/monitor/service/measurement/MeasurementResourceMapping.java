package pz.monitor.service.measurement;

import org.springframework.stereotype.Component;

import pz.monitor.db.entity.Measurement;
import pz.monitor.service.common.ResourceMapping;

@Component
public class MeasurementResourceMapping implements ResourceMapping<Measurement> {
	@Override
	public String getMapping() {
		return "/measurements";
	}
}
