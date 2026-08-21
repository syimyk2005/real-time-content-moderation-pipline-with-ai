package streamguard.statsservice.model.dto;

import java.util.List;

public record MinuteStats(
        String minute,          // "2026-08-19T22:07" — какая это минута
        long approved,
        long rejected,
        long needsReview,
        double avgToxicity,
        double spamRatio,
        List<TopViolator> topViolators,
        long total
) {}