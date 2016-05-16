package pz.monitor.service.metric;

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
import pz.monitor.db.query.Query;
import pz.monitor.service.common.DtoConverter;

@RestController
@Transactional
public class MetricService {
	@Autowired
	Repository repository;

	@Autowired
	DtoConverter<Metric, MetricDto> dtoConverter;

	@Autowired
	MetricQueryBuilder queryBuilder;

	@RequestMapping(path = "/metrics", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<MetricDto> get(@RequestParam(name = "name-like", required = false) String nameLike) {
		Query<Metric> query = queryBuilder.build(nameLike);

		List<Metric> entities = repository.query(query);

		List<MetricDto> dtos = new ArrayList<>();
		for (Metric entity : entities) {
			MetricDto dto = dtoConverter.toDto(entity);
			dtos.add(dto);
		}

		return dtos;
	}

	@RequestMapping(path = "/metrics/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public MetricDto get(@PathVariable Long id) {
		Metric entity = repository.get(Metric.class, id);
		MetricDto dto = dtoConverter.toDto(entity);
		return dto;
	}

	@RequestMapping(path = "/metrics", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public MetricDto post(@RequestBody MetricDto dto) {
		Metric entity = dtoConverter.toEntity(dto);
		repository.save(entity);
		return dtoConverter.toDto(entity);
	}
}
