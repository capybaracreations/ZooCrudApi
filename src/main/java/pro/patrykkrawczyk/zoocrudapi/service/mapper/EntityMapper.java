package pro.patrykkrawczyk.zoocrudapi.service.mapper;

import java.util.List;

/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <ENTITY> - Entity type parameter.
 * @param <DTO>    - DTO type parameter.
 */
public interface EntityMapper<ENTITY, DTO> {

    public ENTITY toEntity(DTO dto);

    public DTO toDto(ENTITY entity);

    public List<ENTITY> toEntity(List<DTO> dtoList);

    public List<DTO> toDto(List<ENTITY> entityList);
}
