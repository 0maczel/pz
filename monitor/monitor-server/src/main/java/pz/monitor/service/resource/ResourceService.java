package pz.monitor.service.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pz.monitor.db.Repository;
import pz.monitor.db.entity.Resource;
import pz.monitor.db.query.Query;
import pz.monitor.service.common.DtoConverter;

@RestController
@Transactional
public class ResourceService {
	@Autowired
	Repository repository;

	@Autowired
	DtoConverter<Resource, ResourceDto> dtoConverter;

	@Autowired
	ResourceQueryBuilder queryBuilder;

	@RequestMapping(path = "/resources", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<ResourceDto> get(@RequestParam(name = "name-like", required = false) String nameLike) {
		Query<Resource> query = queryBuilder.build(nameLike);

		List<Resource> entities = repository.query(query);

		List<ResourceDto> dtos = new ArrayList<>();
		for (Resource entity : entities) {
			ResourceDto dto = dtoConverter.toDto(entity);
			dtos.add(dto);
		}

		return dtos;
	}

	@RequestMapping(path = "/resources/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResourceDto get(@PathVariable Long id) {
		Resource entity = repository.get(Resource.class, id);
		ResourceDto dto = dtoConverter.toDto(entity);
		return dto;
	}
}
