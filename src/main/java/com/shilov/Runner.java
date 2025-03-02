package com.shilov;

import com.shilov.view.AdminMenu;
import com.shilov.view.CustomerMenu;
import com.shilov.view.MainMenu;

public class Runner {

    public static void main(String[] args) {
       MainMenu.getInstance().showWelcomeMessage();
       dispatchMainMenuOutput(true);
    }

    private static void dispatchMainMenuOutput(boolean isMenuOptionsVisible) {
        MainMenu mainMenu = MainMenu.getInstance();
        if (isMenuOptionsVisible) {
            mainMenu.showMenuOptions();
        }
        switch (mainMenu.processMenuInteractions()) {
            case SESSION_FINISHED -> System.exit(0);
            case INTERACTION_FAILED -> dispatchMainMenuOutput(false);
            case BROWSE_ADMIN_MENU -> dispatchAdminMenuOutput(true);
            case BROWSE_CUSTOMER_MENU -> dispatchCustomerMenuOutput(true);
        }
    }

    private static void dispatchCustomerMenuOutput(boolean isMenuOptionsVisible) {
        CustomerMenu customerMenu = CustomerMenu.getInstance();
        if (isMenuOptionsVisible) {
            customerMenu.showMenuOptions();
        }
        switch (customerMenu.processMenuInteractions()) {
            case BROWSE_CUSTOMER_MENU, INTERACTION_FAILED -> dispatchCustomerMenuOutput(false);
            case BROWSE_MAIN_MENU -> dispatchMainMenuOutput(true);
        }
    }

    private static void dispatchAdminMenuOutput(boolean isMenuOptionsVisible) {
        AdminMenu adminMenu = AdminMenu.getInstance();
        if (isMenuOptionsVisible) {
            adminMenu.showMenuOptions();
        }
        switch (adminMenu.processMenuInteractions()) {
            case BROWSE_ADMIN_MENU, INTERACTION_FAILED -> dispatchAdminMenuOutput(false);
            case BROWSE_MAIN_MENU -> dispatchMainMenuOutput(true);
        }
    }
}
