package edu.cop4331.bustracker.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.cop4331.bustracker.security.AuthoritiesConstants;
import edu.cop4331.bustracker.service.RouteService;
import edu.cop4331.bustracker.service.dto.RouteFilterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

/**
 * Resource to return information about bus routes.
 */
@RestController
@RequestMapping("/api")
public class RouteResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private RouteService routeService;

    /**
     * POST  /routes  : Gets all available routes, for filter criteria.
     * <p>
     * Gets all available routes that match criteria.
     *
     * @return the ResponseEntity with status 200
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/routes")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity createUser(@RequestBody(required = false) RouteFilterDTO routeFilterDTO) throws URISyntaxException {
        log.debug("REST request to get all routes");
        this.routeService.getRoutesForCriteria(routeFilterDTO);
        return ResponseEntity.ok(null);
    }
}
