package streamguard.ingestservice.model.dto;

import java.util.UUID;

public record CommentResponse(
        UUID commentId,
        String message,
        String status
) {}
