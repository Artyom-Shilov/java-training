package com.shilov.repository.impl;

import com.shilov.common.enums.SpaceType;
import com.shilov.common.exceptions.RepositoryException;
import com.shilov.models.Space;
import com.shilov.repository.SpaceRepository;

import java.util.ArrayList;
import java.util.List;

public class ListBasedSpaceRepository implements SpaceRepository {

    private final List<Space> spaces;

    public ListBasedSpaceRepository() {
        spaces = initSpaces();
    }

    private List<Space> initSpaces() {
        List<Space> result = new ArrayList<>();
        result.add(new Space(SpaceType.ROOM, 1));
        result.add(new Space(SpaceType.PRIVATE, 1));
        result.add(new Space(SpaceType.OPEN, 2));
        return result;
    }

    @Override
    public List<Space> getAllSpaces() {
        return spaces;
    }

    @Override
    public Space getSpaceById(String id) throws RepositoryException {
        return spaces.stream().filter(space -> space.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RepositoryException("Space not found"));
    }

    @Override
    public void addSpace(Space space) {
        spaces.add(space);
    }

    @Override
    public void deleteSpace(String id) throws RepositoryException {
        Space spaceToDelete = spaces.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst().orElseThrow(() -> new RepositoryException("Failed to find space to delete"));
        spaces.remove(spaceToDelete);
    }

    @Override
    public void updateSpace(String id, Space newData) throws RepositoryException {
        Space spaceToUpdate = spaces.stream().filter(s -> s.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RepositoryException("Failed to find space to update by id: " + id));
        spaces.set(spaces.indexOf(spaceToUpdate), newData);
    }
}
