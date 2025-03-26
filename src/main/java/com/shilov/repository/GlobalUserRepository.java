package com.shilov.repository;

import com.shilov.common.exceptions.RepositoryException;
import com.shilov.models.User;

import java.util.Optional;

public interface GlobalUserRepository {

    Long saveUser(User user) throws RepositoryException;
    Optional<User> findUserById(Long id) throws RepositoryException;
    Optional<User> findUserByLogin(String login) throws RepositoryException;
}
