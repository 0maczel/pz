package pz.webclient.authorisation.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pz.webclient.authorisation.model.User;

import java.util.List;

/**
 * Created by mnawrot on 08/06/16.
 */
@Repository
public class UserDao implements IUserDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session;

    @Override
    public User createUser(User user) throws ConstraintViolationException {
        session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        long id = (long) session.save(user);

        transaction.commit();
        session.close();

        return getUserById(id);
    }

    @Override
    public User getUser(String email) {
        String queryString = "from User u where u.email = :emailParam";

        session = sessionFactory.openSession();

        Query query = session.createQuery(queryString);
        query.setParameter("emailParam", email);

        List<User> queryResult = query.list();

        User result = (queryResult != null && !queryResult.isEmpty()) ? queryResult.get(0) : null;

        session.close();

        return result;
    }

    @Override
    public User getUserById(long id) {
        String queryString = "from User u where u.id = :idParam";

        session = sessionFactory.openSession();

        Query query = session.createQuery(queryString);
        query.setParameter("idParam", id);

        List<User> queryResult = query.list();

        User result = (queryResult != null && !queryResult.isEmpty()) ? queryResult.get(0) : null;

        session.close();

        return result;
    }

    @Override
    public User getUserWithPassword(String email, String password) {
        String queryString = "from User u where u.email = :emailParam";

        session = sessionFactory.openSession();

        Query query = session.createQuery(queryString);
        query.setParameter("emailParam", email);

        List<User> queryResult = query.list();

        User result = (queryResult != null && !queryResult.isEmpty()) ? queryResult.get(0) : null;

        session.close();

        if (result != null) {
            if (!password.equals(result.getPassword()))
                return null;
        }

        return result;
    }


}
