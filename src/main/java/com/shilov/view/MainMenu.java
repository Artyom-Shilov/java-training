package com.shilov.view;

import com.shilov.common.enums.MainMenuInteractionOutput;
import com.shilov.common.enums.ResponseStatus;
import com.shilov.controllers.AuthController;
import com.shilov.controllers.factory.ControllerFactory;
import com.shilov.controllers.responses.Response;

public class MainMenu extends BaseMenu {

    private static final String WELCOME_MESSAGE = "Welcome to space reservation app!\n";
    private static final String MAIN_MENU_OPTIONS = """
            1: Admin login
            2: User login
            3: Exit
            """;
    private  static final String ENTER_LOGIN_MESSAGE = "Please, enter your login: ";
    private static final MainMenu INSTANCE = new MainMenu();

    private final AuthController authController = ControllerFactory.getInstance().getBaseAuthController();


    private MainMenu() {}

    public static MainMenu getInstance() {
        return INSTANCE;
    }

    public void showWelcomeMessage() {
        writeMessageInConsole(WELCOME_MESSAGE);
    }

    @Override
    public void showMenuOptions() {
        writeMessageInConsole(MAIN_MENU_OPTIONS);
    }

    public MainMenuInteractionOutput processMenuInteractions() {
        writeMessageInConsole(ENTER_OPTION_NUMBER);
        return switch (readLineFromConsole()) {
            case "1" -> processAdminLogin();
            case "2" -> processCustomerLogin();
            case "3" -> processLogout();
            default -> MainMenuInteractionOutput.INTERACTION_FAILED;
        };
    }

    private MainMenuInteractionOutput processAdminLogin() {
        writeMessageInConsole(ENTER_LOGIN_MESSAGE);
        Response response = authController.loginAsAdmin(readLineFromConsole());
        writeMessageInConsole(response.getOutput());
        return response.getStatus() == ResponseStatus.SUCCESS
                ? MainMenuInteractionOutput.BROWSE_ADMIN_MENU
                : MainMenuInteractionOutput.INTERACTION_FAILED;
    }

    private MainMenuInteractionOutput processCustomerLogin() {
        writeMessageInConsole(ENTER_LOGIN_MESSAGE);
        Response response = authController.loginAsCustomer(readLineFromConsole());
        writeMessageInConsole(response.getOutput());
        return response.getStatus() == ResponseStatus.SUCCESS
                ? MainMenuInteractionOutput.BROWSE_CUSTOMER_MENU
                : MainMenuInteractionOutput.INTERACTION_FAILED;
    }

    private MainMenuInteractionOutput processLogout() {
        Response response = authController.logout();
        writeMessageInConsole(response.getOutput());
        return response.getStatus() == ResponseStatus.SUCCESS
                ? MainMenuInteractionOutput.SESSION_FINISHED
                : MainMenuInteractionOutput.INTERACTION_FAILED;
    }
}
