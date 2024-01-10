package com.example.coordinatefinder.service.impl;

import com.example.coordinatefinder.client.GoogleClient;
import com.example.coordinatefinder.config.Constant;
import com.example.coordinatefinder.config.GoogleProperties;
import com.example.coordinatefinder.entity.Coordinate;
import com.example.coordinatefinder.model.CoordinateResponse;
import com.example.coordinatefinder.model.FindCoordinateResponse;
import com.example.coordinatefinder.model.Place;
import com.example.coordinatefinder.model.PlacesNearbySearchResponse;
import com.example.coordinatefinder.repository.CoordinateRepository;
import com.example.coordinatefinder.service.CoordinateService;
import com.example.coordinatefinder.service.model.CoordinateServiceRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CoordinateServiceImpl implements CoordinateService {

    private final GoogleClient googleClient;
    private final GoogleProperties googleProperties;
    private final CoordinateRepository coordinateRepository;
    private final ObjectMapper objectMapper;

    @Override
    public FindCoordinateResponse findCoordinate(CoordinateServiceRequest coordinateServiceRequest) {
        final String latLanCoordinate = coordinateServiceRequest.getLatitude()
                + Constant.COMMA
                + coordinateServiceRequest.getLongitude();

        FindCoordinateResponse response = new FindCoordinateResponse();

        Optional<Coordinate> coordinateOptional = coordinateRepository.findCoordinateByLatitudeAndLongitudeAndRadius(
                coordinateServiceRequest.getLatitude(),
                coordinateServiceRequest.getLongitude(),
                coordinateServiceRequest.getRadius());

        if (coordinateOptional.isEmpty()) {
            final PlacesNearbySearchResponse placesNearbySearchResponse = googleClient.getNearbyPlaces(
                    latLanCoordinate,
                    coordinateServiceRequest.getRadius(),
                    googleProperties.getApiKey());

            saveCoordinate(coordinateServiceRequest, placesNearbySearchResponse);

            response =  prepareFindCoordinateResponse(placesNearbySearchResponse);
        } else {
            try {
                final PlacesNearbySearchResponse placesNearbySearchResponse =
                        objectMapper.readValue(coordinateOptional.get().getResult(), PlacesNearbySearchResponse.class);

                response = prepareFindCoordinateResponse(placesNearbySearchResponse);

            } catch (JsonProcessingException exception) {

            }
        }

        return response;
    }

    private FindCoordinateResponse prepareFindCoordinateResponse(final PlacesNearbySearchResponse placesNearbySearchResponse) {
        final FindCoordinateResponse findCoordinateResponse = new FindCoordinateResponse();
        final List<CoordinateResponse> coordinateList =  new ArrayList<>();

        for (final Place place : placesNearbySearchResponse.getResults()) {
            final CoordinateResponse coordinateResponse = new CoordinateResponse();

            final double latitude = place.getGeometry().getLocation().getLat();
            final double longitude = place.getGeometry().getLocation().getLng();

            coordinateResponse.setLatitude(latitude);
            coordinateResponse.setLongitude(longitude);

            coordinateList.add(coordinateResponse);
        }

        findCoordinateResponse.setCoordinateList(coordinateList);

        return findCoordinateResponse;
    }

    private Coordinate saveCoordinate(final CoordinateServiceRequest coordinateServiceRequest,
                                final PlacesNearbySearchResponse response) {

        final Coordinate coordinate = new Coordinate();

        coordinate.setLongitude(coordinateServiceRequest.getLongitude());
        coordinate.setLatitude(coordinateServiceRequest.getLatitude());
        coordinate.setRadius(coordinateServiceRequest.getRadius());

        try {
            String result = objectMapper.writeValueAsString(response);
            JsonNode jsonNode = objectMapper.valueToTree(result);

            coordinate.setResult(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return coordinateRepository.save(coordinate);
    }
}
