package com.shilov.repository;

import com.shilov.common.enums.SpaceType;
import com.shilov.models.Space;
import com.shilov.models.builders.SpaceBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListBasedSpaceRepository implements SpaceRepository {

    private List<Space> spaces;

    public ListBasedSpaceRepository() {
        spaces = new ArrayList<Space>(generateInitialSpaces());
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
    public Space getSpace(int spaceId) {
        return null;
    }

    @Override
    public List<Space> getAllSpaces() {
        return List.of();
    }

    @Override
    public List<Space> getAvailableSpaces() {
        return List.of();
    }

    @Override
    public void deleteSpace(int spaceId) {

    }
}
