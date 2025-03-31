package com.shilov.services;

import com.shilov.common.enums.UserRole;
import com.shilov.common.exceptions.ServiceException;
import com.shilov.models.User;

public interface AuthService {

    void signIn(String login, UserRole role) throws ServiceException;
    void signOut() throws ServiceException;
    User getCurrentUser() throws ServiceException;
}
