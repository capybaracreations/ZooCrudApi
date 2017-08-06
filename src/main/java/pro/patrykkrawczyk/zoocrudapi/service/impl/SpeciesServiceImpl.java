package pro.patrykkrawczyk.zoocrudapi.service.impl;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.patrykkrawczyk.zoocrudapi.domain.Species;
import pro.patrykkrawczyk.zoocrudapi.repository.SpeciesRepository;
import pro.patrykkrawczyk.zoocrudapi.service.SpeciesService;
import pro.patrykkrawczyk.zoocrudapi.service.dto.SpeciesDTO;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.SpeciesMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Species.
 */
@Log
@Service
@Transactional
public class SpeciesServiceImpl implements SpeciesService {

    private final SpeciesRepository speciesRepository;

    private final SpeciesMapper speciesMapper;

    public SpeciesServiceImpl(SpeciesRepository speciesRepository, SpeciesMapper speciesMapper) {
        this.speciesRepository = speciesRepository;
        this.speciesMapper = speciesMapper;
    }

    /**
     * Save a species.
     *
     * @param speciesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SpeciesDTO save(SpeciesDTO speciesDTO) {
        log.info(String.format("Request to save Species : %s", speciesDTO));

        Species species = speciesMapper.toEntity(speciesDTO);
        species = speciesRepository.save(species);
        return speciesMapper.toDto(species);
    }

    /**
     * Get all the species.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SpeciesDTO> findAll() {
        log.info("Request to get all Species");

        return speciesRepository.findAll().stream()
                .map(speciesMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one species by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SpeciesDTO findOne(Long id) {
        log.info(String.format("Request to get Species : %s", id));

        Species species = speciesRepository.findOne(id);
        return speciesMapper.toDto(species);
    }

    /**
     * Delete the  species by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.info(String.format("Request to get Species : %s", id));

        speciesRepository.delete(id);
    }
}
