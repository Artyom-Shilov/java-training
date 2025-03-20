package com.shilov.repository.impl;

import com.shilov.models.User;
import com.shilov.repository.UserRepository;

import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private User currentUser;


    @Override
    public Optional<User> getCurrentUser() {
        return Optional.ofNullable(currentUser);
    }

    @Override
    public void setCurrentUser(User user) {
        currentUser = user;
    }
}
