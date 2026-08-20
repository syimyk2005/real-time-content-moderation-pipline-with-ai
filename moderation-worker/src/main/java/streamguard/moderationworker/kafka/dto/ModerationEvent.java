package streamguard.moderationworker.kafka.dto;

import streamguard.moderationworker.model.enums.SentimentStatus;
import streamguard.moderationworker.model.enums.ModerationStatus;

import java.time.Instant;

public record ModerationEvent(
        String text,
        ModerationStatus moderationStatus,
        Integer toxicity,
        boolean spam,
        SentimentStatus sentiment,
        Instant timestamp
) {
}
