package pz.monitor.service.metric;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import pz.monitor.db.entity.Metric;
import pz.monitor.db.helpers.TestEntityFactory;
import pz.monitor.service.common.DtoConverter;

public class MetricDtoConverterTests {
	@Test
	public void shouldBuildDto() {
		// Arrange
		DtoConverter<Metric, MetricDto> dtoConverter = new MetricDtoConverter();
		Metric entity = TestEntityFactory.getTestMetric();
		entity.setId(Long.valueOf(1));
		entity.setName("Test metric");

		// Act
		MetricDto dto = dtoConverter.toDto(entity);

		// Assert
		assertThat(dto, notNullValue());
		assertThat(dto.getId(), is(entity.getId()));
		assertThat(dto.getName(), is(entity.getName()));
	}

	@Test
	public void shouldBuildEntity() {
		// Arrange
		DtoConverter<Metric, MetricDto> dtoConverter = new MetricDtoConverter();
		MetricDto dto = new MetricDto();
		dto.setName("Test metric");

		// Act
		Metric entity = dtoConverter.toEntity(dto);

		// Assert
		assertThat(entity.getId(), is(nullValue()));
		assertThat(entity.getEntityVersion(), is(0));
		assertThat(entity.getCreationTimestamp(), is(nullValue()));
		assertThat(entity.getUpdateTimestamp(), is(nullValue()));
		assertThat(entity.getName(), is(dto.getName()));
	}
}
