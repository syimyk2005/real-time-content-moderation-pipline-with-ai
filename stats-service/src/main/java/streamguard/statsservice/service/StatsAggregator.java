package streamguard.statsservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import streamguard.statsservice.kafka.dto.ModerationEvent;
import streamguard.statsservice.model.enums.ModerationStatus;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class StatsAggregator {

    private final StringRedisTemplate redis;
    private static final Duration TTL = Duration.ofMinutes(10); // держим 10 мин, потом сброс

    public void record(ModerationEvent e) {
        String minute = minuteKey(e.timestamp());            // "2026-08-19T22:07"
        String prefix = "stats:" + minute + ":";

        redis.opsForValue().increment(prefix + "total");
        redis.opsForValue().increment(prefix + "decision:" + e.moderationStatus().name());
        redis.opsForValue().increment(prefix + "toxicitySum", e.toxicity());
        if (e.spam()) {
            redis.opsForValue().increment(prefix + "spam");
        }
        if (e.moderationStatus() != ModerationStatus.APPROVED) {
            redis.opsForZSet().incrementScore(prefix + "violators", e.userId(), 1);
        }

        // TTL на ключи, чтобы старые минуты сами исчезали
        redis.expire(prefix + "total", TTL);
        redis.expire(prefix + "decision:" + e.decision(), TTL);
        redis.expire(prefix + "toxicitySum", TTL);
        redis.expire(prefix + "spam", TTL);
        redis.expire(prefix + "violators", TTL);
    }

    private String minuteKey(Instant ts) {
        return LocalDateTime.ofInstant(ts, ZoneOffset.UTC)
                .truncatedTo(ChronoUnit.MINUTES)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }
}
