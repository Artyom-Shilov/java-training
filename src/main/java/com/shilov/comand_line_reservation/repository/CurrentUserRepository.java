package com.shilov.repository;

import com.shilov.common.exceptions.RepositoryException;
import com.shilov.models.User;

import java.util.Optional;

public interface CurrentUserRepository {

    Optional<User> getCurrentUser() throws RepositoryException;
    void setCurrentUser(User user) throws RepositoryException;
}
