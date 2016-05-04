package pz.monitor.db;

import java.util.List;

import pz.monitor.db.entity.Entity;

public interface Repository
{
    <T extends Entity> List<T> all(Class<T> type);

    <T extends Entity> T get(Class<T> type, Long id);

    <T extends Entity> void save(T entity);

    <T extends Entity> void delete(T entity);
}
