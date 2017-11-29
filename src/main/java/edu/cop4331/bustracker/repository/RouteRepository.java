package edu.cop4331.bustracker.repository;

import edu.cop4331.bustracker.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

}
