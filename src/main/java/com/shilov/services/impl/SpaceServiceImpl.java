package com.shilov.services.impl;

import com.shilov.common.enums.ReservationStatus;
import com.shilov.common.exceptions.RepositoryException;
import com.shilov.common.exceptions.ServiceException;
import com.shilov.models.Reservation;
import com.shilov.models.ReservationDateTime;
import com.shilov.models.Space;
import com.shilov.repository.ReservationRepository;
import com.shilov.repository.SpaceRepository;
import com.shilov.services.SpaceService;

import java.util.List;

public class SpaceServiceImpl implements SpaceService {

    private final SpaceRepository spaceRepository;
    private final ReservationRepository reservationRepository;

    public SpaceServiceImpl(SpaceRepository spaceRepository, ReservationRepository reservationRepository) {
        this.spaceRepository = spaceRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void addNewSpace(Space space) throws ServiceException {
        try {
            spaceRepository.addSpace(space);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteSpace(String id) throws ServiceException {
        try {
            spaceRepository.deleteSpace(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateSpace(String id, Space space) throws ServiceException {
        try {
            spaceRepository.updateSpace(id, space);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Space> getAvailableForReservationSpaces(ReservationDateTime reservationDateTime) throws ServiceException {
        try {
            List<Space> spacesWithDateTimeIntersection = reservationRepository
                    .getReservationsIntersectedWithTimeRange(reservationDateTime)
                    .stream()
                    .filter(r -> r.getStatus() == ReservationStatus.ACTIVE)
                    .map(Reservation::getSpace).toList();
            List<Space> spaces = spaceRepository.getAllSpaces();
            spaces.removeAll(spacesWithDateTimeIntersection);
            return spaces;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Space getSpaceById(String id) throws ServiceException {
        try {
            return spaceRepository.getSpaceById(id).orElseThrow(() -> new ServiceException("Space not found"));
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void saveSpaces() throws ServiceException {
        try {
            spaceRepository.saveSpaces();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void initSpaces() throws ServiceException {
        try {
            spaceRepository.loadSpaces();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
