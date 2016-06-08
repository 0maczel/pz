package pz.monitor.service.measurement.complex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pz.monitor.db.entity.ComplexMeasurement;
import pz.monitor.db.entity.Metric;
import pz.monitor.db.entity.Resource;
import pz.monitor.db.entity.Sensor;
import pz.monitor.service.common.DtoConverter;
import pz.monitor.service.common.ResourceMapping;
import pz.monitor.service.common.UriHelper;

@Component
public class ComplexMeasurementDtoConverter implements DtoConverter<ComplexMeasurement, ComplexMeasurementDto> {

	@Autowired
	private ResourceMapping<Metric> metricMapping;
	@Autowired
	private ResourceMapping<Resource> resourceMapping;
	@Autowired
	private UriHelper uriHelper;

	@Override
	public ComplexMeasurementDto toDto(ComplexMeasurement entity) {

		ComplexMeasurementDto dto = new ComplexMeasurementDto();
		dto.setName(entity.getName());
		dto.setId(entity.getId());
		dto.setWindowDurationMilis(entity.getWindowDurationMilis());
		dto.setWindowIntervalMilis(entity.getWindowIntervalMilis());
		dto.setCreationTimestamp(entity.getCreationTimestamp());

		Sensor sensor = entity.getSensor();
		dto.setMetric(uriHelper.resourcePath(metricMapping, sensor.getMetric()));
		dto.setResource(uriHelper.resourcePath(resourceMapping, sensor.getResource()));

		return dto;
	}

	@Override
	public ComplexMeasurement toEntity(ComplexMeasurementDto dto) {
		throw new UnsupportedOperationException("Dto to entity conversion not supported");
	}
}
