package com.shilov.services;

import com.shilov.common.exceptions.ServiceException;
import com.shilov.models.ReservationDateTime;
import com.shilov.models.Space;

import java.util.List;

public interface SpaceService {

    void addNewSpace(Space space) throws ServiceException;
    void deleteSpace(Space space) throws ServiceException;
    void updateSpace(String id, Space update) throws ServiceException;
    List<Space> getAvailableForReservationSpaces(ReservationDateTime reservationDateTime) throws ServiceException;
}
