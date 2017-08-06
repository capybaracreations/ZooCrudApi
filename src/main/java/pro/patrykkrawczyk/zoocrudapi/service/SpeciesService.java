package pro.patrykkrawczyk.zoocrudapi.service;

import pro.patrykkrawczyk.zoocrudapi.dto.SpeciesDTO;

import java.util.List;

/**
 * Service Interface for managing Species.
 */
public interface SpeciesService {

    /**
     * Save a species.
     *
     * @param speciesDTO the entity to save
     * @return the persisted entity
     */
    SpeciesDTO save(SpeciesDTO speciesDTO);

    /**
     * Get all the species.
     *
     * @return the list of entities
     */
    List<SpeciesDTO> findAll();

    /**
     * Get the "id" species.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SpeciesDTO findOne(Long id);

    /**
     * Delete the "id" species.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
