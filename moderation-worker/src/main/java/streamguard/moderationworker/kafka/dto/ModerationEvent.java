package streamguard.moderationworker.kafka.dto;

import streamguard.moderationworker.model.entity.SentimentStatus;
import streamguard.moderationworker.model.enums.ModerationStatus;

public record ModerationEvent(
        String text,
        ModerationStatus moderationStatus,
        Integer toxicity,
        boolean spam,
        SentimentStatus sentiment
) {
}
