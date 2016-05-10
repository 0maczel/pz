package pz.monitor.service.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import pz.monitor.db.entity.Entity;

@Component
public class UriHelperImpl implements UriHelper {
	@Override
	public String basePath() {
		return ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
	}

	@Override
	public String currentPath() {
		return ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
	}

	@Override
	public String resourcePath(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + id).toUriString();
	}

	@Override
	public <T extends Entity> String resourcePath(T entity) {
		return resourcePath(entity.getId());
	}

	@Override
	public <T extends Entity> List<String> resourcePath(List<T> entities) {
		List<String> resources = new ArrayList<>();
		for (T entity : entities) {
			resources.add(resourcePath(entity));
		}
		return resources;
	}

	@Override
	public String resourcePath(String resourceMapping, Long id) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(resourceMapping).path("/" + id).toUriString();
	}

	@Override
	public <T extends Entity> String resourcePath(String resourceMapping, T entity) {
		return resourcePath(resourceMapping, entity.getId());
	}

	@Override
	public <T extends Entity> List<String> resourcePath(String resourceMapping, List<T> entities) {
		List<String> resources = new ArrayList<>();
		for (T entity : entities) {
			resources.add(resourcePath(resourceMapping, entity));
		}
		return resources;
	}

	@Override
	public <T extends Entity> String resourcePath(ResourceMapping<T> resourceMapping, T entity) {
		return resourcePath(resourceMapping.getMapping(), entity.getId());
	}

	@Override
	public <T extends Entity> List<String> resourcePath(ResourceMapping<T> resourceMapping, List<T> entities) {
		List<String> resources = new ArrayList<>();
		for (T entity : entities) {
			resources.add(resourcePath(resourceMapping, entity));
		}
		return resources;
	}
}
