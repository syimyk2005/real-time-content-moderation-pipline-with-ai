package streamguard.statsservice.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.events.CommentEvent;

@Slf4j
@RequiredArgsConstructor
@Component
public class ModeratedCommentConsumer {

    @KafkaListener(topics = "comments.moderated", groupId = "comment-service")
    public void consume(CommentEvent commentEvent, @Header(KafkaHeaders.RECEIVED_KEY) String commentId) {
        log.info("Received comment {}: {}", commentId, commentEvent);
    }




}
