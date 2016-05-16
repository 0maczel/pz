package pz.monitor.service.metric;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import pz.monitor.db.Repository;
import pz.monitor.db.entity.Metric;
import pz.monitor.db.query.Query;
import pz.monitor.service.common.DtoConverter;

public class MetricServiceTests {
	@Test
	public void shouldReturnMetricDto_WhenGetById() {
		// Arrange
		Metric expectedEntity = new Metric();
		MetricDto expectedDto = new MetricDto();

		Repository repository = Mockito.mock(Repository.class);
		Mockito.when(repository.get(Metric.class, Long.valueOf(1))).thenReturn(expectedEntity);

		@SuppressWarnings("unchecked")
		DtoConverter<Metric, MetricDto> dtoConvert1er = Mockito.mock(DtoConverter.class);
		Mockito.when(dtoConvert1er.toDto(expectedEntity)).thenReturn(expectedDto);

		MetricService service = new MetricService();
		service.repository = repository;
		service.dtoConverter = dtoConvert1er;

		// Act
		MetricDto dto = service.get(Long.valueOf(1));

		// Assert
		assertThat(dto, is(expectedDto));
	}

	@Test
	public void shouldReturnMetricDtos_WhenGetByRequestQuery() {
		// Arrange
		String nameLike = "CPU";

		Query<Metric> query = new Query<>(Metric.class);
		Metric expectedEntity1 = new Metric();
		Metric expectedEntity2 = new Metric();
		List<Metric> expectedEntities = new ArrayList<>();
		expectedEntities.add(expectedEntity1);
		expectedEntities.add(expectedEntity2);

		MetricDto expectedDto1 = new MetricDto();
		MetricDto expectedDto2 = new MetricDto();

		MetricQueryBuilder queryBuilder = Mockito.mock(MetricQueryBuilder.class);
		Mockito.when(queryBuilder.build(nameLike)).thenReturn(query);

		Repository repository = Mockito.mock(Repository.class);
		Mockito.when(repository.query(query)).thenReturn(expectedEntities);

		@SuppressWarnings("unchecked")
		DtoConverter<Metric, MetricDto> dtoConverter = Mockito.mock(DtoConverter.class);
		Mockito.when(dtoConverter.toDto(expectedEntity1)).thenReturn(expectedDto1);
		Mockito.when(dtoConverter.toDto(expectedEntity2)).thenReturn(expectedDto2);

		MetricService service = new MetricService();
		service.repository = repository;
		service.dtoConverter = dtoConverter;
		service.queryBuilder = queryBuilder;

		// Act
		List<MetricDto> dtos = service.get(nameLike);

		// Assert
		assertThat(dtos, contains(expectedDto1, expectedDto2));
	}

	@Test
	public void shouldReturnMetricDtoOfNewlyCreatedMetric_WhenPostAMetricDto() {
		// Arrange
		MetricDto inputDto = new MetricDto();
		MetricDto expectedDto = new MetricDto();
		Metric expectedEntity = new Metric();

		Repository repository = Mockito.mock(Repository.class);

		@SuppressWarnings("unchecked")
		DtoConverter<Metric, MetricDto> dtoConverter = Mockito.mock(DtoConverter.class);
		Mockito.when(dtoConverter.toEntity(inputDto)).thenReturn(expectedEntity);
		Mockito.when(dtoConverter.toDto(expectedEntity)).thenReturn(expectedDto);

		MetricService service = new MetricService();
		service.repository = repository;
		service.dtoConverter = dtoConverter;

		// Act
		MetricDto dto = service.post(inputDto);

		// Assert
		assertThat(dto, is(expectedDto));
		Mockito.verify(repository).save(expectedEntity);
	}
}
