package com.shilov.repository;

import com.shilov.common.exceptions.RepositoryException;
import com.shilov.models.Reservation;

import java.util.List;

public interface ReservationRepository {

    Reservation getReservation(int reservationId) throws RepositoryException;
    List<Reservation> getAllReservations() throws  RepositoryException;
    void addReservation(Reservation reservation) throws  RepositoryException;
    void deleteReservation(int reservationId) throws RepositoryException;
}
