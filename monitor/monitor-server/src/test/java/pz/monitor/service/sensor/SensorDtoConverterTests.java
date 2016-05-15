package pz.monitor.service.sensor;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import pz.monitor.db.entity.Metric;
import pz.monitor.db.entity.Resource;
import pz.monitor.db.entity.Sensor;
import pz.monitor.db.helpers.TestEntityFactory;
import pz.monitor.service.common.DtoConverter;
import pz.monitor.service.common.ResourceMapping;
import pz.monitor.service.common.UriHelper;
import pz.monitor.service.metric.MetricResourceMapping;
import pz.monitor.service.resource.ResourceResourceMapping;

public class SensorDtoConverterTests {
	@SuppressWarnings("unchecked")
	@Test
	public void shouldBuildDto() {
		// Arrange
		ResourceMapping<Metric> metricMapping = new MetricResourceMapping();
		ResourceMapping<Resource> resourceMapping = new ResourceResourceMapping();
		
		UriHelper uriHelperMock = mock(UriHelper.class);
		when(uriHelperMock.resourcePath(any(ResourceMapping.class), isA(Metric.class))).thenReturn("/metrics/1");
		when(uriHelperMock.resourcePath(any(ResourceMapping.class), isA(Resource.class))).thenReturn("/resources/1");
		
		DtoConverter<Sensor, SensorDto> dtoConverter = new SensorDtoConverter(uriHelperMock, resourceMapping, metricMapping);
		Sensor entity = TestEntityFactory.getTestSensor();
		entity.setId(Long.valueOf(1));
		
		// Act
		SensorDto dto = dtoConverter.toDto(entity);
		
		// Assert
		assertThat(dto, notNullValue());
		assertThat(dto.getId(), is(entity.getId()));
		assertThat(dto.getResource(), is("/resources/1"));
		assertThat(dto.getMetric(), is("/metrics/1"));
	}
}
