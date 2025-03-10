package com.shilov.controllers.impl;

import com.shilov.common.enums.ResponseStatus;
import com.shilov.common.exceptions.ReservationDateTimeFormatException;
import com.shilov.common.exceptions.ServiceException;
import com.shilov.controllers.ReservationController;
import com.shilov.controllers.requests.MakeCurrentUserReservationRequest;
import com.shilov.controllers.requests.ReservationDateTimeInput;
import com.shilov.controllers.responses.Response;
import com.shilov.models.Reservation;
import com.shilov.models.ReservationDateTime;
import com.shilov.services.ReservationService;
import com.shilov.services.AuthService;
import com.shilov.services.SpaceService;

import java.util.List;

public class ReservationControllerImpl implements ReservationController {

    private final ReservationService reservationService;
    private final SpaceService spaceService;
    private final AuthService authService;

    public ReservationControllerImpl(ReservationService reservationService, SpaceService spaceService,
                                     AuthService authService) {
        this.reservationService = reservationService;
        this.spaceService = spaceService;
        this.authService = authService;
    }

    public Response getAllReservations() {
        StringBuilder output = new StringBuilder();
        List<Reservation> allReservations;
        Response response;
        try {
            allReservations = reservationService.getAllReservations();
            for (int i = 0; i < allReservations.size(); i++) {
                output.append(i + 1).append(": ").append(allReservations.get(i)).append("\n");
            }
            if (output.isEmpty()) {
                String noReservationsMessage = "System does not have any reservations";
                output.append(noReservationsMessage);
            }
            response = new Response(ResponseStatus.SUCCESS, output.toString());
        } catch (ServiceException e) {
            response = new Response(ResponseStatus.FAILURE, e.getMessage());
        }
        return response;
    }

    public Response makeCurrentUserReservation(MakeCurrentUserReservationRequest request) {
        Response response;
        String successMessage = "Reservation was successfully made";
        ReservationDateTimeInput dateTimeInput = request.getReservationDateTimeInput();
        try {
            reservationService.makeReservation(
                    spaceService.getSpaceById(request.getSpaceId()),
                    authService.getCurrentUser(),
                    new ReservationDateTime(dateTimeInput.getDate(), dateTimeInput.getStartTime(),
                            dateTimeInput.getEndTime())
            );
            response = new Response(ResponseStatus.SUCCESS, successMessage);
        } catch (ServiceException | ReservationDateTimeFormatException e) {
            response = new Response(ResponseStatus.FAILURE, e.getMessage());
        }
        return response;
    }

    public Response getCurrentUserReservations() {
        Response response;
        StringBuilder output = new StringBuilder();
        List<Reservation> currentUserReservations;
        try {
            currentUserReservations = reservationService.getUserReservations(authService.getCurrentUser());
            for (int i = 0; i < currentUserReservations.size(); i++) {
                output.append(i + 1).append(": ").append(currentUserReservations.get(i)).append("\n");
            }
            if (output.isEmpty()) {
                String noReservationMessage = "User does not have any reservations";
                output.append(noReservationMessage);
            }
            response = new Response(ResponseStatus.SUCCESS, output.toString());
        } catch (ServiceException e) {
            response = new Response(ResponseStatus.FAILURE, e.getMessage());
        }
        return response;
    }

    public Response cancelReservation(String reservationId) {
        String successMessage = "Reservation was successfully cancelled";
        Response response;
        try {
            reservationService.cancelReservation(
                    reservationService.getReservationById(reservationId),
                    authService.getCurrentUser());
            response = new Response(ResponseStatus.SUCCESS, successMessage);
        } catch (ServiceException e) {
            response = new Response(ResponseStatus.FAILURE, e.getMessage());
        }
        return response;
    }

    public Response saveReservations() {
        String successMessage = "Reservations were successfully saved";
        Response response;
        try {
            reservationService.saveReservations();
            response = new Response(ResponseStatus.SUCCESS, successMessage);
        } catch (ServiceException e) {
            response = new Response(ResponseStatus.FAILURE, e.getMessage());
        }
        return response;
    }

    public Response initReservations() {
        String successMessage = "Reservations were successfully loaded";
        Response response;
        try {
            reservationService.initReservations();
            response = new Response(ResponseStatus.SUCCESS, successMessage);
        } catch (ServiceException e) {
            response = new Response(ResponseStatus.FAILURE, e.getMessage());
        }
        return response;
    }


}
