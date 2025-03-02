package com.shilov.view;

import com.shilov.common.enums.CustomerMenuInteractionOutput;
import com.shilov.common.enums.ResponseStatus;
import com.shilov.controllers.ReservationController;
import com.shilov.controllers.SpaceController;
import com.shilov.controllers.factory.ControllerFactory;
import com.shilov.controllers.requests.MakeCurrentUserReservationRequest;
import com.shilov.controllers.requests.ReservationDateTimeInput;
import com.shilov.controllers.responses.Response;

public class CustomerMenu extends BaseMenu {

    private static final String CUSTOMER_MENU_OPTIONS = """
            Customer menu options
            
            1: Browse available spaces
            2: Make a reservation
            3: View my reservations
            4: Cancel reservation
            5: Return to main menu
            """;

    private final SpaceController spaceController = ControllerFactory.getInstance().getBaseSpaceController();
    private final ReservationController reservationController = ControllerFactory.getInstance()
            .getBaseReservationController();

    private static final CustomerMenu INSTANCE = new CustomerMenu();

    private CustomerMenu() {}

    public static CustomerMenu getInstance() {
        return INSTANCE;
    }

    @Override
    public void showMenuOptions() {
        writeMessageInConsole(CUSTOMER_MENU_OPTIONS);
    }

    public CustomerMenuInteractionOutput processMenuInteractions() {
        writeMessageInConsole(ENTER_OPTION_NUMBER);
        return switch (readLineFromConsole()) {
            case "1" -> processAvailableSpacesBrowse();
            case "2" -> processReservationCreation();
            case "3" -> processCurrentUserReservationBrowse();
            case "4" -> processReservationCancel();
            case "5" -> CustomerMenuInteractionOutput.BROWSE_MAIN_MENU;
            default -> CustomerMenuInteractionOutput.INTERACTION_FAILED;
        };
    }

    private CustomerMenuInteractionOutput processAvailableSpacesBrowse() {
        Response response = spaceController.getAvailableSpaces(readReservationTime());
        writeMessageInConsole(response.getOutput());
        return response.getStatus() == ResponseStatus.SUCCESS
                ? CustomerMenuInteractionOutput.BROWSE_CUSTOMER_MENU
                : CustomerMenuInteractionOutput.INTERACTION_FAILED;
    }

    private ReservationDateTimeInput readReservationTime() {
        writeMessageInConsole(ENTER_DATE);
        String date = readLineFromConsole();
        writeMessageInConsole(ENTER_START_TIME);
        String startTime = readLineFromConsole();
        writeMessageInConsole(ENTER_END_TIME);
        String endTime = readLineFromConsole();
        return new ReservationDateTimeInput(date, startTime, endTime);
    }

    private CustomerMenuInteractionOutput processReservationCreation() {
        writeMessageInConsole(ENTER_SPACE_ID);
        String spaceId = readLineFromConsole();
        ReservationDateTimeInput reservationTime = readReservationTime();
        Response response = reservationController.makeCurrentUserReservation(
                new MakeCurrentUserReservationRequest(spaceId, reservationTime));
        writeMessageInConsole(response.getOutput());
        return response.getStatus() == ResponseStatus.SUCCESS
                ? CustomerMenuInteractionOutput.BROWSE_CUSTOMER_MENU
                : CustomerMenuInteractionOutput.INTERACTION_FAILED;
    }

    private CustomerMenuInteractionOutput processCurrentUserReservationBrowse() {
        Response response = reservationController.getCurrentUserReservations();
        writeMessageInConsole(response.getOutput());
        return response.getStatus() == ResponseStatus.SUCCESS
                ? CustomerMenuInteractionOutput.BROWSE_CUSTOMER_MENU
                : CustomerMenuInteractionOutput.INTERACTION_FAILED;
    }

    private CustomerMenuInteractionOutput processReservationCancel() {
        writeMessageInConsole(ENTER_RESERVATION_ID);
        Response response = reservationController.cancelReservation(readLineFromConsole());
        writeMessageInConsole(response.getOutput());
        return response.getStatus() == ResponseStatus.SUCCESS
                ? CustomerMenuInteractionOutput.BROWSE_CUSTOMER_MENU
                : CustomerMenuInteractionOutput.INTERACTION_FAILED;
    }
}
