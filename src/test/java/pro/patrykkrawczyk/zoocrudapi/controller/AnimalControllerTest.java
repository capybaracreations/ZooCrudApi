package pro.patrykkrawczyk.zoocrudapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pro.patrykkrawczyk.zoocrudapi.TestObjectFactory;
import pro.patrykkrawczyk.zoocrudapi.service.AnimalService;
import pro.patrykkrawczyk.zoocrudapi.service.dto.AnimalDTO;

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
@WebMvcTest(AnimalController.class)
public class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalService animalService;

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    @Test
    public void createAnimal_shouldReturnRepresentationOfCreatedEntity() throws Exception {
        AnimalDTO animalDTO = TestObjectFactory.AnimalDTO();
        animalDTO.setId(null);

        AnimalDTO persisted = TestObjectFactory.AnimalDTO();

        Mockito.when(animalService.save(animalDTO)).thenReturn(persisted);

        mockMvc.perform(post("/api/animals")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(animalDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(persisted.getId().intValue()))
                .andExpect(jsonPath("$.name").value(persisted.getName()));
    }

    @Test
    public void createAnimal_shouldReturnBadRequestForDtoWithExistingId() throws Exception {
        AnimalDTO animalDTO = TestObjectFactory.AnimalDTO();
        animalDTO.setId(TestObjectFactory.ID_FIELD);

        mockMvc.perform(post("/api/animals")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(animalDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAnimal_checkIfNameIsRequired() throws Exception {
        AnimalDTO animalDTO = TestObjectFactory.AnimalDTO();
        animalDTO.setName(null);

        mockMvc.perform(post("/api/animals")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(animalDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllAnimal_shouldReturnEmptyArray() throws Exception {
        mockMvc.perform(get("/api/animals"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").value(hasSize(0)));
    }

    @Test
    public void getAllAnimal_shouldReturnArrayWithSingleResult() throws Exception {
        AnimalDTO animalDTO = TestObjectFactory.AnimalDTO();
        animalDTO.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(animalService.findAll()).thenReturn(Arrays.asList(animalDTO));

        mockMvc.perform(get("/api/animals"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").value(hasSize(1)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(animalDTO.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(animalDTO.getName())));
    }

    @Test
    public void getAnimal_shouldReturnAStoredEntity() throws Exception {
        AnimalDTO animalDTO = TestObjectFactory.AnimalDTO();
        animalDTO.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(animalService.findOne(TestObjectFactory.ID_FIELD)).thenReturn(animalDTO);

        mockMvc.perform(get("/api/animals/{id}", TestObjectFactory.ID_FIELD))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(animalDTO.getId().intValue()))
                .andExpect(jsonPath("$.name").value(animalDTO.getName()));
    }

    @Test
    public void getAnimal_shouldReturnNotFound() throws Exception {
        Mockito.when(animalService.findOne(TestObjectFactory.ID_FIELD)).thenReturn(null);

        mockMvc.perform(get("/api/animals/{id}", TestObjectFactory.ID_FIELD))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAnimal_shouldReturnObjectRepresentingModifiedValue() throws Exception {
        AnimalDTO animalDTO = TestObjectFactory.AnimalDTO();
        animalDTO.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(animalService.save(animalDTO)).thenReturn(animalDTO);

        mockMvc.perform(put("/api/animals")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(animalDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(animalDTO.getId().intValue()))
                .andExpect(jsonPath("$.name").value(animalDTO.getName()));
    }

    @Test
    public void updateAnimal_shouldCreateNewEntityAndReturnItsRepresentationForNonExistingId() throws Exception {
        AnimalDTO animalDTO = TestObjectFactory.AnimalDTO();
        animalDTO.setId(null);

        AnimalDTO persisted = TestObjectFactory.AnimalDTO();

        Mockito.when(animalService.save(Matchers.any(AnimalDTO.class))).thenReturn(persisted);

        mockMvc.perform(put("/api/animals")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapToJson(animalDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(persisted.getId().intValue()))
                .andExpect(jsonPath("$.name").value(persisted.getName()));
    }

    @Test
    public void deleteAnimal() throws Exception {
        mockMvc.perform(delete("/api/animals/{id}", TestObjectFactory.ID_FIELD)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

}
