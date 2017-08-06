package pro.patrykkrawczyk.zoocrudapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import pro.patrykkrawczyk.zoocrudapi.service.EnclosureService;
import pro.patrykkrawczyk.zoocrudapi.service.dto.EnclosureDTO;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Enclosure.
 */
@RestController
@RequestMapping("/api")
public class EnclosureController {

    private static final String ENTITY_NAME = "enclosure";
    private final Logger log = LoggerFactory.getLogger(EnclosureController.class);
    private final EnclosureService enclosureService;

    public EnclosureController(EnclosureService enclosureService) {
        this.enclosureService = enclosureService;
    }

    /**
     * POST  /enclosures : Create a new enclosure.
     *
     * @param enclosureDTO the enclosureDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new enclosureDTO, or with status 400 (Bad Request) if the enclosure has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/enclosures")
    public ResponseEntity<EnclosureDTO> createEnclosure(@Valid @RequestBody EnclosureDTO enclosureDTO) throws URISyntaxException {
        log.info(String.format("REST request to save Enclosure : %s", enclosureDTO));

        if (enclosureDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new enclosure cannot already have an ID")).body(null);
        }

        EnclosureDTO result = enclosureService.save(enclosureDTO);
        return ResponseEntity.created(new URI("/api/enclosures/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /enclosures : Updates an existing enclosure.
     *
     * @param enclosureDTO the enclosureDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated enclosureDTO,
     * or with status 400 (Bad Request) if the enclosureDTO is not valid,
     * or with status 500 (Internal Server Error) if the enclosureDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/enclosures")
    public ResponseEntity<EnclosureDTO> updateEnclosure(@Valid @RequestBody EnclosureDTO enclosureDTO) throws URISyntaxException {
        log.info(String.format("REST request to update Enclosure : %s", enclosureDTO));

        if (enclosureDTO.getId() == null) {
            return createEnclosure(enclosureDTO);
        }

        EnclosureDTO result = enclosureService.save(enclosureDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, enclosureDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  /enclosures : get all the enclosures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of enclosures in body
     */
    @GetMapping("/enclosures")
    public List<EnclosureDTO> getAllEnclosures() {
        log.info("REST request to get all Enclosures");

        return enclosureService.findAll();
    }

    /**
     * GET  /enclosures/:id : get the "id" enclosure.
     *
     * @param id the id of the enclosureDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the enclosureDTO, or with status 404 (Not Found)
     */
    @GetMapping("/enclosures/{id}")
    public ResponseEntity<EnclosureDTO> getEnclosure(@PathVariable Long id) {
        log.info(String.format("REST request to get Enclosure : %s", id));

        EnclosureDTO enclosureDTO = enclosureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(enclosureDTO));
    }

    /**
     * DELETE  /enclosures/:id : delete the "id" enclosure.
     *
     * @param id the id of the enclosureDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/enclosures/{id}")
    public ResponseEntity<Void> deleteEnclosure(@PathVariable Long id) {
        log.info(String.format("REST request to delete Enclosure : %s", id));

        enclosureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
