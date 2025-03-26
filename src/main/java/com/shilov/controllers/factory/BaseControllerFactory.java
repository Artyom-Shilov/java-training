package com.shilov.controllers.factory;

import com.shilov.controllers.AuthController;
import com.shilov.controllers.ReservationController;
import com.shilov.controllers.SpaceController;

public interface BaseControllerFactory {

    AuthController getAuthController();
    ReservationController getReservationController();
    SpaceController getSpaceController();
}
