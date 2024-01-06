package com.example.coordinatefinder.service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordinateServiceRequest {

    private String latitude;
    private String longitude;
    private String radius;

}
