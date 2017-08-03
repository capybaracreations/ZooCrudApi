package pro.patrykkrawczyk.zoocrudapi.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pro.patrykkrawczyk.zoocrudapi.TestObjectFactory;
import pro.patrykkrawczyk.zoocrudapi.domain.Animal;
import pro.patrykkrawczyk.zoocrudapi.domain.Enclosure;
import pro.patrykkrawczyk.zoocrudapi.domain.Species;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AnimalRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private EnclosureRepository enclosureRepository;

    @Autowired
    private SpeciesRepository speciesRepository;

    private Species SPECIES = TestObjectFactory.Species();
    private Enclosure ENCLOSURE = TestObjectFactory.Enclosure();

    @Before
    public void beforeEachTest() {
        SPECIES = speciesRepository.saveAndFlush(SPECIES);

        ENCLOSURE.setSpecies(SPECIES);

        ENCLOSURE = enclosureRepository.saveAndFlush(ENCLOSURE);
    }

    @Test
    public void create_shouldPersistNewEntity() {
        List<Animal> before = animalRepository.findAll();
        Assert.assertEquals(0, before.size());

        Animal animal = TestObjectFactory.Animal();
        animal.setSpecies(SPECIES);
        animal.setEnclosure(ENCLOSURE);
        animalRepository.saveAndFlush(animal);

        List<Animal> after = animalRepository.findAll();
        Assert.assertEquals(1, after.size());
    }

    @Test(expected = ConstraintViolationException.class)
    public void create_shouldNotAllowNullSpecies() {
        Animal animal = TestObjectFactory.Animal();

        animal.setSpecies(null);
        animal.setEnclosure(ENCLOSURE);

        animalRepository.saveAndFlush(animal);
    }

    @Test(expected = ConstraintViolationException.class)
    public void create_shouldNotAllowNullEnclosure() {
        Animal animal = TestObjectFactory.Animal();

        animal.setSpecies(SPECIES);
        animal.setEnclosure(null);

        animalRepository.saveAndFlush(animal);
    }

    @Test(expected = ConstraintViolationException.class)
    public void create_shouldNotAllowNullName() {
        Animal animal = TestObjectFactory.Animal();

        animal.setName(null);
        animal.setSpecies(SPECIES);
        animal.setEnclosure(ENCLOSURE);

        animalRepository.saveAndFlush(animal);
    }

    @Test
    public void update_shouldUpdateExistingEntity() {
        Animal animal = TestObjectFactory.Animal();
        animal.setSpecies(SPECIES);
        animal.setEnclosure(ENCLOSURE);

        animalRepository.saveAndFlush(animal);

        Animal persisted = animalRepository.findAll().get(0);

        Animal modified = TestObjectFactory.AnimalModified();
        modified.setSpecies(SPECIES);
        modified.setEnclosure(ENCLOSURE);
        modified.setId(persisted.getId());

        animalRepository.saveAndFlush(modified);

        Assert.assertEquals(persisted.getId(), modified.getId());
        Assert.assertEquals(modified.getName(), persisted.getName());
    }

    @Test
    public void retrieve_shouldReturnPersistedEntity() {
        Animal animal = TestObjectFactory.Animal();
        animal.setSpecies(SPECIES);
        animal.setEnclosure(ENCLOSURE);

        animalRepository.saveAndFlush(animal);

        Animal persisted = animalRepository.findAll().get(0);

        Assert.assertNotNull(persisted.getId());
        Assert.assertEquals(animal.getName(), persisted.getName());
    }

    @Test
    public void retrieve_shouldReturnAllPersistedEntities() {
        Animal animalA = TestObjectFactory.Animal();
        animalA.setSpecies(SPECIES);
        animalA.setEnclosure(ENCLOSURE);

        Animal animalB = TestObjectFactory.Animal();
        animalB.setSpecies(SPECIES);
        animalB.setEnclosure(ENCLOSURE);

        Animal animalC = TestObjectFactory.Animal();
        animalC.setSpecies(SPECIES);
        animalC.setEnclosure(ENCLOSURE);

        List<Animal> animal = Arrays.asList(animalA, animalB, animalC);
        animalRepository.save(animal);

        List<Animal> persisted = animalRepository.findAll();

        Assert.assertEquals(animal.size(), persisted.size());
    }

    @Test
    public void delete_shouldDeletePersistedEntity() {
        Animal animal = TestObjectFactory.Animal();
        animal.setSpecies(SPECIES);
        animal.setEnclosure(ENCLOSURE);

        animalRepository.saveAndFlush(animal);

        List<Animal> before = animalRepository.findAll();
        Assert.assertEquals(1, before.size());

        Animal persisted = before.get(0);
        animalRepository.delete(persisted);

        List<Animal> after = animalRepository.findAll();
        Assert.assertEquals(0, after.size());
    }
}
