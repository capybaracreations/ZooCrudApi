package pro.patrykkrawczyk.zoocrudapi.mapper;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.patrykkrawczyk.zoocrudapi.TestObjectFactory;
import pro.patrykkrawczyk.zoocrudapi.domain.Species;
import pro.patrykkrawczyk.zoocrudapi.service.dto.SpeciesDTO;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.SpeciesMapper;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.SpeciesMapperImpl;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpeciesMapperTest.class, SpeciesMapperImpl.class})
public class SpeciesMapperTest extends EntityMapperTest<Species, SpeciesDTO, SpeciesMapper> {

    @Override
    protected Species getEntity() {
        Species species = TestObjectFactory.Species();
        species.setId(TestObjectFactory.ID_FIELD);

        return species;
    }

    @Override
    protected SpeciesDTO getDto() {
        return TestObjectFactory.SpeciesDTO();
    }

    @Override
    protected void verifyEntity(Species species, SpeciesDTO speciesDto) {
        assertEquals(species.getId(), speciesDto.getId());
        assertEquals(species.getName(), speciesDto.getName());
    }

    @Override
    protected void verifyDto(Species species, SpeciesDTO speciesDto) {
        assertEquals(species.getId(), speciesDto.getId());
        assertEquals(species.getName(), speciesDto.getName());
    }
}
