package com.shilov.services;

import com.shilov.models.Reservation;
import com.shilov.models.Space;
import com.shilov.models.User;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {


    @Override
    public void cancelReservation(Reservation reservation, User customer) {

    }

    @Override
    public void reserveSpace(Space space, User customer) {

    }

    @Override
    public List<Space> browseAvailableSpaces() {
        return List.of();
    }

    @Override
    public List<Reservation> browseUserReservations(User customer) {
        return List.of();
    }
}
