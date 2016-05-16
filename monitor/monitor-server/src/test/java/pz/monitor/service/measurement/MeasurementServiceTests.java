package pz.monitor.service.measurement;

import static org.junit.Assert.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.contains;

import org.junit.Test;
import org.mockito.Mockito;

import pz.monitor.db.Repository;
import pz.monitor.db.entity.Measurement;
import pz.monitor.db.query.Query;
import pz.monitor.service.common.DateTimeHelper;
import pz.monitor.service.common.DtoConverter;

public class MeasurementServiceTests {
	@Test
	public void shouldReturnMeasurementDto_WhenGetById() {
		// Arrange
		Measurement expectedEntity = new Measurement();
		MeasurementDto expectedDto = new MeasurementDto();

		Repository repository = Mockito.mock(Repository.class);
		Mockito.when(repository.get(Measurement.class, Long.valueOf(1))).thenReturn(expectedEntity);

		@SuppressWarnings("unchecked")
		DtoConverter<Measurement, MeasurementDto> dtoConverter = Mockito.mock(DtoConverter.class);
		Mockito.when(dtoConverter.toDto(expectedEntity)).thenReturn(expectedDto);

		MeasurementService service = new MeasurementService();
		service.repository = repository;
		service.dtoConverter = dtoConverter;

		// Act
		MeasurementDto dto = service.get(Long.valueOf(1));

		// Assert
		assertThat(dto, is(expectedDto));
	}

	@Test
	public void shouldReturnMeasurementDtos_WhenGetByRequestQuery() {
		// Arrange
		String resourceLike = "resource";
		String metricLike = "metric";
		ZonedDateTime fromDate = ZonedDateTime.now();
		ZonedDateTime toDate = ZonedDateTime.now();
		Long limit = Long.valueOf(1);

		Query<Measurement> query = new Query<>(Measurement.class);
		Measurement expectedEntity1 = new Measurement();
		Measurement expectedEntity2 = new Measurement();
		List<Measurement> expectedEntities = new ArrayList<>();
		expectedEntities.add(expectedEntity1);
		expectedEntities.add(expectedEntity2);

		MeasurementDto expectedDto1 = new MeasurementDto();
		MeasurementDto expectedDto2 = new MeasurementDto();

		MeasurementQueryBuilder queryBuilder = Mockito.mock(MeasurementQueryBuilder.class);
		Mockito.when(queryBuilder.build(resourceLike, metricLike, DateTimeHelper.toTimestamp(fromDate),
				DateTimeHelper.toTimestamp(toDate), limit)).thenReturn(query);

		Repository repository = Mockito.mock(Repository.class);
		Mockito.when(repository.query(query)).thenReturn(expectedEntities);

		@SuppressWarnings("unchecked")
		DtoConverter<Measurement, MeasurementDto> dtoConverter = Mockito.mock(DtoConverter.class);
		Mockito.when(dtoConverter.toDto(expectedEntity1)).thenReturn(expectedDto1);
		Mockito.when(dtoConverter.toDto(expectedEntity2)).thenReturn(expectedDto2);

		MeasurementService service = new MeasurementService();
		service.repository = repository;
		service.dtoConverter = dtoConverter;
		service.queryBuilder = queryBuilder;

		// Act
		List<MeasurementDto> dtos = service.get(resourceLike, metricLike, fromDate, toDate, limit);

		// Assert
		assertThat(dtos, contains(expectedDto1, expectedDto2));
	}
}
