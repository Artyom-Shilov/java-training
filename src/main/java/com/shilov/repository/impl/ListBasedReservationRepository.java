package com.shilov.repository.impl;

import com.shilov.common.exceptions.RepositoryException;
import com.shilov.models.Reservation;
import com.shilov.models.ReservationDateTime;
import com.shilov.models.User;
import com.shilov.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ListBasedReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> getAllReservations() {
        return reservations;
    }

    @Override
    public Reservation getReservationById(String id) throws RepositoryException {
        return reservations.stream().filter(r -> r.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RepositoryException("Reservation not found"));
    }

    @Override
    public List<Reservation> getReservationsByCustomer(User customer) throws RepositoryException {
        return reservations.stream().filter(r -> r.getCustomer().equals(customer)).toList();
    }

    @Override
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    @Override
    public void updateReservation(String id, Reservation newData) throws RepositoryException {
        Reservation reservationToUpdate = reservations.stream().filter(r -> r.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RepositoryException("Failed to find reservation to update by id: " + id));
        reservations.set(reservations.indexOf(reservationToUpdate), newData);
    }

    @Override
    public List<Reservation> getReservationsIntersectedWithTimeRange(ReservationDateTime dateTimeForIntersection) {
        Predicate<? super Reservation> timeIntersectionPredicate = reservation ->
                !dateTimeForIntersection.getEndTime().isBefore(reservation.getReservationDateTime().getStartTime()) &&
                ! dateTimeForIntersection.getStartTime().isAfter(reservation.getReservationDateTime().getEndTime());
        return reservations.stream()
                .filter(r -> r.getReservationDateTime().getDate().equals(dateTimeForIntersection.getDate()))
                .filter(timeIntersectionPredicate)
                .toList();
    }
}
