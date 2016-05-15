package pz.monitor.service.sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pz.monitor.db.entity.Metric;
import pz.monitor.db.entity.Resource;
import pz.monitor.db.entity.Sensor;
import pz.monitor.service.common.DtoConverter;
import pz.monitor.service.common.ResourceMapping;
import pz.monitor.service.common.UriHelper;

@Component
public class SensorDtoConverter implements DtoConverter<Sensor, SensorDto> {
	private UriHelper uriHelper;
	private ResourceMapping<Resource> resourceMapping;
	private ResourceMapping<Metric> metricMapping;
	
	@Autowired
	public SensorDtoConverter(UriHelper uriHelper, ResourceMapping<Resource> resourceMapping, ResourceMapping<Metric> metricMapping) {
		this.uriHelper = uriHelper;
		this.resourceMapping = resourceMapping;
		this.metricMapping = metricMapping;
	}

	@Override
	public SensorDto toDto(Sensor entity) {
		SensorDto dto = new SensorDto();
		dto.setId(entity.getId());
		dto.setResource(uriHelper.resourcePath(resourceMapping, entity.getResource()));
		dto.setMetric(uriHelper.resourcePath(metricMapping, entity.getMetric()));
		return dto;
	}

	@Override
	public Sensor toEntity(SensorDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
