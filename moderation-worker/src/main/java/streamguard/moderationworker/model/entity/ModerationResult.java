package streamguard.moderationworker.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import streamguard.moderationworker.model.enums.ModerationStatus;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ModerationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String text;
    private String author;
    private ModerationStatus moderationStatus;
}
