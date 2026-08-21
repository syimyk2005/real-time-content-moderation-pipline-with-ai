package streamguard.statsservice.kafka.dto;

import streamguard.statsservice.model.enums.ModerationStatus;
import streamguard.statsservice.model.enums.SentimentStatus;

import java.time.Instant;

public record ModerationEvent(
        String userId,
        String text,
        ModerationStatus moderationStatus,
        Integer toxicity,
        boolean spam,
        SentimentStatus sentiment,
        Instant timestamp
) {
}