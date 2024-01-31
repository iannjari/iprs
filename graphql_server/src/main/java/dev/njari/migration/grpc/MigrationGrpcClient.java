package dev.njari.migration.grpc;

import iprs.migration.v1.MigrationServiceGrpc;
import iprs.migration.v1.RecordMovementCmd;
import iprs.migration.v1.UpdateMovementCmd;
import lombok.Getter;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * @author njari_mathenge
 * on 31/01/2024.
 * github.com/iannjari
 */

@Service
public class MigrationGrpcClient {

    @Getter
    @GrpcClient("iprs_svc_client")
    MigrationServiceGrpc.MigrationServiceBlockingStub migrationServiceBlockingStub;

    public RecordMovementCmd recordMovement(RecordMovementCmd cmd) {
        return migrationServiceBlockingStub.recordMovement(cmd);
    }
    public UpdateMovementCmd updateMovement(UpdateMovementCmd cmd) {
        return migrationServiceBlockingStub.updateMovement(cmd);
    }
}
