package com.shilov.repository;

import com.shilov.common.exceptions.RepositoryException;
import com.shilov.models.Space;

import java.util.List;
import java.util.Optional;

public interface SpaceRepository {

    List<Space> getAllSpaces() throws RepositoryException;
    Optional<Space> getSpaceById(Long id) throws RepositoryException;
    Long addSpace(Space space) throws RepositoryException;
    void deleteSpace(Long id) throws RepositoryException;
    void updateSpace(Long id, Space newData) throws RepositoryException;
    void loadSpaces() throws RepositoryException;
    void saveSpaces() throws RepositoryException;
    void deleteAllSpaces() throws RepositoryException;
}
