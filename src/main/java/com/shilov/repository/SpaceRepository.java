package com.shilov.repository;

import com.shilov.common.exceptions.RepositoryException;
import com.shilov.models.Space;

import java.util.List;

public interface SpaceRepository {

    List<Space> getAllSpaces() throws RepositoryException;
    Space getSpaceById(String id) throws RepositoryException;
    void addSpace(Space space) throws RepositoryException;
    void deleteSpace(String id) throws RepositoryException;
    void updateSpace(String id, Space newData) throws RepositoryException;
}
