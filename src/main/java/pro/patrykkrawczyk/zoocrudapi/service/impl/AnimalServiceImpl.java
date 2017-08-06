package pro.patrykkrawczyk.zoocrudapi.service.impl;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.patrykkrawczyk.zoocrudapi.domain.Animal;
import pro.patrykkrawczyk.zoocrudapi.repository.AnimalRepository;
import pro.patrykkrawczyk.zoocrudapi.service.AnimalService;
import pro.patrykkrawczyk.zoocrudapi.service.dto.AnimalDTO;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.AnimalMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Animal.
 */
@Log
@Service
@Transactional
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;

    private final AnimalMapper animalMapper;

    public AnimalServiceImpl(AnimalRepository animalRepository, AnimalMapper animalMapper) {
        this.animalRepository = animalRepository;
        this.animalMapper = animalMapper;
    }

    /**
     * Save a animal.
     *
     * @param animalDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AnimalDTO save(AnimalDTO animalDTO) {
        log.info(String.format("Request to save Animal : %s", animalDTO));

        Animal animal = animalMapper.toEntity(animalDTO);
        animal = animalRepository.save(animal);
        return animalMapper.toDto(animal);
    }

    /**
     * Get all the animals.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AnimalDTO> findAll() {
        log.info("Request to get all Animals");

        return animalRepository.findAll().stream()
                .map(animalMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one animal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AnimalDTO findOne(Long id) {
        log.info(String.format("Request to get Animal : %s", id));

        Animal animal = animalRepository.findOne(id);
        return animalMapper.toDto(animal);
    }

    /**
     * Delete the  animal by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.info(String.format("Request to delete Animal : %s", id));

        animalRepository.delete(id);
    }
}
