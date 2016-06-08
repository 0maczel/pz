package pz.webclient.authorisation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pz.webclient.authorisation.dao.IUserDao;
import pz.webclient.authorisation.model.User;

/**
 * Created by mnawrot on 08/06/16.
 */
@Service
public class SecurityService implements ISecurityService {

    @Autowired
    private IUserDao userDao;

    private User loggedUser;

    @Override
    public User login(String email, String password) {
        loggedUser = userDao.getUserWithPassword(email, password);
        return loggedUser;
    }

    @Override
    public void logout() {
        loggedUser = null;
    }

    @Override
    public User getLoggedUser() {
        return loggedUser;
    }
}
