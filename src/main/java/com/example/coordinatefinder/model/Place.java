package com.example.coordinatefinder.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Place {

    @JsonProperty("geometry")
    private Geometry geometry;
}
