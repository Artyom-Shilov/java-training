package com.shilov.repository;

import com.shilov.common.exceptions.RepositoryException;
import com.shilov.models.Reservation;
import com.shilov.models.User;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> getAllReservations() throws  RepositoryException;
    List<Reservation> getReservationsByCustomer(User customer) throws  RepositoryException;
    void addReservation(Reservation reservation) throws  RepositoryException;
    void updateReservation(Reservation reservation) throws  RepositoryException;
}
