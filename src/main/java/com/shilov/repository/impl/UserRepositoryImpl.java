package com.shilov.repository.impl;

import com.shilov.models.User;
import com.shilov.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {

    private User currentUser;


    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void setCurrentUser(User user) {
        currentUser = user;
    }
}
