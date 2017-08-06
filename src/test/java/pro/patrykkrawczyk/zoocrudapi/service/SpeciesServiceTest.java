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
import pro.patrykkrawczyk.zoocrudapi.domain.Species;
import pro.patrykkrawczyk.zoocrudapi.dto.SpeciesDTO;
import pro.patrykkrawczyk.zoocrudapi.repository.SpeciesRepository;
import pro.patrykkrawczyk.zoocrudapi.service.impl.SpeciesServiceImpl;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.SpeciesMapper;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.SpeciesMapperImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpeciesServiceTest.class, SpeciesServiceImpl.class, SpeciesMapperImpl.class})
public class SpeciesServiceTest {

    @Autowired
    private SpeciesMapper speciesMapper;

    @Autowired
    private SpeciesService speciesService;

    @MockBean
    private SpeciesRepository speciesRepository;

    @Test
    public void save_shouldCreateReturnAValidDTORepresentationForUnknownEntity() {
        Species persisted = TestObjectFactory.Species();
        persisted.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(speciesRepository.save(Mockito.any(Species.class))).thenReturn(persisted);

        SpeciesDTO speciesDTO = TestObjectFactory.SpeciesDTO();
        SpeciesDTO result = speciesService.save(speciesDTO);

        Assert.assertEquals(persisted.getId(), result.getId());
        Assert.assertEquals(persisted.getName(), result.getName());
    }

    @Test
    public void save_shouldUpdateEntityWithTheSameId() {
        Species persisted = TestObjectFactory.Species();
        persisted.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(speciesRepository.save(Mockito.any(Species.class))).thenReturn(persisted);

        SpeciesDTO speciesDTO = TestObjectFactory.SpeciesDTO();
        speciesDTO.setId(persisted.getId());
        SpeciesDTO result = speciesService.save(speciesDTO);

        Assert.assertEquals(persisted.getId(), result.getId());
        Assert.assertEquals(persisted.getName(), result.getName());
    }

    @Test
    public void findAll_shouldReturnAListOfEntityDtos() {
        Species persisted = TestObjectFactory.Species();
        persisted.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(speciesRepository.findAll()).thenReturn(Arrays.asList(persisted));

        List<SpeciesDTO> all = speciesService.findAll();

        Assert.assertEquals(1, all.size());

        SpeciesDTO speciesDTO = all.get(0);

        Assert.assertEquals(persisted.getId(), speciesDTO.getId());
        Assert.assertEquals(persisted.getName(), speciesDTO.getName());
    }

    @Test
    public void findAll_shouldReturnAnEmptyList() {
        Mockito.when(speciesRepository.findAll()).thenReturn(new ArrayList<>());

        List<SpeciesDTO> all = speciesService.findAll();

        Assert.assertEquals(0, all.size());
    }

    @Test
    public void findOne_shouldReturnAValidDtoRepresentation() {
        Species persisted = TestObjectFactory.Species();
        persisted.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(speciesRepository.findOne(Mockito.any(Long.class))).thenReturn(persisted);

        SpeciesDTO one = speciesService.findOne(persisted.getId());

        Assert.assertEquals(persisted.getId(), one.getId());
        Assert.assertEquals(persisted.getName(), one.getName());
    }

    @Test
    public void findOne_shouldReturnNull() {
        Mockito.when(speciesRepository.findOne(Mockito.any(Long.class))).thenReturn(null);

        SpeciesDTO persisted = speciesService.findOne(TestObjectFactory.ID_FIELD);

        Assert.assertNull(persisted);
    }

    @Test
    public void delete_shouldNotThrowForValidProperty() {
        speciesService.delete(TestObjectFactory.ID_FIELD);
    }

    @Test
    public void delete_shouldNotThrowForNull() {
        speciesService.delete(null);
    }
}
