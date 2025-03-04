package com.shilov.controllers;

import com.shilov.controllers.requests.MakeCurrentUserReservationRequest;
import com.shilov.controllers.responses.Response;

public interface ReservationController {

    Response getAllReservations();
    Response makeCurrentUserReservation(MakeCurrentUserReservationRequest request);
    Response getCurrentUserReservations();
    Response cancelReservation(String reservationId);
    Response saveReservations();
    Response initReservations();
}
