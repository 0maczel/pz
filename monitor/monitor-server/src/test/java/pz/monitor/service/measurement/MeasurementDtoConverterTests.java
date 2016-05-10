package pz.monitor.service.measurement;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import pz.monitor.db.entity.Measurement;
import pz.monitor.db.entity.Metric;
import pz.monitor.db.entity.Resource;
import pz.monitor.db.helpers.TestEntityFactory;
import pz.monitor.service.common.DtoConverter;
import pz.monitor.service.common.ResourceMapping;
import pz.monitor.service.common.UriHelper;
import pz.monitor.service.metric.MetricResourceMapping;
import pz.monitor.service.resource.ResourceResourceMapping;

public class MeasurementDtoConverterTests {
	@Test
	public void shouldBuildDto() {
		// Arrange
		ResourceMapping<Metric> metricMapping = new MetricResourceMapping();
		ResourceMapping<Resource> resourceMapping = new ResourceResourceMapping();

		UriHelper uriHelperMock = mock(UriHelper.class);
		when(uriHelperMock.resourcePath(any(ResourceMapping.class), isA(Metric.class))).thenReturn("/metrics/1");
		when(uriHelperMock.resourcePath(any(ResourceMapping.class), isA(Resource.class))).thenReturn("/resources/1");

		DtoConverter<Measurement, MeasurementDto> dtoConverter = new MeasurementDtoConverter(null, uriHelperMock,
				resourceMapping, metricMapping);
		Measurement entity = TestEntityFactory.getTestMeasurement();

		// Act
		MeasurementDto dto = dtoConverter.toDto(entity);

		// Assert
		assertNotNull(dto);
		assertEquals(dto.getId(), entity.getId());
		assertEquals(dto.getCreationTimestamp(), entity.getCreationTimestamp());
		assertEquals(dto.getUpdateTimestamp(), entity.getUpdateTimestamp());
		assertEquals(dto.getValue(), entity.getValue(), 0);
		assertEquals(dto.getMetric(), "/metrics/1");
		assertEquals(dto.getResource(), "/resources/1");
	}
}
