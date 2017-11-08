package edu.cop4331.bustracker.service;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import edu.cop4331.bustracker.service.dto.Route;
import edu.cop4331.bustracker.service.dto.RouteFilterDTO;

import java.util.ArrayList;
import java.util.List;

public class RouteService {

    private String googleApiKey = "AIzaSyBNY9gFLvjxCYdYlXm5I72TRLEQn8M47Xs";

    public List<Route> getRoutesForCriteria(RouteFilterDTO routeFilterDTO) {
        GeoApiContext context = new GeoApiContext.Builder()
            .apiKey(this.googleApiKey)
            .build();
        DirectionsApiRequest results = DirectionsApi.getDirections(context, routeFilterDTO.getFrom(), routeFilterDTO.getTo());
        return new ArrayList<>();
    }

}
