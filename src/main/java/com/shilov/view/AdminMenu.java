package com.shilov.view;

import com.shilov.common.console.ConsoleOperator;
import com.shilov.common.enums.AdminMenuInteractionOutput;
import com.shilov.common.enums.ResponseStatus;
import com.shilov.common.enums.SpaceType;
import com.shilov.controllers.ReservationController;
import com.shilov.controllers.SpaceController;
import com.shilov.controllers.factory.ControllerFactory;
import com.shilov.controllers.responses.Response;

import java.util.Arrays;

public class AdminMenu extends ConsoleOperator {

    private static final String ADMIN_MENU_OPTIONS = """
            Admin Menu Options
            
            1: Add a new coworking space
            2: Remove a coworking space
            3: View all reservations
            4: Return to main menu
            """;

    private final SpaceController spaceController = ControllerFactory.getInstance().getBaseSpaceController();
    private final ReservationController reservationController = ControllerFactory.getInstance()
            .getBaseReservationController();

    private static final AdminMenu INSTANCE = new AdminMenu();

    private AdminMenu() {}

    public static AdminMenu getInstance() {
        return INSTANCE;
    }

    public void showMenuOptions() {
        writeMessageInConsole(ADMIN_MENU_OPTIONS);
    }

    public AdminMenuInteractionOutput processMenuInteractions() {
        writeMessageInConsole(ENTER_OPTION_NUMBER);
        return switch (readLineFromConsole()) {
            case "1" -> processSpaceAdding();
            case "2" -> processSpaceRemoval();
            case "3" -> processAllReservationBrowse();
            case "4" -> AdminMenuInteractionOutput.BROWSE_MAIN_MENU;
            default -> AdminMenuInteractionOutput.INTERACTION_FAILED;
        };
    }

    private AdminMenuInteractionOutput processSpaceAdding() {
        writeMessageInConsole(ENTER_SPACE_TYPE);
        Arrays.stream(SpaceType.values()).forEach(spaceType -> writeMessageInConsole(spaceType.toString()));
        String enteredSpaceType = readLineFromConsole().toUpperCase();
        writeMessageInConsole(ENTER_SPACE_PRICE);
        String enteredSpacePrice = readLineFromConsole();
        Response response = spaceController.createSpace(enteredSpaceType, enteredSpacePrice);
        writeMessageInConsole(response.getPayload());
        return response.getStatus() == ResponseStatus.SUCCESS
                ? AdminMenuInteractionOutput.BROWSE_ADMIN_MENU
                : AdminMenuInteractionOutput.INTERACTION_FAILED;
    }

    private AdminMenuInteractionOutput processSpaceRemoval() {
        writeMessageInConsole(ENTER_SPACE_ID);
        Response response = spaceController.deleteSpace(readLineFromConsole());
        writeMessageInConsole(response.getPayload());
        return response.getStatus() == ResponseStatus.SUCCESS
                ? AdminMenuInteractionOutput.BROWSE_ADMIN_MENU
                : AdminMenuInteractionOutput.INTERACTION_FAILED;
    }

    private AdminMenuInteractionOutput processAllReservationBrowse() {
        Response response = reservationController.getAllReservations();
        writeMessageInConsole(response.getPayload());
        return response.getStatus() == ResponseStatus.SUCCESS
                ? AdminMenuInteractionOutput.BROWSE_ADMIN_MENU
                : AdminMenuInteractionOutput.INTERACTION_FAILED;
    }
}
