package com.shilov.repository;

import com.shilov.models.User;

public interface UserRepository {

    User getCurrentUser();
    void setCurrentUser(User user);
}
