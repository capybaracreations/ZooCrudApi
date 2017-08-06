package pro.patrykkrawczyk.zoocrudapi.controller;

import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.patrykkrawczyk.zoocrudapi.controller.util.HeaderUtil;
import pro.patrykkrawczyk.zoocrudapi.controller.util.ResponseUtil;
import pro.patrykkrawczyk.zoocrudapi.dto.SpeciesDTO;
import pro.patrykkrawczyk.zoocrudapi.service.SpeciesService;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Species.
 */
@Log
@RestController
@RequestMapping("/api")
public class SpeciesController {

    private static final String ENTITY_NAME = "species";

    private final SpeciesService speciesService;

    public SpeciesController(SpeciesService speciesService) {
        this.speciesService = speciesService;
    }

    /**
     * POST  /species : Create a new species.
     *
     * @param speciesDTO the speciesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new speciesDTO, or with status 400 (Bad Request) if the species has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/species")
    public ResponseEntity<SpeciesDTO> createSpecies(@Valid @RequestBody SpeciesDTO speciesDTO) throws URISyntaxException {
        log.info(String.format("REST request to save Species : %s", speciesDTO));

        if (speciesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new species cannot already have an ID")).body(null);
        }

        SpeciesDTO result = speciesService.save(speciesDTO);
        return ResponseEntity.created(new URI("/api/species/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /species : Updates an existing species.
     *
     * @param speciesDTO the speciesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated speciesDTO,
     * or with status 400 (Bad Request) if the speciesDTO is not valid,
     * or with status 500 (Internal Server Error) if the speciesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/species")
    public ResponseEntity<SpeciesDTO> updateSpecies(@Valid @RequestBody SpeciesDTO speciesDTO) throws URISyntaxException {
        log.info(String.format("REST request to update Species : %s", speciesDTO));

        if (speciesDTO.getId() == null) {
            return createSpecies(speciesDTO);
        }

        SpeciesDTO result = speciesService.save(speciesDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, speciesDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  /species : get all the species.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of species in body
     */
    @GetMapping("/species")
    public List<SpeciesDTO> getAllSpecies() {
        log.info("REST request to get all Species");

        return speciesService.findAll();
    }

    /**
     * GET  /species/:id : get the "id" species.
     *
     * @param id the id of the speciesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the speciesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/species/{id}")
    public ResponseEntity<SpeciesDTO> getSpecies(@PathVariable Long id) {
        log.info(String.format("REST request to get Species : %s", id));

        SpeciesDTO speciesDTO = speciesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(speciesDTO));
    }

    /**
     * DELETE  /species/:id : delete the "id" species.
     *
     * @param id the id of the speciesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/species/{id}")
    public ResponseEntity<Void> deleteSpecies(@PathVariable Long id) {
        log.info(String.format("REST request to delete Species : %s", id));

        speciesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
