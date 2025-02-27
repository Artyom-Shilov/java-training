package com.shilov.repository;

import com.shilov.common.exceptions.RepositoryException;
import com.shilov.models.Reservation;
import com.shilov.models.User;

import java.util.ArrayList;
import java.util.List;

public class ListBasedReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> getAllReservations() {
        return reservations;
    }

    @Override
    public List<Reservation> getReservationsByCustomer(User customer) throws RepositoryException {
        return reservations.stream().filter((r) -> r.getCustomer().equals(customer)).toList();
    }

    @Override
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    @Override
    public void updateReservation(Reservation reservation) throws RepositoryException {

    }
}
