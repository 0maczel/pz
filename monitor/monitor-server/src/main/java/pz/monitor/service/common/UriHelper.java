package pz.monitor.service.common;

import java.util.List;

import pz.monitor.db.entity.Entity;

public interface UriHelper {

	String basePath();

	String currentPath();

	String resourcePath(Long id);

	<T extends Entity> String resourcePath(T entity);

	<T extends Entity> List<String> resourcePath(List<T> entities);

	String resourcePath(String resourceMapping, Long id);

	<T extends Entity> String resourcePath(String resourceMapping, T entity);

	<T extends Entity> List<String> resourcePath(String resourceMapping, List<T> entities);

	<T extends Entity> String resourcePath(ResourceMapping<T> resourceMapping, T entity);

	<T extends Entity> List<String> resourcePath(ResourceMapping<T> resourceMapping, List<T> entities);

}