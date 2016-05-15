package pz.monitor.service.resource;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import pz.monitor.db.entity.Resource;
import pz.monitor.db.helpers.TestEntityFactory;
import pz.monitor.service.common.DtoConverter;

public class ResourceDtoConverterTests {
	@Test
	public void shouldBuildDto() {
		// Arrange
		DtoConverter<Resource, ResourceDto> dtoConverter = new ResourceDtoConverter();
		Resource entity = TestEntityFactory.getTestResource();
		entity.setId(Long.valueOf(1));
		entity.setName("Test resource");

		// Act
		ResourceDto dto = dtoConverter.toDto(entity);

		// Assert
		assertThat(dto, notNullValue());
		assertThat(dto.getId(), is(entity.getId()));
		assertThat(dto.getName(), is(entity.getName()));
	}

	@Test
	public void shouldBuildEntity() {
		// Arrange
		DtoConverter<Resource, ResourceDto> dtoConverter = new ResourceDtoConverter();
		ResourceDto dto = new ResourceDto();
		dto.setName("Test resource");

		// Act
		Resource entity = dtoConverter.toEntity(dto);

		// Assert
		assertThat(entity.getId(), is(nullValue()));
		assertThat(entity.getEntityVersion(), is(0));
		assertThat(entity.getCreationTimestamp(), is(nullValue()));
		assertThat(entity.getUpdateTimestamp(), is(nullValue()));
		assertThat(entity.getName(), is(dto.getName()));
	}
}
