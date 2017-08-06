package pro.patrykkrawczyk.zoocrudapi.mapper;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pro.patrykkrawczyk.zoocrudapi.service.mapper.EntityMapper;

import java.util.Arrays;
import java.util.List;

public abstract class EntityMapperTest<Entity, DTO, Mapper extends EntityMapper<Entity, DTO>> {

    @Autowired
    private Mapper mapper;
    private Entity entity;
    private DTO dto;

    protected abstract Entity getEntity();

    protected abstract DTO getDto();

    protected abstract void verifyEntity(Entity entity, DTO dto);

    protected abstract void verifyDto(Entity entity, DTO dto);

    @Before
    public void beforeEachTest() {
        entity = getEntity();
        dto = getDto();
    }

    @Test
    public void verifyEntityToDtoMapping() {
        DTO mappedDto = mapper.toDto(entity);
        verifyDto(entity, mappedDto);
    }

    @Test
    public void verifyDtoToEntityMapping() {
        Entity mappedEntity = mapper.toEntity(dto);
        verifyDto(mappedEntity, dto);
    }

    @Test
    public void verifyEntitiesToDtosMapping() {
        List<Entity> entities = Arrays.asList(entity);
        List<DTO> mappedDtos = mapper.toDto(entities);
        mappedDtos.forEach(dto -> verifyDto(entity, dto));
    }

    @Test
    public void verifyDtosToEntityMapping() {
        List<DTO> dtos = Arrays.asList(dto);
        List<Entity> mappedEntities = mapper.toEntity(dtos);
        mappedEntities.forEach(d -> verifyEntity(d, dto));
    }
}
