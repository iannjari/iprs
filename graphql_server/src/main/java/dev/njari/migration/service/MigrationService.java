package dev.njari.migration.service;

import com.netflix.dgs.codegen.generated.types.*;
import dev.njari.migration.grpc.MigrationGrpcClient;
import iprs.document.v1.DocumentType;
import iprs.migration.v1.Movement;
import iprs.migration.v1.MovementType;
import iprs.migration.v1.RecordMovementCmd;
import iprs.migration.v1.UpdateMovementCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author njari_mathenge
 * on 31/01/2024.
 * github.com/iannjari
 */

@Service
@RequiredArgsConstructor
public class MigrationService {

    private final MigrationGrpcClient migrationGrpcClient;

    /**
     * Record Movement
     * @param request - graphql request
     * @return - RecordMovementResponse
     */
    public RecordMovementResponse recordMovement(RecordMovementRequest request) {
        // validate the incoming payload
        validate(request);

        // map the graphql type to protobuf
        RecordMovementCmd cmd = map(request);

        // make call to server
        cmd = migrationGrpcClient.recordMovement(cmd);

        // map to graphql response and return
        return map(cmd);
    }

    /**
     * Update Movement
     * @param request - graphql request
     * @return - UpdateMovementResponse
     */
    public UpdateMovementResponse updateMovement(UpdateMovementRequest request) {
        // validate the incoming payload
        validate(request);

        // map the graphql type to protobuf
        UpdateMovementCmd cmd = map(request);

        // make call to server
        cmd = migrationGrpcClient.updateMovement(cmd);

        // map to graphql response and return
        return map(cmd);
    }

    /**
     * Validate incoming payload
     * @param request - RecordMovementRequest
     */
    private void validate(RecordMovementRequest request) {
        if (Objects.isNull(request.getTemplate().getType())) throw new RuntimeException("Type must be provided!");
        if (Objects.isNull(request.getTemplate().getCountry())) throw new RuntimeException("Country must be provided!");
        if (Objects.isNull(request.getTemplate().getDocumentId())) throw new RuntimeException("Document id cannot be null!");
        if (Objects.isNull(request.getTemplate().getDocumentUsed())) throw new RuntimeException("Document used cannot be null!");
        if (Objects.isNull(request.getTemplate().getIprsSvcId())) throw new RuntimeException("IPRS ID can be blank but not null!");
        if (Objects.isNull(request.getTemplate().getPortOfEntryOrExit())) throw new RuntimeException("PoEoE must be provided!");
        if (request.getTemplate().getCountry().isBlank()) throw new RuntimeException("Country must be provided!");
        if (request.getTemplate().getPortOfEntryOrExit().isBlank()) throw new RuntimeException("PoEoE must be provided!");
        if (request.getTemplate().getIprsSvcId().isBlank()) throw new RuntimeException("IPRS ID must be provided!");
    }

    /**
     * Validate incoming payload
     * @param request - UpdateMovementRequest
     */
    private void validate(UpdateMovementRequest request) {
        if (Objects.isNull(request.getTemplate().getType())) throw new RuntimeException("Type must be provided!");
        if (Objects.isNull(request.getTemplate().getCountry())) throw new RuntimeException("Country must be provided!");
        if (Objects.isNull(request.getTemplate().getDocumentId())) throw new RuntimeException("Document id cannot be null!");
        if (Objects.isNull(request.getTemplate().getDocumentUsed())) throw new RuntimeException("Document used cannot be null!");
        if (Objects.isNull(request.getTemplate().getIprsSvcId())) throw new RuntimeException("IPRS ID can be blank but not null!");
        if (Objects.isNull(request.getTemplate().getPortOfEntryOrExit())) throw new RuntimeException("PoEoE must be provided!");
        if (Objects.isNull(request.getTemplate().getId())) throw new RuntimeException("IPRS ID must be provided");
        if (request.getTemplate().getCountry().isBlank()) throw new RuntimeException("Country must be provided!");
        if (request.getTemplate().getPortOfEntryOrExit().isBlank()) throw new RuntimeException("PoEoE must be provided!");
        if (request.getTemplate().getId().isBlank()) throw new RuntimeException("Id must be provided!");
        if (request.getTemplate().getIprsSvcId().isBlank()) throw new RuntimeException("IPRS ID must be provided!");
    }

    /**
     * Convert RecordMovementRequest (graphql) to RecordMovementCmd (protobuf)
     * @param request - RecordMovementRequest
     * @return - RecordMovementCmd
     */
    private RecordMovementCmd map(RecordMovementRequest request) {

        return RecordMovementCmd.newBuilder()
                .setTemplate(Movement.newBuilder()
                                .setType(MovementType.valueOf(request.getTemplate().getType().name()))
                        .setIprsSvcId(request.getTemplate().getIprsSvcId())
                        .setDocumentUsed(DocumentType.valueOf(request.getTemplate().getDocumentUsed().name()))
                        .setDocumentId(request.getTemplate().getDocumentId())
                        .setCountry(request.getTemplate().getCountry())
                        .setPortOfEntryOrExit(request.getTemplate().getPortOfEntryOrExit())
                .build()).build();
    }

    /**
     * Convert UpdateMovementRequest (graphql) to UpdateMovementCmd (protobuf)
     * @param request - UpdateMovementRequest
     * @return - UpdateMovementCmd
     */
    private UpdateMovementCmd map(UpdateMovementRequest request) {

        return UpdateMovementCmd.newBuilder()
                .setTemplate(Movement.newBuilder()
                        .setId(request.getTemplate().getId())
                        .setType(MovementType.valueOf(request.getTemplate().getType().name()))
                        .setIprsSvcId(request.getTemplate().getIprsSvcId())
                        .setDocumentUsed(DocumentType.valueOf(request.getTemplate().getDocumentUsed().name()))
                        .setDocumentId(request.getTemplate().getDocumentId())
                        .setCountry(request.getTemplate().getCountry())
                        .setPortOfEntryOrExit(request.getTemplate().getPortOfEntryOrExit())
                        .build()).build();
    }

    /**
     * Convert RecordMovementCmd (protobuf) to RecordMovementResponse (graphql)
     * @param cmd - RecordMovementCmd
     * @return - RecordMovementResponse
     */
    private RecordMovementResponse map(RecordMovementCmd cmd) {

        return RecordMovementResponse.newBuilder()
                .template(com.netflix.dgs.codegen.generated.types.Movement.newBuilder()
                        .id(cmd.getTemplate().getId())
                        .type(com.netflix.dgs.codegen.generated.types.MovementType
                                .valueOf(cmd.getTemplate().getType().name()))
                        .iprsSvcId(cmd.getTemplate().getIprsSvcId())
                        .documentUsed(com.netflix.dgs.codegen.generated.types.DocumentType
                                .valueOf(cmd.getTemplate().getDocumentUsed().name()))
                        .documentId(cmd.getTemplate().getDocumentId())
                        .country(cmd.getTemplate().getCountry())
                        .portOfEntryOrExit(cmd.getTemplate().getPortOfEntryOrExit())
                        .build())
                .build();
    }


    /**
     * Convert UpdateMovementCmd (protobuf) to UpdateMovementResponse (graphql)
     * @param cmd - UpdateMovementCmd
     * @return - UpdateMovementResponse
     */
    private UpdateMovementResponse map(UpdateMovementCmd cmd) {

        return UpdateMovementResponse.newBuilder()
                .template(com.netflix.dgs.codegen.generated.types.Movement.newBuilder()
                        .id(cmd.getTemplate().getId())
                        .type(com.netflix.dgs.codegen.generated.types.MovementType
                                .valueOf(cmd.getTemplate().getType().name()))
                        .iprsSvcId(cmd.getTemplate().getIprsSvcId())
                        .documentUsed(com.netflix.dgs.codegen.generated.types.DocumentType
                                .valueOf(cmd.getTemplate().getDocumentUsed().name()))
                        .documentId(cmd.getTemplate().getDocumentId())
                        .country(cmd.getTemplate().getCountry())
                        .portOfEntryOrExit(cmd.getTemplate().getPortOfEntryOrExit())
                        .build())
                .build();
    }
}
