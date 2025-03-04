package com.shilov.controllers;

import com.shilov.controllers.responses.Response;

public interface AuthController {

    Response loginAsAdmin(String login);
    Response loginAsCustomer(String login);
    Response logout();
}
