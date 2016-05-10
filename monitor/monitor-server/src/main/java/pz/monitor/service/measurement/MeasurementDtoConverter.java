package pz.monitor.service.measurement;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pz.monitor.db.Repository;
import pz.monitor.db.entity.Measurement;
import pz.monitor.db.entity.Metric;
import pz.monitor.db.entity.Resource;
import pz.monitor.service.common.DtoConverter;
import pz.monitor.service.common.ResourceMapping;
import pz.monitor.service.common.UriHelper;

@Component
public class MeasurementDtoConverter implements DtoConverter<Measurement, MeasurementDto> {
	private Repository repository;
	private UriHelper uriHelper;
	private ResourceMapping<Resource> resourceMapping;
	private ResourceMapping<Metric> metricMapping;

	@Autowired
	public MeasurementDtoConverter(Repository repository, UriHelper uriHelper,
			ResourceMapping<Resource> resourceMapping, ResourceMapping<Metric> metricMapping) {
		this.repository = repository;
		this.uriHelper = uriHelper;
		this.resourceMapping = resourceMapping;
		this.metricMapping = metricMapping;
	}

	@Override
	public MeasurementDto toDto(Measurement entity) {
		MeasurementDto dto = new MeasurementDto();

		dto.setId(entity.getId());
		dto.setCreationTimestamp(entity.getCreationTimestamp());
		dto.setUpdateTimestamp(entity.getUpdateTimestamp());
		dto.setValue(entity.getValue());
		dto.setResource(uriHelper.resourcePath(resourceMapping, entity.getResource()));
		dto.setMetric(uriHelper.resourcePath(metricMapping, entity.getMetric()));

		return dto;
	}

	@Override
	public Measurement toEntity(MeasurementDto dto) {
		// TODO Auto-generated method stub
		throw new NotImplementedException("To be implemented.");
	}

}
