package pro.patrykkrawczyk.zoocrudapi.mapper;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.patrykkrawczyk.zoocrudapi.TestObjectFactory;
import pro.patrykkrawczyk.zoocrudapi.domain.Animal;
import pro.patrykkrawczyk.zoocrudapi.domain.Enclosure;
import pro.patrykkrawczyk.zoocrudapi.domain.Species;
import pro.patrykkrawczyk.zoocrudapi.service.dto.AnimalDTO;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.AnimalMapper;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.AnimalMapperImpl;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.EnclosureMapperImpl;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.SpeciesMapperImpl;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AnimalMapperTest.class, AnimalMapperImpl.class, SpeciesMapperImpl.class, EnclosureMapperImpl.class})
public class AnimalMapperTest extends EntityMapperTest<Animal, AnimalDTO, AnimalMapper> {

    @Override
    protected Animal getEntity() {
        Animal animal = TestObjectFactory.Animal();
        animal.setId(TestObjectFactory.ID_FIELD);

        Enclosure enclosure = TestObjectFactory.Enclosure();
        enclosure.setId(TestObjectFactory.ID_FIELD);

        Species species = TestObjectFactory.Species();
        species.setId(TestObjectFactory.ID_FIELD);

        animal.setEnclosure(enclosure);
        animal.setSpecies(species);

        return animal;
    }

    @Override
    protected AnimalDTO getDto() {
        return TestObjectFactory.AnimalDTO();
    }

    @Override
    protected void verifyEntity(Animal animal, AnimalDTO animalDTO) {
        assertEquals(animal.getId(), animalDTO.getId());
        assertEquals(animal.getName(), animalDTO.getName());
        assertEquals(animal.getSpecies().getId(), animalDTO.getSpeciesId());
        assertEquals(animal.getEnclosure().getId(), animalDTO.getEnclosureId());
    }

    @Override
    protected void verifyDto(Animal animal, AnimalDTO animalDTO) {
        assertEquals(animal.getId(), animalDTO.getId());
        assertEquals(animal.getName(), animalDTO.getName());
        assertEquals(animal.getSpecies().getId(), animalDTO.getSpeciesId());
        assertEquals(animal.getEnclosure().getId(), animalDTO.getEnclosureId());
    }
}
