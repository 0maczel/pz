package pz.webclient.authorisation.service;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pz.webclient.authorisation.dao.IUserDao;
import pz.webclient.authorisation.model.User;

/**
 * Created by mnawrot on 08/06/16.
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public User createUser(User user) throws ConstraintViolationException {
        return userDao.createUser(user);
    }


    @Override
    public User getUser(String email) {
        return userDao.getUser(email);
    }

    @Override
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

}
