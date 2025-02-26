package com.shilov.repository;

import com.shilov.common.exceptions.RepositoryException;
import com.shilov.models.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ListBasedReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    public Reservation getReservation(int reservationId) {
        return null;
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void deleteReservation(int reservationId) throws  RepositoryException{
        Reservation reservationToDelete = reservations.stream()
                .filter(r -> r.getId() == reservationId)
                .findFirst().orElseThrow(() -> new RepositoryException("Failed to find reservation to delete by id"));
        reservations.remove(reservationToDelete);
    }
}
