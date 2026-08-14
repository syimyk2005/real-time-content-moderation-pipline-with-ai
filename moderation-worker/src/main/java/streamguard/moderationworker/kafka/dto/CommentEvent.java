package streamguard.moderationworker.kafka.dto;

public record CommentEvent(
        String message,
        String status
) {}
