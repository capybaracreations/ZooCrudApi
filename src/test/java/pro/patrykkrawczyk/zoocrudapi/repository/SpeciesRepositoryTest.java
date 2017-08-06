package pro.patrykkrawczyk.zoocrudapi.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pro.patrykkrawczyk.zoocrudapi.TestObjectFactory;
import pro.patrykkrawczyk.zoocrudapi.domain.Species;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SpeciesRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SpeciesRepository speciesRepository;

    @Test
    public void create_shouldPersistNewEntity() {
        List<Species> before = speciesRepository.findAll();
        Assert.assertEquals(0, before.size());

        Species species = TestObjectFactory.Species();
        speciesRepository.saveAndFlush(species);

        List<Species> after = speciesRepository.findAll();
        Assert.assertEquals(1, after.size());
    }

    @Test(expected = ConstraintViolationException.class)
    public void create_shouldNotAllowNullName() {
        Species species = TestObjectFactory.Species();

        species.setName(null);

        speciesRepository.saveAndFlush(species);
    }

    @Test
    public void update_shouldUpdateExistingEntity() {
        Species species = TestObjectFactory.Species();
        speciesRepository.saveAndFlush(species);

        Species persisted = speciesRepository.findAll().get(0);

        Species modified = Species.builder()
                .id(persisted.getId())
                .name(TestObjectFactory.NAME_MODIFIED_FIELD)
                .build();

        speciesRepository.saveAndFlush(modified);

        persisted = speciesRepository.findAll().get(0);

        Assert.assertEquals(persisted.getId(), modified.getId());
        Assert.assertEquals(modified.getName(), persisted.getName());
    }

    @Test
    public void retrieve_shouldReturnPersistedEntity() {
        Species species = TestObjectFactory.Species();
        speciesRepository.saveAndFlush(species);

        Species persisted = speciesRepository.findAll().get(0);

        Assert.assertNotNull(persisted.getId());
        Assert.assertEquals(species.getName(), persisted.getName());
    }

    @Test
    public void retrieve_shouldReturnAllPersistedEntities() {
        Species speciesA = TestObjectFactory.Species();
        Species speciesB = TestObjectFactory.Species();
        Species speciesC = TestObjectFactory.Species();

        List<Species> species = Arrays.asList(speciesA, speciesB, speciesC);
        speciesRepository.save(species);

        List<Species> persisted = speciesRepository.findAll();

        Assert.assertEquals(species.size(), persisted.size());
    }

    @Test
    public void delete_shouldDeletePersistedEntity() {
        Species species = TestObjectFactory.Species();
        speciesRepository.saveAndFlush(species);

        List<Species> before = speciesRepository.findAll();
        Assert.assertEquals(1, before.size());

        Species persisted = before.get(0);
        speciesRepository.delete(persisted.getId());

        List<Species> after = speciesRepository.findAll();
        Assert.assertEquals(0, after.size());
    }
}
