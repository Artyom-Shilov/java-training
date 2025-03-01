package com.shilov.controller.commands.impl;

import com.shilov.controller.commands.Command;

public class InitApp implements Command {

    private final static String HELLO_MESSAGE = "Welcome to space reservation app!";

    @Override
    public void execute() {
        System.out.println(HELLO_MESSAGE);
    }
}
