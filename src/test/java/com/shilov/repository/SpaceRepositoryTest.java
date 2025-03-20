package com.shilov.repository;

import com.shilov.common.enums.SpaceType;
import com.shilov.common.exceptions.RepositoryException;
import com.shilov.common.properties.PropertyReader;
import com.shilov.models.Space;
import com.shilov.repository.impl.ListBasedSpaceRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class SpaceRepositoryTest {

    private SpaceRepository spaceRepository;

    @BeforeEach
    void setUp() throws RepositoryException {
        spaceRepository = new ListBasedSpaceRepository();
        spaceRepository.saveSpaces();
    }

    @Test
    void getAllSpaces_shouldReturnSpacesWhenFound() throws RepositoryException {
        System.out.println(spaceRepository);
        List<Space> spacesToAdd = new ArrayList<>(){};
        spacesToAdd.add(new Space());
        spacesToAdd.add(new Space());
        for (Space space : spacesToAdd) {
            spaceRepository.addSpace(space);
        }

        List<Space> result = spaceRepository.getAllSpaces();

        assertEquals(spacesToAdd, result);
    }

    @Test
    void getAllSpaces_shouldReturnEmptyListWhenNotFound() throws RepositoryException {
        List<Space> result = spaceRepository.getAllSpaces();

        assertTrue(result.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideSpaces")
    void getSpaceById_shouldReturnSpaceWhenFound(Space space) throws RepositoryException {
        spaceRepository.addSpace(space);

        Optional<Space> result = spaceRepository.getSpaceById(space.getId());

        assertTrue(result.isPresent());
    }

    private static Stream<Space> provideSpaces() {
        return Stream.of(new Space(), new Space(), new Space());
    }

    @Test
    void getSpaceById_shouldReturnEmptyOptionalWhenNotFound() throws RepositoryException {
        Optional<Space> result = spaceRepository.getSpaceById("");

        assertTrue(result.isEmpty());
    }

    @Test
    void addSpace_shouldAddSpaceToList() throws RepositoryException {
        Space space = new Space();

        spaceRepository.addSpace(space);

        assertFalse(spaceRepository.getAllSpaces().isEmpty());
    }

    @Test
    void deleteSpace_shouldRemoveSpaceWhenFound() throws RepositoryException {
        Space spaceToAdd = new Space();
        spaceRepository.addSpace(spaceToAdd);

        spaceRepository.deleteSpace(spaceToAdd.getId());

        assertEquals(0, spaceRepository.getAllSpaces().size());
    }

    @Test
    void deleteSpace_shouldThrowRepositoryExceptionWhenNotFound() {
        assertThrows(RepositoryException.class, () -> spaceRepository.deleteSpace(""));
    }

    @Test
    void saveSpace_shouldPersistSpaceWhenStorageExists() throws RepositoryException {
        spaceRepository.addSpace(new Space());

        spaceRepository.saveSpaces();
        spaceRepository.loadSpaces();

        assertFalse(spaceRepository.getAllSpaces().isEmpty());
    }

    @Test
    void saveSpace_shouldThrowRepositoryExceptionWhenStorageDoesNotExist() {
        try(MockedStatic<PropertyReader> mockedStatic = Mockito.mockStatic(PropertyReader.class)) {
            mockedStatic.when(() -> PropertyReader.getProperty(PropertyReader.SPACE_STORAGE_PATH)).thenReturn("");

            Assert.assertThrows(RepositoryException.class, () -> spaceRepository.saveSpaces());
        }
    }

    @Test
    void updateSpace_shouldUpdateSpaceWhenFound() throws RepositoryException {
        Space initSpace = new Space();
        String id = initSpace.getId();
        initSpace.setType(SpaceType.ROOM);
        spaceRepository.addSpace(initSpace);
        Space update = new Space();
        update.setId(id);
        update.setType(SpaceType.PRIVATE);

        spaceRepository.updateSpace(id, update);
        Optional<Space> updatedOptional = spaceRepository.getSpaceById(id);

        assertTrue(updatedOptional.isPresent());
        assertNotEquals(initSpace, updatedOptional.get());
        assertEquals(update, updatedOptional.get());
    }

    @Test
    void updateSpace_shouldThrowRepositoryExceptionWhenSpaceNotFound() {
        assertThrows(RepositoryException.class, () -> spaceRepository.updateSpace("", new Space()));
    }
}
