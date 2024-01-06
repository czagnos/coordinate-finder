package com.example.coordinatefinder.controller;

import com.example.coordinatefinder.api.CoordinateApi;
import com.example.coordinatefinder.service.CoordinateService;
import com.example.coordinatefinder.service.model.CoordinateServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CoordinateController implements CoordinateApi {

    private final CoordinateService coordinateService;

    @Override
    public ResponseEntity<Void> findCoordinate(final String longitude, final String latitude, final String radius) {
        final CoordinateServiceRequest serviceRequest = new CoordinateServiceRequest();

        serviceRequest.setLatitude(latitude);
        serviceRequest.setLongitude(longitude);
        serviceRequest.setRadius(radius);

        coordinateService.findCoordinate(serviceRequest);

        return null;
    }


}
