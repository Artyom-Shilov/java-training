package com.shilov.repository.impl;

import com.shilov.common.connectivity.DatabaseConnectionManager;
import com.shilov.common.exceptions.RepositoryException;
import com.shilov.models.Reservation;
import com.shilov.models.ReservationDateTime;
import com.shilov.models.User;
import com.shilov.repository.ReservationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class JpaReservationRepository implements ReservationRepository {
    @Override
    public List<Reservation> getAllReservations() {
        try(EntityManager em = DatabaseConnectionManager.getEntityManager()) {
            return em.createQuery("SELECT r FROM Reservation r", Reservation.class).getResultList();
        }
    }

    @Override
    public Optional<Reservation> getReservationById(Long id) {
        try(EntityManager em = DatabaseConnectionManager.getEntityManager()) {
            Reservation reservation = em.find(Reservation.class, id);
            return reservation != null ? Optional.of(reservation) : Optional.empty();
        }
    }

    @Override
    public List<Reservation> getReservationsByCustomer(User customer) {
        try(EntityManager em = DatabaseConnectionManager.getEntityManager()) {
            return em.createQuery("SELECT r FROM Reservation r WHERE r.customer.id = customer.id",Reservation.class)
                    .getResultList();
        }
    }

    @Override
    public Long addReservation(Reservation reservation) throws RepositoryException {
        EntityTransaction transaction = null;
        try(EntityManager em = DatabaseConnectionManager.getEntityManager()) {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(reservation);
            transaction.commit();
            return reservation.getId();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e);
        }
    }

    @Override
    public void updateReservation(Long id, Reservation newData) throws RepositoryException {
        EntityTransaction transaction = null;
        try(EntityManager em = DatabaseConnectionManager.getEntityManager()) {
            transaction = em.getTransaction();
            transaction.begin();
            Reservation reservation = em.find(Reservation.class, id);
            if (reservation != null) {
                newData.setId(id);
                em.merge(newData);
                transaction.commit();
            } else {
                transaction.rollback();
                throw new RepositoryException("Reservation not found");
            }
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Reservation> getReservationsIntersectedWithTimeRange(ReservationDateTime dateTimeForIntersection) throws RepositoryException {
        try(EntityManager em = DatabaseConnectionManager.getEntityManager()) {
            String reservationIntersectionQuery = """
                    SELECT r FROM Reservation r WHERE r.reservationDateTime.date = :date
                    AND r.reservationDateTime.startTime <= :startLimit
                    AND r.reservationDateTime.endTime >= :endLimit
                    """;
           return em.createQuery(reservationIntersectionQuery, Reservation.class)
                    .setParameter("date", dateTimeForIntersection.getDate())
                    .setParameter("startLimit", dateTimeForIntersection.getEndTime())
                    .setParameter("endLimit", dateTimeForIntersection.getStartTime())
                    .getResultList();
        }
    }
}
