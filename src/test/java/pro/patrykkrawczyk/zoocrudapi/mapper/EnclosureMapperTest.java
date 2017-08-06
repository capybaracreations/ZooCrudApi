package pro.patrykkrawczyk.zoocrudapi.mapper;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.patrykkrawczyk.zoocrudapi.TestObjectFactory;
import pro.patrykkrawczyk.zoocrudapi.domain.Enclosure;
import pro.patrykkrawczyk.zoocrudapi.domain.Species;
import pro.patrykkrawczyk.zoocrudapi.service.dto.EnclosureDTO;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.AnimalMapperImpl;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.EnclosureMapper;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.EnclosureMapperImpl;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.SpeciesMapperImpl;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EnclosureMapperTest.class, EnclosureMapperImpl.class, AnimalMapperImpl.class, SpeciesMapperImpl.class})
public class EnclosureMapperTest extends EntityMapperTest<Enclosure, EnclosureDTO, EnclosureMapper> {

    @Override
    protected Enclosure getEntity() {
        Enclosure enclosure = TestObjectFactory.Enclosure();
        enclosure.setId(TestObjectFactory.ID_FIELD);

        Species species = TestObjectFactory.Species();
        enclosure.setSpecies(species);

        return enclosure;
    }

    @Override
    protected EnclosureDTO getDto() {
        return TestObjectFactory.EnclosureDTO();
    }

    @Override
    protected void verifyEntity(Enclosure enclosure, EnclosureDTO enclosureDTO) {
        assertEquals(enclosure.getId(), enclosureDTO.getId());
        assertEquals(enclosure.getName(), enclosureDTO.getName());
        assertEquals(enclosure.getSpecies().getId(), enclosureDTO.getSpeciesId());
    }

    @Override
    protected void verifyDto(Enclosure enclosure, EnclosureDTO enclosureDTO) {
        assertEquals(enclosure.getId(), enclosureDTO.getId());
        assertEquals(enclosure.getName(), enclosureDTO.getName());
        assertEquals(enclosure.getSpecies().getId(), enclosureDTO.getSpeciesId());
    }
}
