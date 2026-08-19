package streamguard.moderationworker.model.dto;

import streamguard.moderationworker.model.enums.SentimentStatus;

public record AiModerationVerdict(
        int toxicity,
        boolean spam,
        SentimentStatus sentiment
) {}
