package edu.cop4331.bustracker.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.cop4331.bustracker.domain.Station;
import edu.cop4331.bustracker.domain.User;
import edu.cop4331.bustracker.repository.StationRepository;
import edu.cop4331.bustracker.repository.UserRepository;
import edu.cop4331.bustracker.security.SecurityUtils;
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
import java.util.Optional;

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
     * GET  /stations : get all stations for current logged in user.
     *
     * @return the ResponseEntity with status 200 (OK) and with an available route
     */
    @GetMapping("/stations")
    @Timed
    public ResponseEntity<ResponseDTO> getStationsForUserId() {
        ResponseDTO responseDTO = new ResponseDTO();

        String currentUserLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> currentUser = this.userRepository.findOneByLogin(currentUserLogin);

        if (currentUser.isPresent()) {
            List<Station> stations = stationRepository.findAllByUserId(currentUser.get().getId());
            responseDTO.setData(stations);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
    }

    /**
     * POST  /stations : create a station for current logged in user.
     *
     * @param stationDTO
     * @return the ResponseEntity with status 200 (OK) and with an available route
     */
    @PostMapping("/stations")
    @Timed
    public ResponseEntity<ResponseDTO> createStation(@RequestBody StationDTO stationDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        String currentUserLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> currentUser = this.userRepository.findOneByLogin(currentUserLogin);

        if (currentUser.isPresent()) {
            Station station = new Station();
            station.setAddress(stationDTO.getAddress());
            station.setUser(currentUser.get());
            station = this.stationRepository.save(station);
            responseDTO.setData(station);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
    }
}
