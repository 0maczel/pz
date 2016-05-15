package pz.monitor.service.sensor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.contains;

import org.junit.Test;
import org.mockito.Mockito;

import pz.monitor.db.Repository;
import pz.monitor.db.entity.Sensor;
import pz.monitor.db.query.Query;
import pz.monitor.service.common.DtoConverter;

public class SensorServiceTests {
	@Test
	public void shouldReturnSensorDto_WhenGetById() {
		// Arrange
		Sensor expectedEntity = new Sensor();
		SensorDto expectedDto = new SensorDto();
		
		Repository repository = Mockito.mock(Repository.class);
		Mockito.when(repository.get(Sensor.class, Long.valueOf(1))).thenReturn(expectedEntity);
		
		@SuppressWarnings("unchecked")
		DtoConverter<Sensor, SensorDto> dtoConverter = Mockito.mock(DtoConverter.class);
		Mockito.when(dtoConverter.toDto(expectedEntity)).thenReturn(expectedDto);
		
		SensorService service = new SensorService();
		service.repository = repository;
		service.dtoConverter = dtoConverter;
		
		// Act
		SensorDto dto = service.get(Long.valueOf(1));
		
		// Assert
		assertThat(dto, is(expectedDto));
	}
	
	@Test
	public void shouldReturnSensorsDtos_WhenGetByRequestQuery() {
		// Arrange
		String resourceName = "zeus";
		String metricName = "CPU";
		
		Query<Sensor> query = new Query<>(Sensor.class);
		Sensor expectedEntity1 = new Sensor();
		Sensor expectedEntity2 = new Sensor();
		List<Sensor> expectedEntities = new ArrayList<>();
		expectedEntities.add(expectedEntity1);
		expectedEntities.add(expectedEntity2);
		
		SensorDto expectedDto1 = new SensorDto();
		SensorDto expectedDto2 = new SensorDto();
		
		SensorQueryBuilder queryBuilder = Mockito.mock(SensorQueryBuilder.class);
		Mockito.when(queryBuilder.build(resourceName, metricName)).thenReturn(query);
		
		Repository repository = Mockito.mock(Repository.class);
		Mockito.when(repository.query(query)).thenReturn(expectedEntities);
		
		@SuppressWarnings("unchecked")
		DtoConverter<Sensor, SensorDto> dtoConverter = Mockito.mock(DtoConverter.class);
		Mockito.when(dtoConverter.toDto(expectedEntity1)).thenReturn(expectedDto1);
		Mockito.when(dtoConverter.toDto(expectedEntity2)).thenReturn(expectedDto2);
		
		SensorService service = new SensorService();
		service.repository = repository;
		service.dtoConverter = dtoConverter;
		service.queryBuilder = queryBuilder;
		
		// Act
		List<SensorDto> dtos = service.get(resourceName, metricName);
		
		// Assert
		assertThat(dtos, contains(expectedDto1, expectedDto2));
	}
}
