package com.shilov.services.impl;

import com.shilov.common.enums.UserRole;
import com.shilov.models.User;
import com.shilov.repository.UserRepository;
import com.shilov.services.UserService;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void signIn(String login, UserRole role) {
        userRepository.setCurrentUser(new User(login, role));
    }

    @Override
    public void signOut() {
        userRepository.setCurrentUser(null);
    }

    @Override
    public User getCurrentUser() {
        return userRepository.getCurrentUser();
    }
}
