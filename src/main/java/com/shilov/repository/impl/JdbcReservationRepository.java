package com.shilov.repository.impl;

import com.shilov.common.connectivity.DatabaseConnectionManager;
import com.shilov.common.enums.ReservationStatus;
import com.shilov.common.exceptions.RepositoryException;
import com.shilov.models.Reservation;
import com.shilov.models.ReservationDateTime;
import com.shilov.models.User;
import com.shilov.models.builders.ReservationBuilder;
import com.shilov.repository.ReservationRepository;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcReservationRepository implements ReservationRepository {

    @Override
    public List<Reservation> getAllReservations() throws RepositoryException {
        String selectReservations = """
                SELECT * FROM reservations
                    INNER JOIN users ON users.id = reservations.customer_id
                    INNER JOIN spaces ON spaces.id = reservations.space_id
                """;
        try (Connection connection = DatabaseConnectionManager.getJdbcConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(selectReservations)) {
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                List<Reservation> reservations = new ArrayList<>();
                while (resultSet.next()) {
                    reservations.add(formReservation(resultSet));
                }
                return reservations;
            }
        } catch (IOException | SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Optional<Reservation> getReservationById(Long id) throws RepositoryException {
        String selectReservationById = """
                SELECT * FROM reservations
                    INNER JOIN users ON users.id = reservations.customer_id
                    INNER JOIN spaces ON spaces.id = reservations.space_id
                         WHERE reservations.id = ?
                """;
        try (Connection connection = DatabaseConnectionManager.getJdbcConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(selectReservationById)) {
                statement.setLong(1, id);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                Optional<Reservation> result = Optional.empty();
                if (resultSet.next()) {
                    result = Optional.of(formReservation(resultSet));
                }
                return result;
            }
        } catch (IOException | SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Reservation> getReservationsByCustomer(User customer) throws RepositoryException {
        String selectReservationsByCustomer = """
                SELECT * FROM reservations
                    INNER JOIN users ON users.id = reservations.customer_id
                    INNER JOIN spaces ON spaces.id = reservations.space_id
                         WHERE users.login = ?
                """;
        try (Connection connection = DatabaseConnectionManager.getJdbcConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(selectReservationsByCustomer)) {
                statement.setString(1, customer.getLogin());
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                List<Reservation> reservations = new ArrayList<>();
                while (resultSet.next()) {
                    reservations.add(formReservation(resultSet));
                }
                return reservations;
            }
        } catch (IOException | SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Long addReservation(Reservation reservation) throws RepositoryException {
        String insertReservation = """
                INSERT INTO reservations
                (customer_id, space_id, status, start_time, end_time, date)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = DatabaseConnectionManager.getJdbcConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(insertReservation, Statement.RETURN_GENERATED_KEYS)) {
                setReservationStatement(reservation, statement);
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
    public void updateReservation(Long id, Reservation newData) throws RepositoryException {
        String updateReservation = """
                UPDATE reservations
                SET customer_id = ?, space_id = ?, status = ?, start_time = ?, end_time = ?, date = ?
                WHERE id = ?
                """;
        try (Connection connection = DatabaseConnectionManager.getJdbcConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(updateReservation)) {
                setReservationStatement(newData, statement);
                statement.setLong(7, id);
                int effectedRows = statement.executeUpdate();
                if (effectedRows == 0) {
                    throw new RepositoryException("No reservation was updated");
                }
            }
        } catch (IOException | SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Reservation> getReservationsIntersectedWithTimeRange(ReservationDateTime dateTimeForIntersection) throws RepositoryException {
        String getIntersectedReservations = """
                SELECT * FROM reservations
                    INNER JOIN users ON users.id = reservations.customer_id
                    INNER JOIN spaces ON spaces.id = reservations.space_id
                         WHERE date = ?
                         AND start_time <= ?
                         AND end_time >= ?
                """;
        try (Connection connection = DatabaseConnectionManager.getJdbcConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getIntersectedReservations)) {
                statement.setDate(1, Date.valueOf(dateTimeForIntersection.getDate()));
                statement.setTime(2, Time.valueOf(dateTimeForIntersection.getEndTime()));
                statement.setTime(3,Time.valueOf(dateTimeForIntersection.getStartTime()));
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                List<Reservation> reservations = new ArrayList<>();
                while (resultSet.next()) {
                    reservations.add(formReservation(resultSet));
                }
                return reservations;
            }
        } catch (IOException | SQLException e) {
            throw new RepositoryException(e);
        }

    }

    private void setReservationStatement(Reservation reservation, PreparedStatement statement) throws SQLException {
        statement.setLong(1, reservation.getCustomer().getId());
        statement.setLong(2, reservation.getSpace().getId());
        statement.setString(3, reservation.getStatus().name());
        statement.setTime(4, Time.valueOf(reservation.getReservationDateTime().getStartTime()));
        statement.setTime(5, Time.valueOf(reservation.getReservationDateTime().getEndTime()));
        statement.setDate(6, Date.valueOf(reservation.getReservationDateTime().getDate()));
    }

    static Reservation formReservation(ResultSet resultSet) throws SQLException, RepositoryException {
        return new ReservationBuilder()
                .setCustomer(JdbcGlobalUserRepository.formUser(resultSet))
                .setSpace(JdbcSpaceRepository.formSpace(resultSet))
                .setReservationDateTime(formReservationDateTime(resultSet))
                .setStatus(parseReservationStatus(resultSet.getString("reservations.status")))
                .setId(resultSet.getLong("reservations.id"))
                .createReservation();
    }

    private static ReservationDateTime formReservationDateTime(ResultSet resultSet) throws SQLException {
        ReservationDateTime reservationDateTime = new ReservationDateTime();
        reservationDateTime.setStartTime(resultSet.getTime("reservations.start_time").toLocalTime());
        reservationDateTime.setEndTime(resultSet.getTime("reservations.end_time").toLocalTime());
        reservationDateTime.setDate(resultSet.getDate("reservations.date").toLocalDate());
        return reservationDateTime;
    }

    private static ReservationStatus parseReservationStatus(String reservationStatus) throws RepositoryException {
        try {
            return ReservationStatus.valueOf(reservationStatus.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new RepositoryException("Failed to parse reservation status");
        }
    }
}
