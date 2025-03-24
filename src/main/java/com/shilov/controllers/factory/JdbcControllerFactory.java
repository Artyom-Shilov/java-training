package com.shilov.controllers.factory;

import com.shilov.controllers.AuthController;
import com.shilov.controllers.ReservationController;
import com.shilov.controllers.SpaceController;
import com.shilov.controllers.impl.AuthControllerImpl;
import com.shilov.controllers.impl.ReservationControllerImpl;
import com.shilov.controllers.impl.SpaceControllerImpl;
import com.shilov.repository.CurrentUserRepository;
import com.shilov.repository.GlobalUserRepository;
import com.shilov.repository.ReservationRepository;
import com.shilov.repository.SpaceRepository;
import com.shilov.repository.impl.*;
import com.shilov.services.AuthService;
import com.shilov.services.ReservationService;
import com.shilov.services.SpaceService;
import com.shilov.services.impl.AuthServiceImpl;
import com.shilov.services.impl.ReservationServiceImpl;
import com.shilov.services.impl.SpaceServiceImpl;

public class JdbcControllerFactory implements BaseControllerFactory {

    private final SpaceRepository spaceRepository = new JdbcSpaceRepository();
    private final CurrentUserRepository currentUserRepository = new CurrentUserRepositoryImpl();
    private final GlobalUserRepository globalUserRepository = new JdbcGlobalUserRepository();
    private final ReservationRepository reservationRepository = new JdbcReservationRepository();
    private final SpaceService spaceService = new SpaceServiceImpl(spaceRepository, reservationRepository);
    private final AuthService authService = new AuthServiceImpl(currentUserRepository, globalUserRepository);
    private final ReservationService reservationService = new ReservationServiceImpl(reservationRepository);

    private final AuthControllerImpl authControllerImpl = new AuthControllerImpl(authService);
    private final ReservationControllerImpl reservationControllerImpl
            = new ReservationControllerImpl(reservationService, spaceService, authService);
    private final SpaceControllerImpl spaceControllerImpl
            = new SpaceControllerImpl(spaceService);

    private static final JdbcControllerFactory instance = new JdbcControllerFactory();

    public static JdbcControllerFactory getInstance(){
        return instance;
    }

    private JdbcControllerFactory(){}

    @Override
    public AuthController getAuthController() {
        return authControllerImpl;
    }

    @Override
    public ReservationController getReservationController() {
        return reservationControllerImpl;
    }

    @Override
    public SpaceController getSpaceController() {
        return spaceControllerImpl;
    }
}
