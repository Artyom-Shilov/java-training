package com.shilov.models.builders;

import com.shilov.common.enums.ReservationStatus;
import com.shilov.models.Reservation;
import com.shilov.models.Space;
import com.shilov.models.User;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationBuilder {
    private int id;
    private User customer;
    private Space space;
    private ReservationStatus status;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;

    public ReservationBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public ReservationBuilder setCustomer(User customer) {
        this.customer = customer;
        return this;
    }

    public ReservationBuilder setSpace(Space space) {
        this.space = space;
        return this;
    }

    public ReservationBuilder setStatus(ReservationStatus status) {
        this.status = status;
        return this;
    }

    public ReservationBuilder setStartTime(LocalTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public ReservationBuilder setEndTime(LocalTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public ReservationBuilder setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Reservation createReservation() {
        return new Reservation(id, customer, space, status, startTime, endTime, date);
    }
}