package com.shilov.controllers.factory;

import com.shilov.controllers.AuthController;
import com.shilov.controllers.ReservationController;
import com.shilov.controllers.SpaceController;
import com.shilov.controllers.impl.AuthControllerImpl;
import com.shilov.controllers.impl.ReservationControllerImpl;
import com.shilov.controllers.impl.SpaceControllerImpl;
import com.shilov.repository.GlobalUserRepository;
import com.shilov.repository.ReservationRepository;
import com.shilov.repository.SpaceRepository;
import com.shilov.repository.CurrentUserRepository;
import com.shilov.repository.impl.JdbcGlobalUserRepository;
import com.shilov.repository.impl.ListBasedReservationRepository;
import com.shilov.repository.impl.ListBasedSpaceRepository;
import com.shilov.repository.impl.CurrentUserRepositoryImpl;
import com.shilov.services.AuthService;
import com.shilov.services.ReservationService;
import com.shilov.services.SpaceService;
import com.shilov.services.impl.AuthServiceImpl;
import com.shilov.services.impl.ReservationServiceImpl;
import com.shilov.services.impl.SpaceServiceImpl;

public class ControllerFactory implements BaseControllerFactory {

    private final SpaceRepository spaceRepository = new ListBasedSpaceRepository();
    private final CurrentUserRepository currentUserRepository = new CurrentUserRepositoryImpl();
    private final GlobalUserRepository globalUserRepository = new JdbcGlobalUserRepository();
    private final ReservationRepository reservationRepository = new ListBasedReservationRepository();
    private final SpaceService spaceService = new SpaceServiceImpl(spaceRepository, reservationRepository);
    private final AuthService authService = new AuthServiceImpl(currentUserRepository, globalUserRepository);
    private final ReservationService reservationService = new ReservationServiceImpl(reservationRepository);

    private final AuthControllerImpl authControllerImpl = new AuthControllerImpl(authService);
    private final ReservationControllerImpl reservationControllerImpl
            = new ReservationControllerImpl(reservationService, spaceService, authService);
    private final SpaceControllerImpl spaceControllerImpl
            = new SpaceControllerImpl(spaceService);

    private static final ControllerFactory instance = new ControllerFactory();

    public static ControllerFactory getInstance(){
        return instance;
    }

    private ControllerFactory(){}

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
