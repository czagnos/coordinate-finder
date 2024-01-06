package com.example.coordinatefinder.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LatLngLiteral {

    @JsonProperty("lat")
    private double lat;

    @JsonProperty("lng")
    private double lng;
}
