package pro.patrykkrawczyk.zoocrudapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pro.patrykkrawczyk.zoocrudapi.TestObjectFactory;
import pro.patrykkrawczyk.zoocrudapi.dto.SpeciesDTO;
import pro.patrykkrawczyk.zoocrudapi.service.SpeciesService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SpeciesController.class)
public class SpeciesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpeciesService speciesService;

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    @Test
    public void createSpecies_shouldReturnRepresentationOfCreatedEntity() throws Exception {
        SpeciesDTO speciesDTO = TestObjectFactory.SpeciesDTO();
        SpeciesDTO persisted = TestObjectFactory.SpeciesDTO();
        persisted.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(speciesService.save(speciesDTO)).thenReturn(persisted);

        mockMvc.perform(post("/api/species")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(speciesDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(persisted.getId().intValue()))
                .andExpect(jsonPath("$.name").value(persisted.getName()));
    }

    @Test
    public void createSpecies_shouldReturnBadRequestForDtoWithExistingId() throws Exception {
        SpeciesDTO speciesDTO = TestObjectFactory.SpeciesDTO();
        speciesDTO.setId(TestObjectFactory.ID_FIELD);

        mockMvc.perform(post("/api/species")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(speciesDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createSpecies_checkIfNameIsRequired() throws Exception {
        SpeciesDTO speciesDTO = TestObjectFactory.SpeciesDTO();
        speciesDTO.setName(null);

        mockMvc.perform(post("/api/species")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(speciesDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllSpecies_shouldReturnEmptyArray() throws Exception {
        mockMvc.perform(get("/api/species"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").value(hasSize(0)));
    }

    @Test
    public void getAllSpecies_shouldReturnArrayWithSingleResult() throws Exception {
        SpeciesDTO speciesDTO = TestObjectFactory.SpeciesDTO();
        speciesDTO.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(speciesService.findAll()).thenReturn(Arrays.asList(speciesDTO));

        mockMvc.perform(get("/api/species"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").value(hasSize(1)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(speciesDTO.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(speciesDTO.getName())));
    }

    @Test
    public void getSpecies_shouldReturnAStoredEntity() throws Exception {
        SpeciesDTO speciesDTO = TestObjectFactory.SpeciesDTO();
        speciesDTO.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(speciesService.findOne(TestObjectFactory.ID_FIELD)).thenReturn(speciesDTO);

        mockMvc.perform(get("/api/species/{id}", TestObjectFactory.ID_FIELD))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(speciesDTO.getId().intValue()))
                .andExpect(jsonPath("$.name").value(speciesDTO.getName()));
    }

    @Test
    public void getSpecies_shouldReturnNotFound() throws Exception {
        Mockito.when(speciesService.findOne(TestObjectFactory.ID_FIELD)).thenReturn(null);

        mockMvc.perform(get("/api/species/{id}", TestObjectFactory.ID_FIELD))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateSpecies_shouldReturnObjectRepresentingModifiedValue() throws Exception {
        SpeciesDTO speciesDTO = TestObjectFactory.SpeciesDTO();
        speciesDTO.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(speciesService.save(speciesDTO)).thenReturn(speciesDTO);

        mockMvc.perform(put("/api/species")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(speciesDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(speciesDTO.getId().intValue()))
                .andExpect(jsonPath("$.name").value(speciesDTO.getName()));
    }

    @Test
    public void updateSpecies_shouldCreateNewEntityAndReturnItsRepresentationForNonExistingId() throws Exception {
        SpeciesDTO speciesDTO = TestObjectFactory.SpeciesDTO();
        SpeciesDTO persisted = TestObjectFactory.SpeciesDTO();
        persisted.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(speciesService.save(speciesDTO)).thenReturn(persisted);

        mockMvc.perform(put("/api/species")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(speciesDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(persisted.getId().intValue()))
                .andExpect(jsonPath("$.name").value(persisted.getName()));
    }

    @Test
    public void deleteSpecies() throws Exception {
        mockMvc.perform(delete("/api/species/{id}", TestObjectFactory.ID_FIELD)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

}
