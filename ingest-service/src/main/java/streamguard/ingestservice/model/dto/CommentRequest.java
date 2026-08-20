package streamguard.ingestservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import streamguard.ingestservice.model.enums.CommentStatus;

public record CommentRequest(

        String text,

        @NotBlank
        @Size(max = 100, message = "Text should not exceed 100.")
        CommentStatus status,

        String userId
) {}
