package com.shilov.services.impl;

import com.shilov.common.enums.ReservationStatus;
import com.shilov.common.enums.UserRole;
import com.shilov.common.exceptions.RepositoryException;
import com.shilov.common.exceptions.ServiceException;
import com.shilov.models.Reservation;
import com.shilov.models.ReservationDateTime;
import com.shilov.models.Space;
import com.shilov.models.User;
import com.shilov.models.builders.ReservationBuilder;
import com.shilov.repository.ReservationRepository;
import com.shilov.services.ReservationService;

import java.util.List;

public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationBuilder reservationBuilder;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
        reservationBuilder = new ReservationBuilder();
    }

    @Override
    public void cancelReservation(Reservation reservation, User user) throws ServiceException {
        validateUserForReservationCancel(user, reservation);
        reservation.setStatus(ReservationStatus.CANCELLED);
        try {
            reservationRepository.updateReservation(reservation.getId(), reservation);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void validateUserForReservationCancel(User user, Reservation reservation) throws ServiceException {
        if (user.getRole() != UserRole.ADMIN || user.equals(reservation.getCustomer())) {
            throw new ServiceException("User does have rights to cancel the reservation");
        }
    }

    @Override
    public void makeReservation(Space space, User customer, ReservationDateTime reservationDateTime) throws ServiceException {
        Reservation newReservation = reservationBuilder.setSpace(space).setCustomer(customer)
                .setReservationDateTime(reservationDateTime).setStatus(ReservationStatus.ACTIVE).createReservation();
        validateNewReservationTime(newReservation);
        try {
            reservationRepository.addReservation(newReservation);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private void validateNewReservationTime(Reservation newReservation) throws ServiceException {
        List<Space> spacesWithDateTimeIntersection;
        try {
            spacesWithDateTimeIntersection = reservationRepository
                    .getReservationsIntersectedWithTimeRange(newReservation.getReservationDateTime())
                    .stream().map(Reservation::getSpace).toList();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        if (spacesWithDateTimeIntersection.contains(newReservation.getSpace())) {
            throw new ServiceException("Failed to make a reservation due to time conflict");
        }
    }

    @Override
    public List<Reservation> getAllReservations() throws ServiceException {
        try {
            return reservationRepository.getAllReservations();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Reservation> getUserReservations(User customer) throws ServiceException {
        try {
            return  reservationRepository.getReservationsByCustomer(customer);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Reservation getReservationById(String reservationId) throws ServiceException {
        try {
            return reservationRepository.getReservationById(reservationId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
