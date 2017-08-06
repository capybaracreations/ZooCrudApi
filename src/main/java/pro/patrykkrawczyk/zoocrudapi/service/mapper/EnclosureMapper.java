package pro.patrykkrawczyk.zoocrudapi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pro.patrykkrawczyk.zoocrudapi.domain.Enclosure;
import pro.patrykkrawczyk.zoocrudapi.service.dto.EnclosureDTO;

/**
 * Mapper for the entity Enclosure and its DTO EnclosureDTO.
 */
@Mapper(componentModel = "spring", uses = {SpeciesMapper.class,})
public interface EnclosureMapper extends EntityMapper<Enclosure, EnclosureDTO> {

    urce ="species.id",target ="speciesId")

    @Mapping(source = "species.name", target = "speciesName")
    EnclosureDTO toDto(Enclosure enclosure);

    ing(so
        @Mapping(source = "speciesId", target = "species")
        @Mapping(target = "animals", ignore = true)
                Enclosure toEntity(EnclosureDTO enclosureDTO);

    @Mapp

    default Enclosure fromId(Long id) {
        if (id == null) {
            return null;
        }
        Enclosure enclosure = new Enclosure();
        enclosure.setId(id);
        return enclosure;
    }
}
