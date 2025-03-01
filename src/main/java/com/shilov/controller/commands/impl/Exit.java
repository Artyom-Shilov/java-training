package com.shilov.controller.commands.impl;

import com.shilov.controller.commands.Command;
import com.shilov.services.UserService;

public class Exit implements Command {

    private static final String EXIT_MESSAGE = "See you next time, " + AppState.currentUser.getLogin() + " !";

    private final UserService userService;

    public Exit(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute() {
        System.out.println(EXIT_MESSAGE);
        userService.signOut();
    }
}
