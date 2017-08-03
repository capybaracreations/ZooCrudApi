package pro.patrykkrawczyk.zoocrudapi;

import pro.patrykkrawczyk.zoocrudapi.domain.Animal;
import pro.patrykkrawczyk.zoocrudapi.domain.Enclosure;
import pro.patrykkrawczyk.zoocrudapi.domain.Species;

import java.util.HashSet;

/**
 * This factory ensures usage of uniformly created objects for testing purposes.
 */
public class TestObjectFactory {

    public static final String NAME_FIELD = "NAME_FIELD";
    public static final String NAME_MODIFIED_FIELD = "NAME_MODIFIED_FIELD";

    public static Species Species() {
        return Species.builder()
                .id(null)
                .name(NAME_FIELD)
                .build();
    }

    public static Species SpeciesModified() {
        return Species.builder()
                .id(null)
                .name(NAME_MODIFIED_FIELD)
                .build();
    }

    public static Animal Animal() {
        return Animal.builder()
                .id(null)
                .name(NAME_FIELD)
                .enclosure(null)
                .species(null)
                .build();
    }

    public static Animal AnimalModified() {
        return Animal.builder()
                .id(null)
                .name(NAME_MODIFIED_FIELD)
                .enclosure(null)
                .species(null)
                .build();
    }

    public static Enclosure Enclosure() {
        return Enclosure.builder()
                .id(null)
                .name(NAME_FIELD)
                .animals(new HashSet<>())
                .build();
    }

    public static Enclosure EnclosureModified() {
        return Enclosure.builder()
                .id(null)
                .name(NAME_MODIFIED_FIELD)
                .animals(new HashSet<>())
                .build();
    }
}
