package pz.monitor.data.persistence;

import java.util.List;

import pz.monitor.data.model.Entity;

public interface Repository {
	<T extends Entity> List<T> all(Class<T> type);
	<T extends Entity> T get(Class<T> type, int id);
	<T extends Entity> void save(T entity);
	<T extends Entity> void delete(T entity);
}
