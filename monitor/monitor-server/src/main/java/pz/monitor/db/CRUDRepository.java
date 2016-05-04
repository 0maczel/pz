package pz.monitor.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pz.monitor.db.entity.Entity;

@Component
public class CRUDRepository implements Repository
{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public <T extends Entity> List<T> all(Class<T> type)
    {
        @SuppressWarnings("unchecked")
        List<T> resultList = createCriteria(type).list();
        return resultList;
    }

    @Override
    public <T extends Entity> T get(Class<T> type, Long id)
    {
        return getSession().get(type, id);
    }

    @Override
    public <T extends Entity> void save(T entity)
    {
        getSession().saveOrUpdate(entity);
    }

    @Override
    public <T extends Entity> void delete(T entity)
    {
        getSession().delete(entity);
    }

    /**
     * Get current thread session.
     * 
     * @return hibernate session
     */
    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Create criteria based on entity class type.
     * 
     * @param clazz
     *            entity class
     * @return criteria
     */
    private <T extends Entity> Criteria createCriteria(Class<T> clazz)
    {
        return sessionFactory.getCurrentSession().createCriteria(clazz);
    }

}
