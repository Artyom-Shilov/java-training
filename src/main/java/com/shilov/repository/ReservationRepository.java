package com.shilov.repository;

import com.shilov.common.exceptions.RepositoryException;
import com.shilov.models.Reservation;
import com.shilov.models.ReservationDateTime;
import com.shilov.models.User;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    List<Reservation> getAllReservations() throws  RepositoryException;
    Optional<Reservation> getReservationById(Long id) throws  RepositoryException;
    List<Reservation> getReservationsByCustomer(User customer) throws  RepositoryException;
    Long addReservation(Reservation reservation) throws  RepositoryException;
    void updateReservation(Long id, Reservation newData) throws  RepositoryException;
    List<Reservation> getReservationsIntersectedWithTimeRange(ReservationDateTime dateTimeForIntersection) throws  RepositoryException;
}
