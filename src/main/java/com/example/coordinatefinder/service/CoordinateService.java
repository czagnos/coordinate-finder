package com.example.coordinatefinder.service;

import com.example.coordinatefinder.service.model.CoordinateServiceRequest;

public interface CoordinateService {

    Void findCoordinate(final CoordinateServiceRequest coordinateServiceRequest);
}
