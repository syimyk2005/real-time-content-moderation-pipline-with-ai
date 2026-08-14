package streamguard.moderationworker.mapper;

import org.mapstruct.Mapper;
import streamguard.moderationworker.kafka.dto.ModerationEvent;
import streamguard.moderationworker.model.entity.ModerationResult;

@Mapper(componentModel = "spring")
public interface ModerationMapper {

    ModerationEvent toModerationEvent(ModerationResult moderationResult);
}
