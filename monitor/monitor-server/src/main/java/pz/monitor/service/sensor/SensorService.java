package pz.monitor.service.sensor;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pz.monitor.db.Repository;
import pz.monitor.db.entity.Metric;
import pz.monitor.db.entity.Resource;
import pz.monitor.db.entity.Sensor;
import pz.monitor.db.query.Query;
import pz.monitor.service.common.DtoConverter;

@RestController
@Transactional
public class SensorService {
	@Autowired
	Repository repository;

	@Autowired
	DtoConverter<Sensor, SensorDto> dtoConverter;

	@Autowired
	SensorQueryBuilder queryBuilder;

	@RequestMapping(path = "/sensors", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<SensorDto> get(@RequestParam(name = "resource-name", required = false) String resourceName,
			@RequestParam(name = "metric-name", required = false) String metricName) {
		Query<Sensor> query = queryBuilder.build(resourceName, metricName);

		List<Sensor> entities = repository.query(query);

		List<SensorDto> dtos = new ArrayList<>();
		for (Sensor entity : entities) {
			SensorDto dto = dtoConverter.toDto(entity);
			dtos.add(dto);
		}

		return dtos;
	}

	@RequestMapping(path = "/sensors/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public SensorDto get(@PathVariable Long id) {
		Sensor entity = repository.get(Sensor.class, id);
		SensorDto dto = dtoConverter.toDto(entity);
		return dto;
	}

	@RequestMapping(path = "/sensors", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public SensorDto create(@RequestBody SensorCreateRequest createRequest) {
		requireNonNull(createRequest.getMetricId(), "metric identifier required");
		requireNonNull(createRequest.getResourceId(), "resource identifier required");
		Sensor createCandidate = new Sensor();
		createCandidate.setMetric(repository.get(Metric.class, createRequest.getMetricId()));
		createCandidate.setResource(repository.get(Resource.class, createRequest.getResourceId()));
		repository.save(createCandidate);
		return dtoConverter.toDto(createCandidate);
	}
}
