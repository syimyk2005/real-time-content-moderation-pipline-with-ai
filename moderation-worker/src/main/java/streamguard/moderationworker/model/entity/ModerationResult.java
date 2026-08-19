package streamguard.moderationworker.model.entity;

import jakarta.persistence.*;
import lombok.*;
import streamguard.moderationworker.model.enums.ModerationStatus;
import streamguard.moderationworker.model.enums.SentimentStatus;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModerationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    private ModerationStatus moderationStatus;

    private Integer toxicity;

    private boolean spam;

    @Enumerated(EnumType.STRING)
    private SentimentStatus sentiment;
}