package com.shilov.controllers;

import com.shilov.controllers.requests.ReservationDateTimeInput;
import com.shilov.controllers.requests.UpdateSpaceRequest;
import com.shilov.controllers.responses.Response;

public interface SpaceController {

    Response createSpace(String spaceType, String hourlyRate);
    Response deleteSpace(String id);
    Response updateSpace (UpdateSpaceRequest request);
    Response getAvailableSpaces(ReservationDateTimeInput request);
    Response saveSpaces();
    Response initSpaces();
}
