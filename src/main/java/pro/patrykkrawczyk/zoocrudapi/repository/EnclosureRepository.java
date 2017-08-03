package pro.patrykkrawczyk.zoocrudapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.patrykkrawczyk.zoocrudapi.domain.Enclosure;

/**
 * Spring Data JPA repository for the Enclosure entity.
 */
@Repository
public interface EnclosureRepository extends JpaRepository<Enclosure, Long> {

}
