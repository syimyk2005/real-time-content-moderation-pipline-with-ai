package streamguard.statsservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import streamguard.statsservice.model.dto.MinuteStats;
import streamguard.statsservice.model.dto.TopViolator;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsPublisher {

    private final StringRedisTemplate redis;
    // private final SimpMessagingTemplate ws;  // подключим на шаге WebSocket

    @Scheduled(cron = "0 * * * * *")
    public void publishPreviousMinute() {
        String minute = LocalDateTime.now(ZoneOffset.UTC)
                .minusMinutes(1)
                .truncatedTo(ChronoUnit.MINUTES)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        String prefix = "stats:" + minute + ":";

        long total       = getLong(prefix + "total");
        if (total == 0) return;

        long approved    = getLong(prefix + "decision:APPROVED");
        long rejected    = getLong(prefix + "decision:REJECTED");
        long needsReview = getLong(prefix + "decision:NEEDS_REVIEW");
        long spam        = getLong(prefix + "spam");
        long toxSum      = getLong(prefix + "toxicitySum");

        Set<ZSetOperations.TypedTuple<String>> top =
                redis.opsForZSet().reverseRangeWithScores(prefix + "violators", 0, 4);

        List<TopViolator> violators = top == null ? List.of() :
                top.stream()
                   .map(t -> new TopViolator(t.getValue(),
                             t.getScore() == null ? 0 : t.getScore().longValue()))
                   .toList();

        MinuteStats stats = new MinuteStats(
                minute, approved, rejected, needsReview,
                (double) toxSum / total,        // средняя токсичность
                (double) spam / total,          // доля спама
                violators, total);

        log.info("Minute {} => {}", minute, stats);
        // ws.convertAndSend("/topic/stats", stats);   // следующий шаг
    }

    private long getLong(String key) {
        String v = redis.opsForValue().get(key);
        return v == null ? 0 : Long.parseLong(v);
    }
}