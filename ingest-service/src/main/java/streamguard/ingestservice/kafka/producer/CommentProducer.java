package streamguard.ingestservice.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentProducer {

    private final KafkaTemplate<UUID, Object> kafkaTemplate;

    public void send(UUID key, Object message) {
        kafkaTemplate.send("comments.incoming", key, message)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Не удалось отправить в Kafka: {}", ex.getMessage());
                    } else {
                        log.debug("Отправлено, offset={}", result.getRecordMetadata().offset());
                    }
                });
    }
}
