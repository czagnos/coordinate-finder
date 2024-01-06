package com.example.coordinatefinder.service.impl;

import com.example.coordinatefinder.client.GoogleClient;
import com.example.coordinatefinder.config.Constant;
import com.example.coordinatefinder.config.GoogleProperties;
import com.example.coordinatefinder.entity.Coordinate;
import com.example.coordinatefinder.model.PlacesNearbySearchResponse;
import com.example.coordinatefinder.service.CoordinateService;
import com.example.coordinatefinder.service.model.CoordinateServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CoordinateServiceImpl implements CoordinateService {

    private final GoogleClient googleClient;
    private final GoogleProperties googleProperties;
    private final CoordinateRepository coordinateRepository;
    private final ObjectMapper objectMapper;

    @Override
    public Void findCoordinate(CoordinateServiceRequest coordinateServiceRequest) {
        final String latLanCoordinate = coordinateServiceRequest.getLatitude()
                + Constant.COMMA
                + coordinateServiceRequest.getLongitude();

        Optional<Coordinate> coordinateOptional = coordinateRepository.findCoordinateByLatitudeAndLongitudeAndRadius(
                coordinateServiceRequest.getLatitude(),
                coordinateServiceRequest.getLongitude(),
                coordinateServiceRequest.getRadius());

        if (coordinateOptional.isEmpty()) {
            PlacesNearbySearchResponse response = googleClient.getNearbyPlaces(
                    latLanCoordinate,
                    coordinateServiceRequest.getRadius(),
                    googleProperties.getApiKey());

            Coordinate coordinate = saveCoordinate(coordinateServiceRequest, response);
        }

        return null;
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
