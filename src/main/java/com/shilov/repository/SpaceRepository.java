package com.shilov.repository;

import com.shilov.models.Space;

import java.util.List;

public interface SpaceRepository {

    Space getSpace(int spaceId);
    List<Space> getAllSpaces();
    List<Space> getAvailableSpaces();
    void deleteSpace(int spaceId);
}
