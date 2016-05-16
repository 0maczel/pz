package pz.monitor.service.sensor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pz.monitor.db.entity.Metric;
import pz.monitor.db.entity.Resource;
import pz.monitor.db.entity.Sensor;
import pz.monitor.db.helpers.TestEntityFactory;
import pz.monitor.service.common.ResourceMapping;
import pz.monitor.service.common.UriHelper;
import pz.monitor.service.metric.MetricResourceMapping;
import pz.monitor.service.resource.ResourceResourceMapping;

@RunWith(MockitoJUnitRunner.class)
public class SensorDtoConverterTests {
	@Mock
	private UriHelper uriHelper;
	@Mock
	private ResourceMapping<Metric> metricMapping = new MetricResourceMapping();
	@Mock
	private ResourceMapping<Resource> resourceMapping = new ResourceResourceMapping();

	@InjectMocks
	private SensorDtoConverter dtoConverter;

	@SuppressWarnings("unchecked")
	@Test
	public void shouldBuildDto() {
		when(uriHelper.resourcePath(any(ResourceMapping.class), isA(Metric.class))).thenReturn("/metrics/1");
		when(uriHelper.resourcePath(any(ResourceMapping.class), isA(Resource.class))).thenReturn("/resources/1");

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

	@Test
	public void shoudFailOnToEntityBuild() {
		SensorDto sensorDto = mock(SensorDto.class);

		try {
			dtoConverter.toEntity(sensorDto);
			fail("exception expected");
		} catch (UnsupportedOperationException e) {
			assertEquals("Sensor dto to entity not supported", e.getMessage());
		}

	}
}
