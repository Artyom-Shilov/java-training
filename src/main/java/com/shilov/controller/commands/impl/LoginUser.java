package com.shilov.controller.commands.impl;

import com.shilov.controller.commands.Command;

import java.util.Scanner;

public abstract class LoginUser implements Command {

    protected final static String ENTER_LOGIN_MESSAGE = "Please, enter your login: ";

    protected String getLogin(Scanner scanner) {
        System.out.println(ENTER_LOGIN_MESSAGE);
        return scanner.nextLine().trim();
    }
}
