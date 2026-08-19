package streamguard.statsservice.kafka.dto;

import streamguard.statsservice.model.enums.ModerationStatus;
import streamguard.statsservice.model.enums.SentimentStatus;

public record ModerationEvent(
        String text,
        ModerationStatus moderationStatus,
        Integer toxicity,
        boolean spam,
        SentimentStatus sentiment
) {
}