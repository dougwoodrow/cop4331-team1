package edu.cop4331.bustracker.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.cop4331.bustracker.domain.Route;
import edu.cop4331.bustracker.repository.RouteRepository;
import edu.cop4331.bustracker.service.GoogleMapsService;
import edu.cop4331.bustracker.service.dto.ResponseDTO;
import edu.cop4331.bustracker.service.dto.RouteDTO;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api")
public class RouteResource {

    private final Logger log = LoggerFactory.getLogger(RouteResource.class);

    @Inject
    private GoogleMapsService googleMapsService;

    @Inject
    private RouteRepository routeRepository;

    public RouteResource(GoogleMapsService googleMapsService) {
        this.googleMapsService = googleMapsService;
    }

    /**
     * POST  /routes : get all routes that meet criteria.
     *
     * @param to the to location
     * @param from the from location
     * @return the ResponseEntity with status 200 (OK) and with an available route
     */
    @GetMapping("/routes")
    @Timed
    public ResponseEntity<ResponseDTO> getRoute(@ApiParam String to, @ApiParam String from) {
        RouteDTO routeDTO = googleMapsService.getBusRoute(to, from);
        ResponseDTO responseDTO = new ResponseDTO();

        Route route = new Route();
        route.setArrivalDateTime(routeDTO.getArrivalDateTime());
        route.setBusLinePath(routeDTO.getBusLinePath());
        route.setDepartureDateTime(routeDTO.getDepartureDateTime());
        route.setStartAddress(routeDTO.getStartAddress());
        route.setStartLocation(routeDTO.getStartLocation());
        route.setEndAddress(routeDTO.getEndAddress());
        route.setEndLocation(routeDTO.getEndLocation());
        route.setFare(routeDTO.getFare());
        route = this.routeRepository.save(route);

        responseDTO.setData(route);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
