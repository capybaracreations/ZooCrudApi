package pro.patrykkrawczyk.zoocrudapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.patrykkrawczyk.zoocrudapi.domain.Species;

/**
 * Spring Data JPA repository for the Species entity.
 */
@Repository
public interface SpeciesRepository extends JpaRepository<Species, Long> {

}
