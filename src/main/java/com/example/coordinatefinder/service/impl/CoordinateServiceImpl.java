package com.example.coordinatefinder.service.impl;

import com.example.coordinatefinder.client.GoogleClient;
import com.example.coordinatefinder.config.Constant;
import com.example.coordinatefinder.config.GoogleProperties;
import com.example.coordinatefinder.model.PlacesNearbySearchResponse;
import com.example.coordinatefinder.service.CoordinateService;
import com.example.coordinatefinder.service.model.CoordinateServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CoordinateServiceImpl implements CoordinateService {

    final GoogleClient googleClient;
    final GoogleProperties googleProperties;

    @Override
    public Void findCoordinate(CoordinateServiceRequest coordinateServiceRequest) {
        final String coordinate = coordinateServiceRequest.getLatitude()
                + Constant.COMMA
                + coordinateServiceRequest.getLongitude();

        PlacesNearbySearchResponse response =
                googleClient.getNearbyPlaces(coordinate, coordinateServiceRequest.getRadius(), googleProperties.getApiKey());


        return null;
    }
}
