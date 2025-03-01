package com.shilov.services;

import com.shilov.common.enums.UserRole;
import com.shilov.models.User;

public interface UserService {

    void signIn(String login, UserRole role);
    void signOut();
    User getCurrentUser();
}
