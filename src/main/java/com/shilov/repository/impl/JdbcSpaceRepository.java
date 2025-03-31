package com.shilov.repository.impl;

import com.shilov.common.connectivity.DatabaseConnectionManager;
import com.shilov.common.enums.SpaceType;
import com.shilov.common.exceptions.RepositoryException;
import com.shilov.models.Space;
import com.shilov.repository.SpaceRepository;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcSpaceRepository implements SpaceRepository {

    @Override
    public List<Space> getAllSpaces() throws RepositoryException {
        String selectAllSpaces = "SELECT id, type, hourly_price  FROM spaces";
        try (Connection connection = DatabaseConnectionManager.getJdbcConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(selectAllSpaces)) {
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                List<Space> result = new ArrayList<>();
                while (resultSet.next()) {
                    result.add(formSpace(resultSet));
                }
                return result;
            }
        } catch (IOException | SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Optional<Space> getSpaceById(Long id) throws RepositoryException {
        String selectSpaceById = "SELECT id, type, hourly_price FROM spaces WHERE id = ?";
        try (Connection connection = DatabaseConnectionManager.getJdbcConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(selectSpaceById)) {
                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();
                Optional<Space> result = Optional.empty();
                if (resultSet.next()) {
                    result = Optional.of(formSpace(resultSet));
                }
                return result;
            }
        } catch (IOException | SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Long addSpace(Space space) throws RepositoryException {
        String insertSpace = "INSERT INTO spaces (type, hourly_price) VALUES (?, ?)";
        try (Connection connection = DatabaseConnectionManager.getJdbcConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(insertSpace, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, space.getType().name());
                statement.setInt(2, space.getHourlyPrice());
                statement.execute();
                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.next();
                return generatedKeys.getLong(1);
            }
        } catch (IOException | SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void deleteSpace(Long id) throws RepositoryException {
        String deleteSpace = "DELETE FROM spaces WHERE id = ?";
        try (Connection connection = DatabaseConnectionManager.getJdbcConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(deleteSpace)) {
                statement.setLong(1, id);
                int effectedRows = statement.executeUpdate();
                if (effectedRows == 0) {
                    throw new RepositoryException("No space was deleted");
                }
            }
        } catch (IOException | SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void updateSpace(Long id, Space newData) throws RepositoryException {
        String updateSpace = """
                UPDATE spaces
                SET type = ?, hourly_price = ?
                WHERE id = ?
                """;
        try (Connection connection = DatabaseConnectionManager.getJdbcConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(updateSpace)) {
                statement.setString(1, newData.getType().name());
                statement.setInt(2, newData.getHourlyPrice());
                statement.setLong(3, id);
                int effectedRows = statement.executeUpdate();
                if (effectedRows == 0) {
                    throw new RepositoryException("No space was updated");
                }
            }
        } catch (IOException | SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void deleteAllSpaces() throws RepositoryException {
        String deleteAllSpaces = "DELETE FROM spaces";
        try (Connection connection = DatabaseConnectionManager.getJdbcConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(deleteAllSpaces)) {
                statement.execute();
            }
        } catch (IOException | SQLException e) {
            throw new RepositoryException(e);
        }
    }

    static Space formSpace(ResultSet resultSet) throws SQLException, RepositoryException {
        return new Space(
                resultSet.getLong("spaces.id"),
                parseSpaceType(resultSet.getString("spaces.type")),
                resultSet.getInt("spaces.hourly_price")
        );
    }

    private static SpaceType parseSpaceType(String spaceType) throws RepositoryException {
        try {
            return SpaceType.valueOf(spaceType);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new RepositoryException("Failed to parse space type");
        }
    }
}
