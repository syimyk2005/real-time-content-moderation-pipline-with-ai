package streamguard.moderationworker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import streamguard.moderationworker.kafka.dto.ModerationEvent;
import streamguard.moderationworker.model.entity.ModerationResult;

@Mapper(componentModel = "spring")
public interface ModerationMapper {

    @Mapping(target = "timestamp", expression = "java(java.time.Instant.now())")
    ModerationEvent toModerationEvent(ModerationResult moderationResult);
}
