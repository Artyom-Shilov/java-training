package com.shilov.repository.impl;

import com.shilov.models.User;
import com.shilov.repository.CurrentUserRepository;

import java.util.Optional;

public class CurrentUserRepositoryImpl implements CurrentUserRepository {

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
