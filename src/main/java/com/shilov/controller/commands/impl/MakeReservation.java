package com.shilov.controller.commands.impl;

import com.shilov.common.exceptions.SpaceReservationException;
import com.shilov.controller.commands.Command;
import com.shilov.models.ReservationDateTime;
import com.shilov.models.Space;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MakeReservation implements Command {

    private static final String ENTER_RESERVATION_DATE_MESSAGE = "Enter reservation time in format in the format " +
            "yyyy-mm-dd";
    private static final String ENTER_RESERVATION_START_TIME_MESSAGE = "Enter reservation start time in the format " +
            "HH:mm:ss";
    private static final String ENTER_RESERVATION_END_TIME_MESSAGE = "Enter reservation end time in the format " +
            "HH:mm:ss";
    private static final String CHOOSE_SPACE_MESSAGE = "Choose space to reserve:";

    private final CustomerReservationService customerReservationService;

    public MakeReservation(CustomerReservationService customerReservationService) {
        this.customerReservationService = customerReservationService;
    }

    @Override
    public void execute() {
        try(Scanner scanner = new Scanner(System.in)) {
            LocalDate reservationDate = chooseReservationDate(scanner);
            ReservationDateTime reservationTimeRange = chooseReservationTimeRange(scanner);
            ReservationDateTime reservationDateTime = new ReservationDateTime(reservationDate,
                    reservationTimeRange.getStartTime(), reservationTimeRange.getEndTime());

        }
    }

    private LocalDate chooseReservationDate(Scanner scanner) throws SpaceReservationException {
        System.out.println(ENTER_RESERVATION_DATE_MESSAGE);
        try {
            LocalDate reservationDate =  LocalDate.parse(scanner.nextLine());
            if (reservationDate.isBefore(LocalDate.now())) {
                throw new SpaceReservationException("Attempt to reserve space in the past");
            }
            return reservationDate;
        } catch (DateTimeParseException e) {
            throw new SpaceReservationException("Failed to parse reservation date input", e);
        }
    }

    private ReservationDateTime chooseReservationTimeRange(Scanner scanner) {
        System.out.println(ENTER_RESERVATION_START_TIME_MESSAGE);
        LocalTime reservationStartTime = LocalTime.parse(scanner.nextLine());
        System.out.println(ENTER_RESERVATION_END_TIME_MESSAGE);
        LocalTime reservationEndTime = LocalTime.parse(scanner.nextLine());
        return new ReservationDateTime(null, reservationStartTime, reservationEndTime);
    }

    private void chooseSpaceForReservation(Scanner scanner, ReservationDateTime reservationDateTime) {
        System.out.println(CHOOSE_SPACE_MESSAGE);
        List<Space> availableSpaces = customerReservationService.browseAvailableSpaces(reservationDateTime);
        customerReservationService.reserveSpace();
    }

}
