package pz.monitor.service.example;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pz.monitor.db.AbstactMonitorDao;
import pz.monitor.db.Example;

//TODO remove
/**
 * testowe dao
 * 
 * @author Invader
 *
 */
@Component
@org.springframework.transaction.annotation.Transactional
public class ExampleDao extends AbstactMonitorDao<Example>
{
    @Autowired
    private SessionFactory sessionFactory;

    public void store()
    {
        Example e = new Example();
        e.setName("pies");
        e.setSurname("pies");
        sessionFactory.getCurrentSession().persist(e);
    }

    public Example get()
    {
        return sessionFactory.getCurrentSession().get(Example.class, 1L);

    }
}
