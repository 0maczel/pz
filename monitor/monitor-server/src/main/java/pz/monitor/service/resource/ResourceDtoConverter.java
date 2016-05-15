package pz.monitor.service.resource;

import org.springframework.stereotype.Component;

import pz.monitor.db.entity.Resource;
import pz.monitor.service.common.DtoConverter;

@Component
public class ResourceDtoConverter implements DtoConverter<Resource, ResourceDto> {

	@Override
	public ResourceDto toDto(Resource entity) {
		ResourceDto dto = new ResourceDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}

	@Override
	public Resource toEntity(ResourceDto dto) {
		Resource entity = new Resource();
		entity.setName(dto.getName());
		return entity;
	}

}
