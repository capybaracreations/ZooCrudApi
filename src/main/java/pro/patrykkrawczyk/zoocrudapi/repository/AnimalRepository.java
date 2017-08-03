package pro.patrykkrawczyk.zoocrudapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.patrykkrawczyk.zoocrudapi.domain.Animal;

/**
 * Spring Data JPA repository for the Animal entity.
 */
@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

}
