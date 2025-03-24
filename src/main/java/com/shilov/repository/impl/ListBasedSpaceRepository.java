package com.shilov.repository.impl;

import com.shilov.common.exceptions.RepositoryException;
import com.shilov.common.properties.PropertyReader;
import com.shilov.models.Space;
import com.shilov.repository.SpaceRepository;

import java.io.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListBasedSpaceRepository implements SpaceRepository {

    private List<Space> spaces = new ArrayList<>();

    @Override
    public List<Space> getAllSpaces() {
        return spaces;
    }

    @Override
    public Optional<Space> getSpaceById(Long id) {
        return spaces.stream().filter(space -> space.getId().equals(id)).findFirst();
    }

    @Override
    public Long addSpace(Space space) {
        long generatedId = Math.abs(new SecureRandom().nextLong());
        space.setId(generatedId);
        spaces.add(space);
        return generatedId;
    }

    @Override
    public void deleteSpace(Long id) throws RepositoryException {
        Space spaceToDelete = spaces.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst().orElseThrow(() -> new RepositoryException("Failed to find space to delete"));
        spaces.remove(spaceToDelete);
    }

    @Override
    public void updateSpace(Long id, Space newData) throws RepositoryException {
        Space spaceToUpdate = spaces.stream().filter(s -> s.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RepositoryException("Failed to find space to update by id: " + id));
        spaces.set(spaces.indexOf(spaceToUpdate), newData);
    }

    @Override
    public void loadSpaces() throws RepositoryException {
        try (FileInputStream fileInputStream = new FileInputStream(getSpacesStoragePath());
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            spaces = (ArrayList<Space>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void saveSpaces() throws RepositoryException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(getSpacesStoragePath());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(spaces);
        } catch (IOException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void deleteAllSpaces() throws RepositoryException {
        spaces = new ArrayList<>();
        saveSpaces();
    }

    private String getSpacesStoragePath() throws IOException {
        return PropertyReader.getProperty(PropertyReader.SPACE_STORAGE_PATH).trim()
                .replace('/', File.separatorChar);
    }
}
