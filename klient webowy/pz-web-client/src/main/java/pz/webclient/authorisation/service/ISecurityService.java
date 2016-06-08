package pz.webclient.authorisation.service;

import pz.webclient.authorisation.model.User;

/**
 * Created by mnawrot on 08/06/16.
 */
public interface ISecurityService {
    User login(String email, String password);

    void logout();

    User getLoggedUser();
}
