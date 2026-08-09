package streamguard.moderationworker.kafka.dto;

public record CommentEvent(
        String userId,
        String message,
        String status
) {}
