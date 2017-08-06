package pro.patrykkrawczyk.zoocrudapi.mapper;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.patrykkrawczyk.zoocrudapi.TestObjectFactory;
import pro.patrykkrawczyk.zoocrudapi.domain.Species;
import pro.patrykkrawczyk.zoocrudapi.dto.SpeciesDTO;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.SpeciesMapper;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.SpeciesMapperImpl;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpeciesMapperTest.class, SpeciesMapperImpl.class})
public class SpeciesMapperTest extends EntityMapperTest<Species, SpeciesDTO, SpeciesMapper> {

    @Override
    protected Species getEntity() {
        Species species = Species.builder()
                .id(TestObjectFactory.ID_FIELD)
                .name(TestObjectFactory.NAME_FIELD)
                .build();

        return species;
    }

    @Override
    protected SpeciesDTO getDto() {
        SpeciesDTO speciesDto = SpeciesDTO.builder()
                .id(TestObjectFactory.ID_FIELD)
                .name(TestObjectFactory.NAME_FIELD)
                .build();

        return speciesDto;
    }

    @Override
    protected void verifyDomain(Species species, SpeciesDTO speciesDto) {
        assertEquals(species.getId(), speciesDto.getId());
        assertEquals(species.getName(), speciesDto.getName());
    }

    @Override
    protected void verifyDto(Species species, SpeciesDTO speciesDto) {
        assertEquals(species.getId(), speciesDto.getId());
        assertEquals(species.getName(), speciesDto.getName());
    }
}
