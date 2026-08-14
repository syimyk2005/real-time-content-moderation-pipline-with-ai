package streamguard.ingestservice.kafka.dto;

public record CommentEvent (
        String message,
        String status
) {}
