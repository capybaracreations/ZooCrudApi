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
import pro.patrykkrawczyk.zoocrudapi.service.EnclosureService;
import pro.patrykkrawczyk.zoocrudapi.service.dto.EnclosureDTO;

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
@WebMvcTest(EnclosureController.class)
public class EnclosureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnclosureService enclosureService;

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    @Test
    public void createEnclosure_shouldReturnRepresentationOfCreatedEntity() throws Exception {
        EnclosureDTO enclosureDTO = TestObjectFactory.EnclosureDTO();
        enclosureDTO.setId(null);

        EnclosureDTO persisted = TestObjectFactory.EnclosureDTO();

        Mockito.when(enclosureService.save(enclosureDTO)).thenReturn(persisted);

        mockMvc.perform(post("/api/enclosures")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(enclosureDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(persisted.getId().intValue()))
                .andExpect(jsonPath("$.name").value(persisted.getName()));
    }

    @Test
    public void createEnclosure_shouldReturnBadRequestForDtoWithExistingId() throws Exception {
        EnclosureDTO enclosureDTO = TestObjectFactory.EnclosureDTO();
        enclosureDTO.setId(TestObjectFactory.ID_FIELD);

        mockMvc.perform(post("/api/enclosures")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(enclosureDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createEnclosure_checkIfNameIsRequired() throws Exception {
        EnclosureDTO enclosureDTO = TestObjectFactory.EnclosureDTO();
        enclosureDTO.setName(null);

        mockMvc.perform(post("/api/enclosures")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(enclosureDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllEnclosure_shouldReturnEmptyArray() throws Exception {
        mockMvc.perform(get("/api/enclosures"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").value(hasSize(0)));
    }

    @Test
    public void getAllEnclosure_shouldReturnArrayWithSingleResult() throws Exception {
        EnclosureDTO enclosureDTO = TestObjectFactory.EnclosureDTO();
        enclosureDTO.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(enclosureService.findAll()).thenReturn(Arrays.asList(enclosureDTO));

        mockMvc.perform(get("/api/enclosures"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").value(hasSize(1)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(enclosureDTO.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(enclosureDTO.getName())));
    }

    @Test
    public void getEnclosure_shouldReturnAStoredEntity() throws Exception {
        EnclosureDTO enclosureDTO = TestObjectFactory.EnclosureDTO();
        enclosureDTO.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(enclosureService.findOne(TestObjectFactory.ID_FIELD)).thenReturn(enclosureDTO);

        mockMvc.perform(get("/api/enclosures/{id}", TestObjectFactory.ID_FIELD))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(enclosureDTO.getId().intValue()))
                .andExpect(jsonPath("$.name").value(enclosureDTO.getName()));
    }

    @Test
    public void getEnclosure_shouldReturnNotFound() throws Exception {
        Mockito.when(enclosureService.findOne(TestObjectFactory.ID_FIELD)).thenReturn(null);

        mockMvc.perform(get("/api/enclosures/{id}", TestObjectFactory.ID_FIELD))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateEnclosure_shouldReturnObjectRepresentingModifiedValue() throws Exception {
        EnclosureDTO enclosureDTO = TestObjectFactory.EnclosureDTO();
        enclosureDTO.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(enclosureService.save(enclosureDTO)).thenReturn(enclosureDTO);

        mockMvc.perform(put("/api/enclosures")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(enclosureDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(enclosureDTO.getId().intValue()))
                .andExpect(jsonPath("$.name").value(enclosureDTO.getName()));
    }

    @Test
    public void updateEnclosure_shouldCreateNewEntityAndReturnItsRepresentationForNonExistingId() throws Exception {
        EnclosureDTO enclosureDTO = TestObjectFactory.EnclosureDTO();
        enclosureDTO.setId(null);

        EnclosureDTO persisted = TestObjectFactory.EnclosureDTO();

        Mockito.when(enclosureService.save(enclosureDTO)).thenReturn(persisted);

        mockMvc.perform(put("/api/enclosures/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(enclosureDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(persisted.getId().intValue()))
                .andExpect(jsonPath("$.name").value(persisted.getName()));
    }

    @Test
    public void deleteEnclosure() throws Exception {
        mockMvc.perform(delete("/api/enclosures/{id}", TestObjectFactory.ID_FIELD)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

}
