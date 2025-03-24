package com.shilov.view;

import com.shilov.common.class_loaders.FileClassLoader;
import com.shilov.common.console.ConsoleOperator;
import com.shilov.common.enums.MainMenuInteractionOutput;
import com.shilov.common.enums.ResponseStatus;
import com.shilov.common.properties.PropertyReader;
import com.shilov.controllers.AuthController;
import com.shilov.controllers.ReservationController;
import com.shilov.controllers.SpaceController;
import com.shilov.controllers.factory.BaseControllerFactory;
import com.shilov.controllers.factory.ControllerFactory;
import com.shilov.controllers.factory.JdbcControllerFactory;
import com.shilov.controllers.responses.Response;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class MainMenu extends ConsoleOperator {

    private static final String MAIN_MENU_OPTIONS = """
            1: Admin login
            2: User login
            3: Exit
            """;
    private  static final String ENTER_LOGIN_MESSAGE = "Please, enter your login: ";
    private static final MainMenu INSTANCE = new MainMenu();

    private final BaseControllerFactory controllerFactory = JdbcControllerFactory.getInstance();
    private final AuthController authController = controllerFactory.getAuthController();
    private final ReservationController reservationController = controllerFactory
            .getReservationController();
    private final SpaceController spaceController = ControllerFactory.getInstance().getSpaceController();

    private MainMenu() {}

    public static MainMenu getInstance() {
        return INSTANCE;
    }

    public void showMenuOptions() {
        writeMessageInConsole(MAIN_MENU_OPTIONS);
    }

    public MainMenuInteractionOutput processMenuInteractions() {
        writeMessageInConsole(ENTER_OPTION_NUMBER);
        return switch (readLineFromConsole()) {
            case "1" -> processAdminLogin();
            case "2" -> processCustomerLogin();
            case "3" -> processExit();
            default -> MainMenuInteractionOutput.INTERACTION_FAILED;
        };
    }

    private MainMenuInteractionOutput processAdminLogin() {
        writeMessageInConsole(ENTER_LOGIN_MESSAGE);
        Response response = authController.loginAsAdmin(readLineFromConsole());
        writeMessageInConsole(response.getPayload());
        return response.getStatus() == ResponseStatus.SUCCESS
                ? MainMenuInteractionOutput.BROWSE_ADMIN_MENU
                : MainMenuInteractionOutput.INTERACTION_FAILED;
    }

    private MainMenuInteractionOutput processCustomerLogin() {
        writeMessageInConsole(ENTER_LOGIN_MESSAGE);
        Response response = authController.loginAsCustomer(readLineFromConsole());
        writeMessageInConsole(response.getPayload());
        return response.getStatus() == ResponseStatus.SUCCESS
                ? MainMenuInteractionOutput.BROWSE_CUSTOMER_MENU
                : MainMenuInteractionOutput.INTERACTION_FAILED;
    }

    private MainMenuInteractionOutput processExit() {
        writeMessageInConsole(spaceController.saveSpaces().getPayload());
        writeMessageInConsole(reservationController.saveReservations().getPayload());
        writeMessageInConsole(authController.logout().getPayload());
        try {
            writeGoodbye();
        } catch (IOException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            System.err.println(e.getMessage());
        }
        return MainMenuInteractionOutput.SESSION_FINISHED;
    }

    private void writeGoodbye() throws IOException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        String goodbyeWriterFilePath = PropertyReader.getProperty(PropertyReader.GOODBYE_WRITER_CLASS_FILE_PATH)
                .trim().replace('/', File.separatorChar);
        String className = "GoodbyeWriter";
        String methodName = "writeGoodbye";
        Class<?> goodbyeWriterClass = FileClassLoader.getInstance().loadClassFromFile(goodbyeWriterFilePath, className);
        goodbyeWriterClass.getMethod(methodName).invoke(null);
    }
}
