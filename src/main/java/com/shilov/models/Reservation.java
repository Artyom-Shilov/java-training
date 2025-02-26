package com.shilov.models;

public class Reservation {

    private Long id;
    private User user;
    private Space space;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
