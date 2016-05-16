package pz.monitor.service.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import pz.monitor.db.Repository;
import pz.monitor.db.entity.Resource;
import pz.monitor.db.query.Query;
import pz.monitor.service.common.DtoConverter;

public class ResourceServiceTests {
	@Test
	public void shouldReturnResourceDto_WhenGetById() {
		// Arrange
		Resource expectedEntity = new Resource();
		ResourceDto expectedDto = new ResourceDto();

		Repository repository = Mockito.mock(Repository.class);
		Mockito.when(repository.get(Resource.class, Long.valueOf(1))).thenReturn(expectedEntity);

		@SuppressWarnings("unchecked")
		DtoConverter<Resource, ResourceDto> dtoConverter = Mockito.mock(DtoConverter.class);
		Mockito.when(dtoConverter.toDto(expectedEntity)).thenReturn(expectedDto);

		ResourceService service = new ResourceService();
		service.repository = repository;
		service.dtoConverter = dtoConverter;

		// Act
		ResourceDto dto = service.get(Long.valueOf(1));

		// Assert
		assertThat(dto, is(expectedDto));
	}

	@Test
	public void shouldReturnResourceDtos_WhenGetByRequestQuery() {
		// Arrange
		String nameLike = "zeus";

		Query<Resource> query = new Query<>(Resource.class);
		Resource expectedEntity1 = new Resource();
		Resource expectedEntity2 = new Resource();
		List<Resource> expectedEntities = new ArrayList<>();
		expectedEntities.add(expectedEntity1);
		expectedEntities.add(expectedEntity2);

		ResourceDto expectedDto1 = new ResourceDto();
		ResourceDto expectedDto2 = new ResourceDto();

		ResourceQueryBuilder queryBuilder = Mockito.mock(ResourceQueryBuilder.class);
		Mockito.when(queryBuilder.build(nameLike)).thenReturn(query);

		Repository repository = Mockito.mock(Repository.class);
		Mockito.when(repository.query(query)).thenReturn(expectedEntities);

		@SuppressWarnings("unchecked")
		DtoConverter<Resource, ResourceDto> dtoConverter = Mockito.mock(DtoConverter.class);
		Mockito.when(dtoConverter.toDto(expectedEntity1)).thenReturn(expectedDto1);
		Mockito.when(dtoConverter.toDto(expectedEntity2)).thenReturn(expectedDto2);

		ResourceService service = new ResourceService();
		service.repository = repository;
		service.dtoConverter = dtoConverter;
		service.queryBuilder = queryBuilder;

		// Act
		List<ResourceDto> dtos = service.get(nameLike);

		// Assert
		assertThat(dtos, contains(expectedDto1, expectedDto2));
	}

	@Test
	public void shouldReturnResourceDtoOfNewlyCreatedResource_WhenPostAResourceDto() {
		// Arrange
		ResourceDto inputDto = new ResourceDto();
		ResourceDto expectedDto = new ResourceDto();
		Resource expectedEntity = new Resource();

		Repository repository = Mockito.mock(Repository.class);

		@SuppressWarnings("unchecked")
		DtoConverter<Resource, ResourceDto> dtoConverter = Mockito.mock(DtoConverter.class);
		Mockito.when(dtoConverter.toEntity(inputDto)).thenReturn(expectedEntity);
		Mockito.when(dtoConverter.toDto(expectedEntity)).thenReturn(expectedDto);

		ResourceService service = new ResourceService();
		service.repository = repository;
		service.dtoConverter = dtoConverter;

		// Act
		ResourceDto dto = service.post(inputDto);

		// Assert
		assertThat(dto, is(expectedDto));
		Mockito.verify(repository).save(expectedEntity);
	}
}
