package pro.patrykkrawczyk.zoocrudapi;

import pro.patrykkrawczyk.zoocrudapi.domain.Animal;
import pro.patrykkrawczyk.zoocrudapi.domain.Enclosure;
import pro.patrykkrawczyk.zoocrudapi.domain.Species;
import pro.patrykkrawczyk.zoocrudapi.service.dto.AnimalDTO;
import pro.patrykkrawczyk.zoocrudapi.service.dto.EnclosureDTO;
import pro.patrykkrawczyk.zoocrudapi.service.dto.SpeciesDTO;

import java.util.HashSet;

/**
 * This factory ensures usage of uniformly created objects for testing purposes.
 */
public class TestObjectFactory {

    public static final Long ID_FIELD = 1L;
    public static final Long ID_MODIFIED_FIELD = 2L;

    public static final String NAME_FIELD = "NAME_FIELD";
    public static final String NAME_MODIFIED_FIELD = "NAME_MODIFIED_FIELD";

    public static Species Species() {
        return Species.builder()
                .id(null)
                .name(NAME_FIELD)
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

    public static Enclosure Enclosure() {
        return Enclosure.builder()
                .id(null)
                .name(NAME_FIELD)
                .animals(new HashSet<>())
                .build();
    }

    public static SpeciesDTO SpeciesDTO() {
        return (SpeciesDTO) produceDTORelationship(SpeciesDTO.class);
    }

    public static EnclosureDTO EnclosureDTO() {
        return (EnclosureDTO) produceDTORelationship(EnclosureDTO.class);
    }

    public static AnimalDTO AnimalDTO() {
        return (AnimalDTO) produceDTORelationship(AnimalDTO.class);
    }

    private static Object produceDTORelationship(Class<?> dtoClass) {
        SpeciesDTO speciesDTO = SpeciesDTO.builder()
                .id(ID_FIELD)
                .name(NAME_FIELD)
                .build();

        AnimalDTO animalDTO = AnimalDTO.builder()
                .id(ID_FIELD)
                .name(NAME_FIELD)
                .speciesId(speciesDTO.getId())
                .speciesName(speciesDTO.getName())
                .build();

        EnclosureDTO enclosureDTO = EnclosureDTO.builder()
                .id(ID_FIELD)
                .name(NAME_FIELD)
                .speciesId(speciesDTO.getId())
                .speciesName(speciesDTO.getName())
                .build();

        animalDTO.setEnclosureId(enclosureDTO.getId());

        switch (dtoClass.getSimpleName()) {
            case "EnclosureDTO":
                return enclosureDTO;
            case "AnimalDTO":
                return animalDTO;
            case "SpeciesDTO":
                return speciesDTO;
            default:
                throw new RuntimeException("Invalid class argument. Valid: [EnclosureDTO, AnimalDTO, SpeciesDTO]");
        }
    }
}
