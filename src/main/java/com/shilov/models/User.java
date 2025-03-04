package com.shilov.models;

import com.shilov.common.enums.UserRole;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private String login;
    private UserRole userRole;

    public User() {}

    public User(String login, UserRole userRole) {
        this.login = login;
        this.userRole = userRole;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserRole getRole() {
        return userRole;
    }

    public void setRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) && userRole == user.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, userRole);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", role=" + userRole +
                '}';
    }
}
