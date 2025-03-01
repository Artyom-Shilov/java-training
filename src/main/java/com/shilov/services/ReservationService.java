package com.shilov.services;

import com.shilov.common.exceptions.ServiceException;
import com.shilov.models.Reservation;
import com.shilov.models.ReservationDateTime;
import com.shilov.models.Space;
import com.shilov.models.User;

import java.util.List;

public interface ReservationService {

    void cancelReservation(Reservation reservation, User user) throws ServiceException;
    void makeReservation(Space space, User customer, ReservationDateTime reservationDateTime) throws ServiceException;
    List<Reservation> getAllReservations() throws ServiceException;
    List<Reservation> getUserReservations(User customer) throws ServiceException;
}
