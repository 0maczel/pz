package pz.monitor.service.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import pz.monitor.db.entity.Entity;

public class UriHelper {
	public static String basePath() {
		return ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
	}

	public static String currentPath() {
		return ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
	}

	public static String resourcePath(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + id).toUriString();
	}

	public static <T extends Entity> String resourcePath(T entity) {
		return resourcePath(entity.getId());
	}

	public static <T extends Entity> List<String> resourcePath(List<T> entities) {
		List<String> resources = new ArrayList<>();
		for (T entity : entities) {
			resources.add(resourcePath(entity));
		}
		return resources;
	}

	public static String resourcePath(String resourceMapping, Long id) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(resourceMapping).path("/" + id).toUriString();
	}

	public static <T extends Entity> String resourcePath(String resourceMapping, T entity) {
		return resourcePath(resourceMapping, entity.getId());
	}

	public static <T extends Entity> List<String> resourcePath(String resourceMapping, List<T> entities) {
		List<String> resources = new ArrayList<>();
		for (T entity : entities) {
			resources.add(resourcePath(resourceMapping, entity));
		}
		return resources;
	}

	public static <T extends Entity> String resourcePath(ResourceMapping<T> resourceMapping, T entity) {
		return resourcePath(resourceMapping.getMapping(), entity.getId());
	}

	public static <T extends Entity> List<String> resourcePath(ResourceMapping<T> resourceMapping, List<T> entities) {
		List<String> resources = new ArrayList<>();
		for (T entity : entities) {
			resources.add(resourcePath(resourceMapping, entity));
		}
		return resources;
	}
}
