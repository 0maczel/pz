package pz.monitor.service.resource;

import org.apache.commons.lang3.NotImplementedException;
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
		// TODO Auto-generated method stub
		throw new NotImplementedException("To be implemented.");
	}

}
