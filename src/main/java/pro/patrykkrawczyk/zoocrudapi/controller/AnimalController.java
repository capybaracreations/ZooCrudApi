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
import pro.patrykkrawczyk.zoocrudapi.service.AnimalService;
import pro.patrykkrawczyk.zoocrudapi.service.dto.AnimalDTO;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Animal.
 */
@Log
@RestController
@RequestMapping("/api")
public class AnimalController {

    private static final String ENTITY_NAME = "animal";

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    /**
     * POST  /animals : Create a new animal.
     *
     * @param animalDTO the animalDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new animalDTO, or with status 400 (Bad Request) if the animal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/animals")
    public ResponseEntity<AnimalDTO> createAnimal(@Valid @RequestBody AnimalDTO animalDTO) throws URISyntaxException {
        log.info(String.format("REST request to save Animal : %s", animalDTO));

        if (animalDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new animal cannot already have an ID")).body(null);
        }

        AnimalDTO result = animalService.save(animalDTO);
        return ResponseEntity.created(new URI("/api/animals/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /animals : Updates an existing animal.
     *
     * @param animalDTO the animalDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated animalDTO,
     * or with status 400 (Bad Request) if the animalDTO is not valid,
     * or with status 500 (Internal Server Error) if the animalDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/animals")
    public ResponseEntity<AnimalDTO> updateAnimal(@Valid @RequestBody AnimalDTO animalDTO) throws URISyntaxException {
        log.info(String.format("REST request to update Animal : %s", animalDTO));

        if (animalDTO.getId() == null) {
            return createAnimal(animalDTO);
        }

        AnimalDTO result = animalService.save(animalDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, animalDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  /animals : get all the animals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of animals in body
     */
    @GetMapping("/animals")
    public List<AnimalDTO> getAllAnimals() {
        log.info("REST request to get all Animals");

        return animalService.findAll();
    }

    /**
     * GET  /animals/:id : get the "id" animal.
     *
     * @param id the id of the animalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the animalDTO, or with status 404 (Not Found)
     */
    @GetMapping("/animals/{id}")
    public ResponseEntity<AnimalDTO> getAnimal(@PathVariable Long id) {
        log.info(String.format("REST request to get Animal : %s", id));

        AnimalDTO animalDTO = animalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(animalDTO));
    }

    /**
     * DELETE  /animals/:id : delete the "id" animal.
     *
     * @param id the id of the animalDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/animals/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        log.info(String.format("REST request to delete Animal : %s", id));

        animalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
