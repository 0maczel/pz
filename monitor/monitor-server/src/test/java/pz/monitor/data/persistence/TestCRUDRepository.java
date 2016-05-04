package pz.monitor.data.persistence;

import java.util.List;

import org.hibernate.Session;

import pz.monitor.db.Repository;
import pz.monitor.db.entity.Entity;

/**
 * Old CRUDRepository for manual test purposes (moved)
 */
public class TestCRUDRepository implements Repository
{
    private final Session session;

    public TestCRUDRepository(Session session)
    {
        this.session = session;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Entity> List<T> all(Class<T> type)
    {
        return session.createCriteria(type).list();
    }

    @Override
    public <T extends Entity> T get(Class<T> type, Long id)
    {
        return session.get(type, id);
    }

    @Override
    public <T extends Entity> void save(T entity)
    {
        session.saveOrUpdate(entity);
    }

    @Override
    public <T extends Entity> void delete(T entity)
    {
        session.delete(entity);
    }
}
