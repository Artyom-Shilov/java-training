package com.shilov.services.impl;

import com.shilov.common.enums.UserRole;
import com.shilov.common.exceptions.RepositoryException;
import com.shilov.common.exceptions.ServiceException;
import com.shilov.models.User;
import com.shilov.repository.CurrentUserRepository;
import com.shilov.repository.GlobalUserRepository;
import com.shilov.services.AuthService;

import java.util.Optional;

public class AuthServiceImpl implements AuthService {

    private final CurrentUserRepository currentUserRepository;
    private final GlobalUserRepository globalUserRepository;

    public AuthServiceImpl(CurrentUserRepository currentUserRepository,
                           GlobalUserRepository globalUserRepository) {
        this.currentUserRepository = currentUserRepository;
        this.globalUserRepository = globalUserRepository;
    }

    @Override
    public void signIn(String login, UserRole role) throws ServiceException{
        try {
            User user;
            Optional<User> searchResult = globalUserRepository.findUserByLogin(login);
            if (searchResult.isEmpty()) {
                user = new User(login, role);
                Long id = globalUserRepository.saveUser(user);
                user.setId(id);
            } else {
                user = searchResult.get();
            }
            currentUserRepository.setCurrentUser(user);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void signOut() throws ServiceException {
        try {
            currentUserRepository.setCurrentUser(null);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getCurrentUser() throws ServiceException{
        try {
            return currentUserRepository.getCurrentUser().orElseThrow(() -> new ServiceException("No user is logged in"));
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
