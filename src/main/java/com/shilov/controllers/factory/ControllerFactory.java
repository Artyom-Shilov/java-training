package com.shilov.controllers.factory;

import com.shilov.controllers.AuthController;
import com.shilov.controllers.ReservationController;
import com.shilov.controllers.SpaceController;
import com.shilov.controllers.impl.AuthControllerImpl;
import com.shilov.controllers.impl.ReservationControllerImpl;
import com.shilov.controllers.impl.SpaceControllerImpl;
import com.shilov.repository.ReservationRepository;
import com.shilov.repository.SpaceRepository;
import com.shilov.repository.UserRepository;
import com.shilov.repository.impl.ListBasedReservationRepository;
import com.shilov.repository.impl.ListBasedSpaceRepository;
import com.shilov.repository.impl.UserRepositoryImpl;
import com.shilov.services.AuthService;
import com.shilov.services.ReservationService;
import com.shilov.services.SpaceService;
import com.shilov.services.impl.AuthServiceImpl;
import com.shilov.services.impl.ReservationServiceImpl;
import com.shilov.services.impl.SpaceServiceImpl;

public class ControllerFactory {

    private final SpaceRepository spaceRepository = new ListBasedSpaceRepository();
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final ReservationRepository reservationRepository = new ListBasedReservationRepository();
    private final SpaceService spaceService = new SpaceServiceImpl(spaceRepository, reservationRepository);
    private final AuthService authService = new AuthServiceImpl(userRepository);
    private final ReservationService reservationService = new ReservationServiceImpl(reservationRepository);

    private final AuthControllerImpl baseAuthControllerImpl = new AuthControllerImpl(authService);
    private final ReservationControllerImpl baseReservationControllerImpl
            = new ReservationControllerImpl(reservationService, spaceService, authService);
    private final SpaceControllerImpl baseSpaceControllerImpl
            = new SpaceControllerImpl(spaceService);

    private static final ControllerFactory instance = new ControllerFactory();

    public static ControllerFactory getInstance(){
        return instance;
    }

    private ControllerFactory(){}

    public AuthController getBaseAuthController() {
        return baseAuthControllerImpl;
    }

    public ReservationController getBaseReservationController() {
        return baseReservationControllerImpl;
    }

    public SpaceController getBaseSpaceController() {
        return baseSpaceControllerImpl;
    }
}
