package pz.monitor.service.metric;

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
		Metric entity = new Metric();
		entity.setName(dto.getName());
		return entity;
	}

}
