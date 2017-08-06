package pro.patrykkrawczyk.zoocrudapi.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pro.patrykkrawczyk.zoocrudapi.TestObjectFactory;
import pro.patrykkrawczyk.zoocrudapi.domain.Animal;
import pro.patrykkrawczyk.zoocrudapi.repository.AnimalRepository;
import pro.patrykkrawczyk.zoocrudapi.service.dto.AnimalDTO;
import pro.patrykkrawczyk.zoocrudapi.service.impl.AnimalServiceImpl;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.AnimalMapper;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.AnimalMapperImpl;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.EnclosureMapperImpl;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.SpeciesMapperImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AnimalServiceTest.class, AnimalServiceImpl.class, AnimalMapperImpl.class, SpeciesMapperImpl.class, EnclosureMapperImpl.class})
public class AnimalServiceTest {

    @Autowired
    private AnimalMapper animalMapper;

    @Autowired
    private AnimalService animalService;

    @MockBean
    private AnimalRepository animalRepository;

    @Test
    public void save_shouldCreateReturnAValidDTORepresentationForUnknownEntity() {
        Animal persisted = TestObjectFactory.Animal();
        persisted.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(animalRepository.save(Mockito.any(Animal.class))).thenReturn(persisted);

        AnimalDTO animalDTO = TestObjectFactory.AnimalDTO();
        AnimalDTO result = animalService.save(animalDTO);

        Assert.assertEquals(persisted.getId(), result.getId());
        Assert.assertEquals(persisted.getName(), result.getName());
    }

    @Test
    public void save_shouldUpdateEntityWithTheSameId() {
        Animal persisted = TestObjectFactory.Animal();
        persisted.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(animalRepository.save(Mockito.any(Animal.class))).thenReturn(persisted);

        AnimalDTO animalDTO = TestObjectFactory.AnimalDTO();
        animalDTO.setId(persisted.getId());
        AnimalDTO result = animalService.save(animalDTO);

        Assert.assertEquals(persisted.getId(), result.getId());
        Assert.assertEquals(persisted.getName(), result.getName());
    }

    @Test
    public void findAll_shouldReturnAListOfEntityDtos() {
        Animal persisted = TestObjectFactory.Animal();
        persisted.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(animalRepository.findAll()).thenReturn(Arrays.asList(persisted));

        List<AnimalDTO> all = animalService.findAll();

        Assert.assertEquals(1, all.size());

        AnimalDTO animalDTO = all.get(0);

        Assert.assertEquals(persisted.getId(), animalDTO.getId());
        Assert.assertEquals(persisted.getName(), animalDTO.getName());
    }

    @Test
    public void findAll_shouldReturnAnEmptyList() {
        Mockito.when(animalRepository.findAll()).thenReturn(new ArrayList<>());

        List<AnimalDTO> all = animalService.findAll();

        Assert.assertEquals(0, all.size());
    }

    @Test
    public void findOne_shouldReturnAValidDtoRepresentation() {
        Animal persisted = TestObjectFactory.Animal();
        persisted.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(animalRepository.findOne(Mockito.any(Long.class))).thenReturn(persisted);

        AnimalDTO one = animalService.findOne(persisted.getId());

        Assert.assertEquals(persisted.getId(), one.getId());
        Assert.assertEquals(persisted.getName(), one.getName());
    }

    @Test
    public void findOne_shouldReturnNull() {
        Mockito.when(animalRepository.findOne(Mockito.any(Long.class))).thenReturn(null);

        AnimalDTO persisted = animalService.findOne(TestObjectFactory.ID_FIELD);

        Assert.assertNull(persisted);
    }

    @Test
    public void delete_shouldNotThrowForValidProperty() {
        animalService.delete(TestObjectFactory.ID_FIELD);
    }

    @Test
    public void delete_shouldNotThrowForNull() {
        animalService.delete(null);
    }
}
