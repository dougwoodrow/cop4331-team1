package edu.cop4331.bustracker.service;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import edu.cop4331.bustracker.service.dto.RouteDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GoogleMapsService {

    private GeoApiContext context;

    public GoogleMapsService() {
        this.context = new GeoApiContext.Builder()
            .apiKey("AIzaSyAV-TB_B7OrFHvsesiRaJbGXD5BsxdJ6TU")
            .build();
    }

    public RouteDTO getBusRoute(String to, String from) {
        try {
            DirectionsResult directionsResult = DirectionsApi.newRequest(this.context)
                .origin(from)
                .destination(to)
                .mode(TravelMode.TRANSIT)
                .await();

            RouteDTO routeDTO = new RouteDTO();
            routeDTO.setArrivalDateTime(directionsResult.routes[0].legs[0].arrivalTime);
            routeDTO.setDepartureDateTime(directionsResult.routes[0].legs[0].departureTime);
            routeDTO.setBusLinePath(directionsResult.routes[0].overviewPolyline);
            routeDTO.setEndAddress(directionsResult.routes[0].legs[0].endAddress);
            routeDTO.setStartAddress(directionsResult.routes[0].legs[0].startAddress);
            routeDTO.setEndLocation(directionsResult.routes[0].legs[0].endLocation);
            routeDTO.setStartLocation(directionsResult.routes[0].legs[0].startLocation);
            routeDTO.setFare("$" + directionsResult.routes[0].fare.value.toString());
            return routeDTO;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return null;
    }
}
