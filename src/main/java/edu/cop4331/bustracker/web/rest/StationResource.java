package edu.cop4331.bustracker.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.cop4331.bustracker.domain.Station;
import edu.cop4331.bustracker.repository.StationRepository;
import edu.cop4331.bustracker.repository.UserRepository;
import edu.cop4331.bustracker.service.GoogleMapsService;
import edu.cop4331.bustracker.service.dto.ResponseDTO;
import edu.cop4331.bustracker.service.dto.RouteDTO;
import edu.cop4331.bustracker.service.dto.StationDTO;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StationResource {

    private final Logger log = LoggerFactory.getLogger(StationResource.class);

    @Inject
    private StationRepository stationRepository;

    @Inject
    private UserRepository userRepository;

    public StationResource() {
    }

    /**
     * GET  /stations/user/{userId} : get all stations for user.
     *
     * @param userId
     * @return the ResponseEntity with status 200 (OK) and with an available route
     */
    @GetMapping("/stations/user/{userId}")
    @Timed
    public ResponseEntity<ResponseDTO> getStationsForUserId(@PathVariable Long userId) {
        List<Station> stations = stationRepository.findAllByUserId(userId);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(stations);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * POST  /routes : get all routes that meet criteria.
     *
     * @param forUserId
     * @param stationDTO
     * @return the ResponseEntity with status 200 (OK) and with an available route
     */
    @PostMapping("/stations/user/{forUserId}")
    @Timed
    public ResponseEntity<ResponseDTO> createStation(@PathVariable Long forUserId, @RequestBody StationDTO stationDTO) {
        Station station = new Station();
        station.setAddress(stationDTO.getAddress());
        station.setUser(this.userRepository.findOne(forUserId));
        station = this.stationRepository.save(station);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(station);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
