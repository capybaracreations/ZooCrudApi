package pro.patrykkrawczyk.zoocrudapi.service.mapper;

import org.mapstruct.Mapper;
import pro.patrykkrawczyk.zoocrudapi.domain.Species;
import pro.patrykkrawczyk.zoocrudapi.dto.SpeciesDTO;

/**
 * Mapper for the entity Species and its DTO SpeciesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SpeciesMapper extends EntityMapper<Species, SpeciesDTO> {

    default Species fromId(Long id) {
        if (id == null) {
            return null;
        }
        Species species = new Species();
        species.setId(id);
        return species;
    }
}
