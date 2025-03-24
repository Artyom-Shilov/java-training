package com.shilov.repository.impl;

import com.shilov.common.connectivity.DatabaseConnectionManager;
import com.shilov.common.enums.UserRole;
import com.shilov.common.exceptions.RepositoryException;
import com.shilov.models.User;
import com.shilov.repository.GlobalUserRepository;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class JdbcGlobalUserRepository implements GlobalUserRepository {

    @Override
    public Long saveUser(User user) throws RepositoryException {
        String insertUser = "INSERT INTO users (role, login) VALUES (?, ?)";
        try (Connection connection = DatabaseConnectionManager.getJdbcConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, user.getRole().name());
                statement.setString(2, user.getLogin());
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
    public Optional<User> findUserById(Long id) throws RepositoryException {
        String selectUserById = "SELECT id, role, login FROM users WHERE id = ?";
        try (Connection connection = DatabaseConnectionManager.getJdbcConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(selectUserById)) {
                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();
                Optional<User> result = Optional.empty();
                if (resultSet.next()) {
                    result = Optional.of(formUser(resultSet));
                }
                return result;
            }
        } catch (IOException | SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws RepositoryException {
        String selectUserById = "SELECT id, role, login FROM users WHERE login = ?";
        try (Connection connection = DatabaseConnectionManager.getJdbcConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(selectUserById)) {
                statement.setString(1, login);
                ResultSet resultSet = statement.executeQuery();
                Optional<User> result = Optional.empty();
                if (resultSet.next()) {
                    result = Optional.of(formUser(resultSet));
                }
                return result;
            }
        } catch (IOException | SQLException e) {
            throw new RepositoryException(e);
        }
    }

    static User formUser(ResultSet resultSet) throws SQLException, RepositoryException {
        return new User(
                resultSet.getLong("users.id"),
                resultSet.getString("users.login"),
                parseRole(resultSet.getString("users.role"))
        );
    }

    private static UserRole parseRole(String role) throws RepositoryException {
        try {
            return UserRole.valueOf(role);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new RepositoryException("Failed to parse user role");
        }
    }
}
