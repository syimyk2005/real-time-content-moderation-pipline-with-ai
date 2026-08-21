package streamguard.moderationworker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import streamguard.moderationworker.kafka.dto.CommentEvent;
import streamguard.moderationworker.kafka.dto.ModerationEvent;
import streamguard.moderationworker.kafka.producer.ModeratingProducer;
import streamguard.moderationworker.mapper.ModerationMapper;
import streamguard.moderationworker.model.dto.AiModerationVerdict;
import streamguard.moderationworker.model.entity.ModerationResult;
import streamguard.moderationworker.model.enums.ModerationStatus;
import streamguard.moderationworker.repository.ModerationResultRepository;

@RequiredArgsConstructor
@Service
public class ModerationMiddleWare {

    private final AiModerationService aiModerationService;
    private final ModeratingProducer moderatingProducer;
    private final ModerationResultRepository moderationResultRepository;
    private final ModerationMapper moderationMapper;

    public void saveDBAndPublishToTopic(CommentEvent commentEvent) {
        AiModerationVerdict aiModerationVerdict = aiModerationService.moderate(commentEvent.message());
        ModerationResult moderationResult = ModerationResult.builder()
                .userId(commentEvent.userId())
                .text(commentEvent.message())
                .moderationStatus(resolveStatus(aiModerationVerdict))
                .toxicity(aiModerationVerdict.toxicity())
                .spam(aiModerationVerdict.spam())
                .sentiment(aiModerationVerdict.sentiment())
                .build();

        ModerationEvent moderationEvent  = moderationMapper.toModerationEvent(moderationResult);
        moderationResultRepository.save(moderationResult);
        moderatingProducer.send(String.valueOf(moderationResult.getId()), moderationEvent);
    }

    public ModerationStatus resolveStatus(AiModerationVerdict aiModerationVerdict) {
        if (aiModerationVerdict.spam() || aiModerationVerdict.toxicity() >= 80) {
            return ModerationStatus.REJECTED;
        } else if (aiModerationVerdict.toxicity() >= 50) {
            return ModerationStatus.NEEDS_REVIEW;
        } else {
            return ModerationStatus.APPROVED;
        }
    }

}
