package pz.monitor.service.metric;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

import pz.monitor.db.entity.Metric;
import pz.monitor.service.common.DtoConverter;

@Component
public class MetricDtoConverter implements DtoConverter<Metric, MetricDto> {

	@Override
	public MetricDto toDto(Metric entity) {
		MetricDto dto = new MetricDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}

	@Override
	public Metric toEntity(MetricDto dto) {
		// TODO Auto-generated method stub
		throw new NotImplementedException("To be implemented.");
	}

}
