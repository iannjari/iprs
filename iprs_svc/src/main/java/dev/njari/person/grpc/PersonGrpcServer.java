package dev.njari.person.grpc;

import dev.njari.person.service.BirthRegistrationService;
import io.grpc.stub.StreamObserver;
import iprs.person.v1.PersonServiceGrpc;
import iprs.person.v1.RecordDeathCmd;
import iprs.person.v1.RegisterBirthCmd;
import iprs.person.v1.UpdatePersonDetailsCmd;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author njari_mathenge
 * on 17/12/2023.
 * github.com/iannjari
 */

@GrpcService
@RequiredArgsConstructor
public class PersonGrpcServer extends PersonServiceGrpc.PersonServiceImplBase {

    private final BirthRegistrationService birthRegistrationService;

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void registerBirth(RegisterBirthCmd request, StreamObserver<RegisterBirthCmd> responseObserver) {
        RegisterBirthCmd response =  birthRegistrationService.registerBirth(request);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void updatePersonDetails(UpdatePersonDetailsCmd request, StreamObserver<UpdatePersonDetailsCmd> responseObserver) {
        super.updatePersonDetails(request, responseObserver);
    }

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void recordDeath(RecordDeathCmd request, StreamObserver<RecordDeathCmd> responseObserver) {
        super.recordDeath(request, responseObserver);
    }
}
