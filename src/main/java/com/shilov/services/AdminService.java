package com.shilov.services;

import com.shilov.models.Reservation;
import com.shilov.models.Space;

import java.util.List;

public interface AdminService {

    void addNewSpace(Space space);
    void deleteSpace(Space space);
    void updateSpace(Space space);
    List<Reservation> browseAllReservations();
}
