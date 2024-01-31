package dev.njari.migration.grpc;

import iprs.migration.v1.MigrationServiceGrpc;
import iprs.migration.v1.RecordMovementCmd;
import iprs.migration.v1.UpdateMovementCmd;
import iprs.person.v1.PersonServiceGrpc;
import iprs.person.v1.RecordDeathCmd;
import iprs.person.v1.RegisterBirthCmd;
import iprs.person.v1.UpdatePersonDetailsCmd;
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
    MigrationServiceGrpc.MigrationServiceBlockingStub client;

    public RecordMovementCmd recordMovement(RecordMovementCmd cmd) {
        return client.recordMovement(cmd);
    }
    public UpdateMovementCmd recordMovement(UpdateMovementCmd cmd) {
        return client.updateMovement(cmd);
    }
}
