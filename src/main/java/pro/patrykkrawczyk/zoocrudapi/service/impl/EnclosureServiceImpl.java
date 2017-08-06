package pro.patrykkrawczyk.zoocrudapi.service.impl;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.patrykkrawczyk.zoocrudapi.domain.Enclosure;
import pro.patrykkrawczyk.zoocrudapi.repository.EnclosureRepository;
import pro.patrykkrawczyk.zoocrudapi.service.EnclosureService;
import pro.patrykkrawczyk.zoocrudapi.service.dto.EnclosureDTO;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.EnclosureMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Enclosure.
 */
@Log
@Service
@Transactional
public class EnclosureServiceImpl implements EnclosureService {

    private final EnclosureRepository enclosureRepository;

    private final EnclosureMapper enclosureMapper;

    public EnclosureServiceImpl(EnclosureRepository enclosureRepository, EnclosureMapper enclosureMapper) {
        this.enclosureRepository = enclosureRepository;
        this.enclosureMapper = enclosureMapper;
    }

    /**
     * Save a enclosure.
     *
     * @param enclosureDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EnclosureDTO save(EnclosureDTO enclosureDTO) {
        log.info(String.format("Request to save Enclosure  %s", enclosureDTO));

        Enclosure enclosure = enclosureMapper.toEntity(enclosureDTO);
        enclosure = enclosureRepository.save(enclosure);
        return enclosureMapper.toDto(enclosure);
    }

    /**
     * Get all the enclosures.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EnclosureDTO> findAll() {
        log.info("Request to get all Enclosures");

        return enclosureRepository.findAll().stream()
                .map(enclosureMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one enclosure by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EnclosureDTO findOne(Long id) {
        log.info(String.format("Request to get Enclosure : %s", id));

        Enclosure enclosure = enclosureRepository.findOne(id);
        return enclosureMapper.toDto(enclosure);
    }

    /**
     * Delete the  enclosure by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.info(String.format("Request to delete Enclosure : %s", id));

        enclosureRepository.delete(id);
    }
}
