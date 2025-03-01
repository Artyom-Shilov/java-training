package com.shilov.controller.commands.impl;

import com.shilov.common.enums.UserRole;
import com.shilov.common.exceptions.AuthenticationException;
import com.shilov.services.UserService;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class LoginAsAdmin extends LoginUser {

    private final UserService userService;

    public LoginAsAdmin(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute() throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            userService.signIn(getLogin(scanner), UserRole.ADMIN);
        } catch (NoSuchElementException e) {
            throw new AuthenticationException(e);
        }
    }
}
