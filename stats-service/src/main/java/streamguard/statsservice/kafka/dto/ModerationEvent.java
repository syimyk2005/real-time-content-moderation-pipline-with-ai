package streamguard.statsservice.kafka.dto;

public record ModerationEvent(
        String text,
        ModerationStatus moderationStatus,
        Integer toxicity,
        boolean spam,
        SentimentStatus sentiment
) {
}