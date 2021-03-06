package pz.monitor.service.measurement;

import static java.util.Objects.requireNonNull;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pz.monitor.db.Repository;
import pz.monitor.db.entity.Measurement;
import pz.monitor.db.entity.Sensor;
import pz.monitor.db.query.Query;
import pz.monitor.service.common.DateTimeHelper;
import pz.monitor.service.common.DtoConverter;

@RestController
@Transactional
public class MeasurementService {
	@Autowired
	Repository repository;

	@Autowired
	DtoConverter<Measurement, MeasurementDto> dtoConverter;

	@Autowired
	MeasurementQueryBuilder queryBuilder;

	@RequestMapping(path = "/measurements", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<MeasurementDto> get(@RequestParam(name = "resource-like", required = false) String resourceLike,
			@RequestParam(name = "metric-like", required = false) String metricLike,
			@RequestParam(name = "from-date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss z") ZonedDateTime fromDate,
			@RequestParam(name = "to-date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss z") ZonedDateTime toDate,
			@RequestParam(name = "limit", required = false) Long limit) {

		Query<Measurement> query = queryBuilder.build(resourceLike, metricLike, DateTimeHelper.toTimestamp(fromDate),
				DateTimeHelper.toTimestamp(toDate), limit);

		List<Measurement> entities = repository.query(query);

		List<MeasurementDto> dtos = new ArrayList<>();
		for (Measurement entity : entities) {
			MeasurementDto dto = dtoConverter.toDto(entity);
			dtos.add(dto);
		}

		return dtos;
	}

	@RequestMapping(path = "/measurements/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public MeasurementDto get(@PathVariable Long id) {
		Measurement entity = repository.get(Measurement.class, id);
		MeasurementDto dto = dtoConverter.toDto(entity);
		return dto;
	}

	@RequestMapping(path = "/sensors/{sensorId}/measurements", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public MeasurementDto add(@PathVariable Long sensorId, @RequestBody MeasurementDto measurementDto) {

		Measurement measurement = new Measurement();
		requireNonNull(measurementDto, "Measurement identifier required.");

		Sensor reportingSensor = repository.get(Sensor.class, sensorId);
		requireNonNull(reportingSensor, "Sensor with this identifier doesn't exist");
		measurement.setSensor(reportingSensor);
		measurement.setMetric(reportingSensor.getMetric());
		measurement.setResource(reportingSensor.getResource());
		measurement.setValue(measurementDto.getValue());

		repository.save(measurement);
		return dtoConverter.toDto(measurement);
	}

}
