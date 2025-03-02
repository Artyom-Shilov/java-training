package com.shilov.controllers;

import com.shilov.common.enums.ResponseStatus;
import com.shilov.common.enums.UserRole;
import com.shilov.common.exceptions.ServiceException;
import com.shilov.controllers.responses.Response;
import com.shilov.services.AuthService;

public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public Response loginAsAdmin(String login) {
        String successMessage = "Admin login operation successful";
        Response response;
        try {
            authService.signIn(login, UserRole.ADMIN);
            response = new Response(ResponseStatus.SUCCESS, successMessage);
        } catch (ServiceException e) {
            response = new Response(ResponseStatus.FAILURE, e.getMessage());
        }
        return response;
    }

    public Response loginAsCustomer(String login) {
        String successMessage = "Customer login operation successful";
        Response response;
        try {
            authService.signIn(login, UserRole.CUSTOMER);
            response = new Response(ResponseStatus.SUCCESS, successMessage);
        } catch (ServiceException e) {
            response = new Response(ResponseStatus.FAILURE, e.getMessage());
        }
        return response;
    }

    public Response logout() {
        String successMessage = "Logout operation successful";
        Response response;
        try {
            authService.signOut();
            response = new Response(ResponseStatus.SUCCESS, successMessage);
        } catch (ServiceException e) {
            response = new Response(ResponseStatus.FAILURE, e.getMessage());
        }
        return response;
    }
}
