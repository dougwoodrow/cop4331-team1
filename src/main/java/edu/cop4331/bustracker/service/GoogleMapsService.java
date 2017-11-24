package edu.cop4331.bustracker.service;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import edu.cop4331.bustracker.service.dto.RouteDTO;
import org.springframework.stereotype.Service;

@Service
public class GoogleMapsService {

    private GeoApiContext context;

    public GoogleMapsService() {
        this.context = new GeoApiContext.Builder()
            .apiKey("AIzaSyAV-TB_B7OrFHvsesiRaJbGXD5BsxdJ6TU")
            .build();
    }

    public RouteDTO getBusRoute(String to, String from) {
//        DirectionsApi.RouteRestriction = new DirectionsApi.RouteRestriction();
        DirectionsApi.getDirections(this.context, to, from);
        return new RouteDTO();
    }
}
