package pz.webclient.authorisation.dao;

import org.hibernate.exception.ConstraintViolationException;
import pz.webclient.authorisation.model.User;

/**
 * Created by mnawrot on 08/06/16.
 */
public interface IUserDao {
    User createUser(User user) throws ConstraintViolationException;

    User getUser(String email);

    User getUserById(long id);

    User getUserWithPassword(String email, String password);
}
