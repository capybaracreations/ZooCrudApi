package pro.patrykkrawczyk.zoocrudapi.service;

import pro.patrykkrawczyk.zoocrudapi.service.dto.EnclosureDTO;

import java.util.List;

/**
 * Service Interface for managing Enclosure.
 */
public interface EnclosureService {

    /**
     * Save a enclosure.
     *
     * @param enclosureDTO the entity to save
     * @return the persisted entity
     */
    EnclosureDTO save(EnclosureDTO enclosureDTO);

    /**
     * Get all the enclosures.
     *
     * @return the list of entities
     */
    List<EnclosureDTO> findAll();

    /**
     * Get the "id" enclosure.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EnclosureDTO findOne(Long id);

    /**
     * Delete the "id" enclosure.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
