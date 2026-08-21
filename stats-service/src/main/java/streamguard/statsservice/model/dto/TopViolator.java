package streamguard.statsservice.model.dto;

public record TopViolator(
        String userId,
        long violations
) {}