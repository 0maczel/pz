package pz.monitor.service.example;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pz.monitor.db.AbstactMonitorDao;
import pz.monitor.db.Repository;
import pz.monitor.db.entity.Example;

/**
 * Zastąpione przez {@link Repository}.
 * 
 * @author Invader
 *
 */
@Deprecated
@Component
public class ExampleDao extends AbstactMonitorDao<Example>
{
    @Autowired
    private SessionFactory sessionFactory;

    public Example store()
    {
        Example e = new Example();
        e.setName(RandomStringUtils.randomAlphabetic(5));
        e.setSurname(RandomStringUtils.randomAlphabetic(5));
        sessionFactory.getCurrentSession().save(e);
        return e;
    }

    public Example get(Long id)
    {
        return sessionFactory.getCurrentSession().get(Example.class, id);

    }
}
