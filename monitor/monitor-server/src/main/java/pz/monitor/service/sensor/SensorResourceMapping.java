package pz.monitor.service.sensor;

import org.springframework.stereotype.Component;

import pz.monitor.db.entity.Sensor;
import pz.monitor.service.common.ResourceMapping;

@Component
public class SensorResourceMapping implements ResourceMapping<Sensor> {
	@Override
	public String getMapping() {
		return "/sensors";
	}

}
