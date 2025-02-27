package com.shilov.repository;

import com.shilov.common.enums.SpaceType;
import com.shilov.common.exceptions.RepositoryException;
import com.shilov.models.Reservation;
import com.shilov.models.Space;
import com.shilov.models.builders.SpaceBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListBasedSpaceRepository implements SpaceRepository {

    private final List<Space> spaces;

    public ListBasedSpaceRepository() {
        spaces = generateInitialSpaces();
    }

    private List<Space> generateInitialSpaces() {
        List<Space> result = new ArrayList<>();
        final  Random random = new Random();
        SpaceType[] spaceTypes = SpaceType.values();
        for (int i = 1; i < generateRandomIntInRange(6, 12, random); i++) {
            result.add(new SpaceBuilder()
                    .setId(i)
                    .setType(spaceTypes[generateRandomIntInRange(0, spaceTypes.length, random)])
                    .setIsAvailable(true)
                    .setPrice(generateRandomIntInRange(10, 100, random))
                    .createSpace());
        }
        return result;
    }

   private int generateRandomIntInRange(int min, int max, Random random) {
        return random.nextInt(max - min) + min;
    }

    @Override
    public List<Space> getAvailableSpaces() {
        return spaces.stream().filter(Space::isAvailable).toList();
    }

    @Override
    public void addSpace(Space space) throws RepositoryException {
        spaces.add(space);
    }

    @Override
    public void deleteSpace(Space space) throws RepositoryException {
        Space spaceToDelete = spaces.stream()
                .filter(r -> r.getId() == space.getId())
                .findFirst().orElseThrow(() -> new RepositoryException("Failed to find space to delete by id"));
        spaces.remove(spaceToDelete);
    }

    @Override
    public void updateSpace(Space space) throws RepositoryException {

    }
}
