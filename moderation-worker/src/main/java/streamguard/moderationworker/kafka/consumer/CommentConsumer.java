package streamguard.moderationworker.kafka.consumer;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import streamguard.moderationworker.kafka.dto.CommentEvent;
import streamguard.moderationworker.service.ModerationMiddleWare;

@RequiredArgsConstructor
@Component
public class CommentConsumer {

    private static final Logger log = LoggerFactory.getLogger(CommentConsumer.class);

    private final ModerationMiddleWare moderationMiddleWare;

    @KafkaListener(topics = "comments.incoming", groupId = "comment-service")
    public void consume(CommentEvent commentEvent, @Header(KafkaHeaders.RECEIVED_KEY) String commentId) {
        moderationMiddleWare.saveDBAndPublishToTopic(commentEvent);

        log.info("Received comment {}: {}", commentId, commentEvent);
    }
}