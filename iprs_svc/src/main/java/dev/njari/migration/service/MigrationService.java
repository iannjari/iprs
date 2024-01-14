package dev.njari.migration.service;

import dev.njari.migration.repository.MovementRepository;
import iprs.migration.v1.Movement;
import iprs.migration.v1.RecordMovementCmd;
import iprs.migration.v1.UpdateMovementCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MigrationService {

    private final MovementRepository movementRepo;

    public RecordMovementCmd recordMovement(RecordMovementCmd cmd) {
        // validate
        validate(cmd);
        // save
        Movement movement = movementRepo.save(cmd.getTemplate());
        return cmd.toBuilder()
                .setTemplate(movement)
                .build();
    }

    public UpdateMovementCmd updateMovement(UpdateMovementCmd cmd) {
        // validate
        validate(cmd);
        // update and save
        Movement movement = movementRepo.save(cmd.getTemplate());

        return cmd.toBuilder()
                .setTemplate(movement)
                .build();
    }

    private void validate(RecordMovementCmd cmd) {
        if (!cmd.hasTemplate()) throw new IllegalArgumentException("Command must have template!");
    }

    private void validate(UpdateMovementCmd cmd) {
        if (!cmd.hasTemplate()) throw new IllegalArgumentException("Command must have template!");
        if (!cmd.getTemplate().getId().isBlank()) throw new IllegalArgumentException("Id cannot be null");

    }
}