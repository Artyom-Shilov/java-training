package com.shilov.repository.impl;

import com.shilov.common.exceptions.RepositoryException;
import com.shilov.models.Space;
import com.shilov.repository.SpaceRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListBasedSpaceRepository implements SpaceRepository {

    private static final String SERIALIZATION_FILE_PATH = "src/main/resources/spaces.txt";

    private List<Space> spaces = new ArrayList<>();

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

    @Override
    public void loadSpaces() throws RepositoryException {
        try (FileInputStream fileInputStream = new FileInputStream(SERIALIZATION_FILE_PATH);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            spaces = (ArrayList<Space>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void saveSpaces() throws RepositoryException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(SERIALIZATION_FILE_PATH);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(spaces);
        } catch (IOException e) {
            throw new RepositoryException(e);
        }
    }
}
