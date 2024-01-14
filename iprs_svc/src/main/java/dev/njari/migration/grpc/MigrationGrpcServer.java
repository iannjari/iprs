package dev.njari.migration.grpc;

import dev.njari.migration.service.MigrationService;
import io.grpc.stub.StreamObserver;
import iprs.migration.v1.MigrationServiceGrpc;
import iprs.migration.v1.RecordMovementCmd;
import iprs.migration.v1.UpdateMovementCmd;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author njari_mathenge
 * on 17/12/2023.
 * github.com/iannjari
 */

@GrpcService
@RequiredArgsConstructor
public class MigrationGrpcServer extends MigrationServiceGrpc.MigrationServiceImplBase {

    private final MigrationService migrationService;

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void recordMovement(RecordMovementCmd request, StreamObserver<RecordMovementCmd> responseObserver) {
        RecordMovementCmd response =  migrationService.recordMovement(request);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void updateMovement(UpdateMovementCmd request, StreamObserver<UpdateMovementCmd> responseObserver) {
        UpdateMovementCmd response = migrationService.updateMovement(request);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
