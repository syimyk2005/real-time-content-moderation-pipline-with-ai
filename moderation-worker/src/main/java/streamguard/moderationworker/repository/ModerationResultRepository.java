package streamguard.moderationworker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import streamguard.moderationworker.model.entity.ModerationResult;

import java.util.UUID;

@Repository
public interface ModerationResultRepository extends JpaRepository<ModerationResult, UUID> {

}
