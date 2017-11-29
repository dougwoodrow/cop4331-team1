package edu.cop4331.bustracker.repository;

import edu.cop4331.bustracker.domain.Station;
import edu.cop4331.bustracker.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    List<Station> findAllByUserId(Long userId);
}
