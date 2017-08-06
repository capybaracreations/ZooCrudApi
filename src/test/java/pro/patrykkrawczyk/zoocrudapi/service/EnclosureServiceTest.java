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
import pro.patrykkrawczyk.zoocrudapi.domain.Enclosure;
import pro.patrykkrawczyk.zoocrudapi.repository.EnclosureRepository;
import pro.patrykkrawczyk.zoocrudapi.service.dto.EnclosureDTO;
import pro.patrykkrawczyk.zoocrudapi.service.impl.EnclosureServiceImpl;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.AnimalMapperImpl;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.EnclosureMapper;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.EnclosureMapperImpl;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.SpeciesMapperImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EnclosureServiceTest.class, EnclosureServiceImpl.class, EnclosureMapperImpl.class, AnimalMapperImpl.class, SpeciesMapperImpl.class})
public class EnclosureServiceTest {

    @Autowired
    private EnclosureMapper enclosureMapper;

    @Autowired
    private EnclosureService enclosureService;

    @MockBean
    private EnclosureRepository enclosureRepository;

    @Test
    public void save_shouldCreateReturnAValidDTORepresentationForUnknownEntity() {
        Enclosure persisted = TestObjectFactory.Enclosure();
        persisted.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(enclosureRepository.save(Mockito.any(Enclosure.class))).thenReturn(persisted);

        EnclosureDTO enclosureDTO = TestObjectFactory.EnclosureDTO();
        EnclosureDTO result = enclosureService.save(enclosureDTO);

        Assert.assertEquals(persisted.getId(), result.getId());
        Assert.assertEquals(persisted.getName(), result.getName());
    }

    @Test
    public void save_shouldUpdateEntityWithTheSameId() {
        Enclosure persisted = TestObjectFactory.Enclosure();
        persisted.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(enclosureRepository.save(Mockito.any(Enclosure.class))).thenReturn(persisted);

        EnclosureDTO enclosureDTO = TestObjectFactory.EnclosureDTO();
        enclosureDTO.setId(persisted.getId());
        EnclosureDTO result = enclosureService.save(enclosureDTO);

        Assert.assertEquals(persisted.getId(), result.getId());
        Assert.assertEquals(persisted.getName(), result.getName());
    }

    @Test
    public void findAll_shouldReturnAListOfEntityDtos() {
        Enclosure persisted = TestObjectFactory.Enclosure();
        persisted.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(enclosureRepository.findAll()).thenReturn(Arrays.asList(persisted));

        List<EnclosureDTO> all = enclosureService.findAll();

        Assert.assertEquals(1, all.size());

        EnclosureDTO enclosureDTO = all.get(0);

        Assert.assertEquals(persisted.getId(), enclosureDTO.getId());
        Assert.assertEquals(persisted.getName(), enclosureDTO.getName());
    }

    @Test
    public void findAll_shouldReturnAnEmptyList() {
        Mockito.when(enclosureRepository.findAll()).thenReturn(new ArrayList<>());

        List<EnclosureDTO> all = enclosureService.findAll();

        Assert.assertEquals(0, all.size());
    }

    @Test
    public void findOne_shouldReturnAValidDtoRepresentation() {
        Enclosure persisted = TestObjectFactory.Enclosure();
        persisted.setId(TestObjectFactory.ID_FIELD);

        Mockito.when(enclosureRepository.findOne(Mockito.any(Long.class))).thenReturn(persisted);

        EnclosureDTO one = enclosureService.findOne(persisted.getId());

        Assert.assertEquals(persisted.getId(), one.getId());
        Assert.assertEquals(persisted.getName(), one.getName());
    }

    @Test
    public void findOne_shouldReturnNull() {
        Mockito.when(enclosureRepository.findOne(Mockito.any(Long.class))).thenReturn(null);

        EnclosureDTO persisted = enclosureService.findOne(TestObjectFactory.ID_FIELD);

        Assert.assertNull(persisted);
    }

    @Test
    public void delete_shouldNotThrowForValidProperty() {
        enclosureService.delete(TestObjectFactory.ID_FIELD);
    }

    @Test
    public void delete_shouldNotThrowForNull() {
        enclosureService.delete(null);
    }
}
