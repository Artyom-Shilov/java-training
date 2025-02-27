package com.shilov.services;

import com.shilov.models.Reservation;
import com.shilov.models.Space;
import com.shilov.models.User;

import java.util.List;

public interface CustomerService {

    void cancelReservation(Reservation reservation, User customer);
    void reserveSpace(Space space, User customer);
    List<Space> browseAvailableSpaces();
    List<Reservation> browseUserReservations(User customer);
}
