package com.shilov.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class ReservationDateTime {

    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;

    public ReservationDateTime() {}

    public ReservationDateTime(LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

    public ReservationDateTime(String date, String startTime, String endTime) {
        this(LocalDate.parse(date), LocalTime.parse(startTime), LocalTime.parse(endTime));
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationDateTime that = (ReservationDateTime) o;
        return Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, date);
    }

    @Override
    public String toString() {
        return "ReservationTime{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", date=" + date +
                '}';
    }
}
