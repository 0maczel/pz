package pz.monitor.db;

import static java.util.Objects.requireNonNull;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Bazowe DAO dla Encji.
 * 
 * @author Invader
 *
 * @param <T>
 *            typ encji, na której operuje DAO
 */
public abstract class AbstactMonitorDao<T>
{
    @Autowired
    protected SessionFactory sessionFactory;

    public T get(Class<T> clazz, Long id)
    {
        requireNonNull(id);
        return getCurrentSession().get(clazz, id);
    }

    protected Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }
}
