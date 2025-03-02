package com.shilov.repository;

import com.shilov.common.exceptions.RepositoryException;
import com.shilov.models.User;

public interface UserRepository {

    User getCurrentUser() throws RepositoryException;
    void setCurrentUser(User user) throws RepositoryException;
}
