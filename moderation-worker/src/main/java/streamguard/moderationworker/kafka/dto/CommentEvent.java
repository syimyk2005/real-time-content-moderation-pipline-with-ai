package streamguard.moderationworker.kafka.dto;

public record CommentEvent(
        String userId,
        String status,
        String message
) {}
