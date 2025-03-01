package com.shilov.controller.commands.impl;

import com.shilov.common.enums.UserRole;
import com.shilov.common.exceptions.AuthenticationException;
import com.shilov.services.UserService;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class LoginAsCustomer extends LoginUser {

    private final UserService userService;

    public LoginAsCustomer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute() throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            userService.signIn(getLogin(scanner), UserRole.CUSTOMER);
        } catch (NoSuchElementException e) {
            throw new AuthenticationException(e);
        }
    }
}
