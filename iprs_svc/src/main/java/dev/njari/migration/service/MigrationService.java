package dev.njari.migration.service;

import dev.njari.migration.repository.MovementRepository;
import iprs.migration.v1.Movement;
import iprs.migration.v1.RecordMovementCmd;
import iprs.migration.v1.UpdateMovementCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MigrationService {

    private final MovementRepository movementRepo;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("kafka.migration.topic")
    private String migrationTopic;

    public RecordMovementCmd recordMovement(RecordMovementCmd cmd) {
        // validate
        validate(cmd);
        // save
        Movement movement = movementRepo.save(cmd.getTemplate());
        kafkaTemplate.send(migrationTopic, movement.getId(), movement);

        return cmd.toBuilder()
                .setTemplate(movement)
                .build();
    }

    public UpdateMovementCmd updateMovement(UpdateMovementCmd cmd) {
        // validate
        validate(cmd);

        Movement movement = movementRepo.findById(cmd.getId());

        if (Objects.isNull(movement)) throw new RuntimeException("Movement with id not found!");

        // update and save
        Movement movement1 = movementRepo.save(cmd.getTemplate());
        kafkaTemplate.send(migrationTopic, movement1.getId(), movement1);

        return cmd.toBuilder()
                .setTemplate(movement1)
                .build();
    }

    private void validate(RecordMovementCmd cmd) {
        if (!cmd.hasTemplate()) throw new IllegalArgumentException("Command must have template!");
        if (cmd.getTemplate().getIprsSvcId().isBlank()) throw new IllegalArgumentException("Request must have an IPRS id!");
    }

    private void validate(UpdateMovementCmd cmd) {
        if (!cmd.hasTemplate()) throw new IllegalArgumentException("Command must have template!");
        if (cmd.getTemplate().getIprsSvcId().isBlank()) throw new IllegalArgumentException("Request must have an IPRS id!");
        if (!cmd.getTemplate().getId().isBlank()) throw new IllegalArgumentException("Id cannot be null");

    }
}