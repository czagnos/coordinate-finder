package com.example.coordinatefinder.service;

import com.example.coordinatefinder.model.FindCoordinateResponse;
import com.example.coordinatefinder.service.model.CoordinateServiceRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface CoordinateService {

    FindCoordinateResponse findCoordinate(final CoordinateServiceRequest coordinateServiceRequest);
}
