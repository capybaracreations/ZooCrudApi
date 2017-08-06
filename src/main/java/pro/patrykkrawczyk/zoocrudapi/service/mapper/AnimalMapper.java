package pro.patrykkrawczyk.zoocrudapi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pro.patrykkrawczyk.zoocrudapi.domain.Animal;
import pro.patrykkrawczyk.zoocrudapi.service.dto.AnimalDTO;

/**
 * Mapper for the entity Animal and its DTO AnimalDTO.
 */
@Mapper(componentModel = "spring", uses = {SpeciesMapper.class, EnclosureMapper.class,})
public interface AnimalMapper extends EntityMapper<Animal, AnimalDTO> {

    ping(source ="species.name", target ="speciesName")

    @Mapping(source = "enclosure.id", target = "enclosureId")
    AnimalDTO toDto(Animal animal);

    @Map
    @Mapping(source = "speciesId", target = "species")

    @Mapping(source = "enclosureId", target = "enclosure")
    Animal toEntity(AnimalDTO animalDTO);

    @Mapping(source = "species.id", target = "speciesId")


    default Animal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Animal animal = new Animal();
        animal.setId(id);
        return animal;
    }
}
