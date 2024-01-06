package com.example.coordinatefinder.client;

import com.example.coordinatefinder.config.Constant;
import com.example.coordinatefinder.model.PlacesNearbySearchResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface GoogleClient {

    @GetExchange(Constant.GOOGLE_PLACES_NEARBY_ENDPOINT)
    PlacesNearbySearchResponse getNearbyPlaces(@RequestParam("location") final String location,
                                               @RequestParam("radius") final String radius,
                                               @RequestParam("key") final String apiKey);

}
