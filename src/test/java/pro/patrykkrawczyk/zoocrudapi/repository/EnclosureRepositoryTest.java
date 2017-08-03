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
import pro.patrykkrawczyk.zoocrudapi.domain.Enclosure;
import pro.patrykkrawczyk.zoocrudapi.domain.Species;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EnclosureRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private EnclosureRepository enclosureRepository;

    @Autowired
    private SpeciesRepository speciesRepository;

    private Species SPECIES = TestObjectFactory.Species();

    @Before
    public void beforeEachTest() {
        SPECIES = speciesRepository.saveAndFlush(SPECIES);
    }

    @Test
    public void create_shouldPersistNewEntity() {
        List<Enclosure> before = enclosureRepository.findAll();
        Assert.assertEquals(0, before.size());

        Enclosure enclosure = TestObjectFactory.Enclosure();
        enclosure.setSpecies(SPECIES);
        enclosureRepository.saveAndFlush(enclosure);

        List<Enclosure> after = enclosureRepository.findAll();
        Assert.assertEquals(1, after.size());
    }

    @Test(expected = ConstraintViolationException.class)
    public void create_shouldNotAllowNullSpecies() {
        Enclosure enclosure = TestObjectFactory.Enclosure();

        enclosure.setSpecies(null);

        enclosureRepository.saveAndFlush(enclosure);
    }

    @Test(expected = ConstraintViolationException.class)
    public void create_shouldNotAllowNullName() {
        Enclosure enclosure = TestObjectFactory.Enclosure();

        enclosure.setName(null);
        enclosure.setSpecies(SPECIES);

        enclosureRepository.saveAndFlush(enclosure);
    }

    @Test
    public void update_shouldUpdateExistingEntity() {
        Enclosure enclosure = TestObjectFactory.Enclosure();
        enclosure.setSpecies(SPECIES);
        enclosureRepository.saveAndFlush(enclosure);

        Enclosure persisted = enclosureRepository.findAll().get(0);

        Enclosure modified = TestObjectFactory.EnclosureModified();
        modified.setSpecies(SPECIES);
        modified.setId(persisted.getId());
        enclosureRepository.saveAndFlush(modified);

        Assert.assertEquals(persisted.getId(), modified.getId());
        Assert.assertEquals(modified.getName(), persisted.getName());
    }

    @Test
    public void retrieve_shouldReturnPersistedEntity() {
        Enclosure enclosure = TestObjectFactory.Enclosure();
        enclosure.setSpecies(SPECIES);
        enclosureRepository.saveAndFlush(enclosure);

        Enclosure persisted = enclosureRepository.findAll().get(0);

        Assert.assertNotNull(persisted.getId());
        Assert.assertEquals(enclosure.getName(), persisted.getName());
    }

    @Test
    public void retrieve_shouldReturnAllPersistedEntities() {
        Enclosure enclosureA = TestObjectFactory.Enclosure();
        enclosureA.setSpecies(SPECIES);

        Enclosure enclosureB = TestObjectFactory.Enclosure();
        enclosureB.setSpecies(SPECIES);

        Enclosure enclosureC = TestObjectFactory.Enclosure();
        enclosureC.setSpecies(SPECIES);

        List<Enclosure> enclosure = Arrays.asList(enclosureA, enclosureB, enclosureC);
        enclosureRepository.save(enclosure);

        List<Enclosure> persisted = enclosureRepository.findAll();

        Assert.assertEquals(enclosure.size(), persisted.size());
    }

    @Test
    public void delete_shouldDeletePersistedEntity() {
        Enclosure enclosure = TestObjectFactory.Enclosure();
        enclosure.setSpecies(SPECIES);
        enclosureRepository.saveAndFlush(enclosure);

        List<Enclosure> before = enclosureRepository.findAll();
        Assert.assertEquals(1, before.size());

        Enclosure persisted = before.get(0);
        enclosureRepository.delete(persisted);

        List<Enclosure> after = enclosureRepository.findAll();
        Assert.assertEquals(0, after.size());
    }
}
