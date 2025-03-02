package com.shilov.services.impl;

import com.shilov.common.enums.UserRole;
import com.shilov.common.exceptions.RepositoryException;
import com.shilov.common.exceptions.ServiceException;
import com.shilov.models.User;
import com.shilov.repository.UserRepository;
import com.shilov.services.AuthService;

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void signIn(String login, UserRole role) throws ServiceException{
        try {
            userRepository.setCurrentUser(new User(login, role));
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void signOut() throws ServiceException {
        try {
            userRepository.setCurrentUser(null);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getCurrentUser() throws ServiceException{
        try {
            return userRepository.getCurrentUser();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
