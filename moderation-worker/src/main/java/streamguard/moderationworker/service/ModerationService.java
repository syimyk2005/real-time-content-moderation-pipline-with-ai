package streamguard.moderationworker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import streamguard.moderationworker.kafka.consumer.CommentConsumer;
import streamguard.moderationworker.kafka.dto.CommentEvent;

@RequiredArgsConstructor
@Service
public class ModerationService {

    private final AiModerationService aiModerationService;
    private final CommentConsumer commentConsumer;

    private String saveAndPublish(CommentEvent commentEvent){
        return  "";
    }




}
