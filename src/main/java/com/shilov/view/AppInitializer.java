package com.shilov.view;

import com.shilov.common.console.ConsoleOperator;
import com.shilov.common.enums.ResponseStatus;
import com.shilov.controllers.ReservationController;
import com.shilov.controllers.SpaceController;
import com.shilov.controllers.factory.BaseControllerFactory;
import com.shilov.controllers.factory.JdbcControllerFactory;
import com.shilov.controllers.responses.Response;

public class AppInitializer extends ConsoleOperator {

    private static final String WELCOME_MESSAGE = "Welcome to space reservation app!\n";
    private final BaseControllerFactory controllerFactory = JdbcControllerFactory.getInstance();
    private final ReservationController reservationController = controllerFactory.getReservationController();
    private final SpaceController spaceController = controllerFactory.getSpaceController();

    public void initApp() {
        Response reservationInitResponse = reservationController.initReservations();
        writeMessageInConsole(reservationInitResponse.getPayload());
        if (reservationInitResponse.getStatus() != ResponseStatus.SUCCESS) {
            closeApp();
        }
        Response spaceInitResponse = spaceController.initSpaces();
        writeMessageInConsole(spaceInitResponse.getPayload());
        if (spaceInitResponse.getStatus() != ResponseStatus.SUCCESS) {
            closeApp();
        }
        writeMessageInConsole(WELCOME_MESSAGE);
    }

    private void closeApp() {
        System.exit(0);
    }
}
