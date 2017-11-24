package edu.cop4331.bustracker.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.cop4331.bustracker.service.GoogleMapsService;
import edu.cop4331.bustracker.service.dto.ResponseDTO;
import edu.cop4331.bustracker.service.dto.RouteDTO;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/routes")
    @Timed
    public ResponseEntity<ResponseDTO> getRoute(@ApiParam String to, @ApiParam String from) {
        RouteDTO routeDTO = googleMapsService.getBusRoute(to, from);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(routeDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
