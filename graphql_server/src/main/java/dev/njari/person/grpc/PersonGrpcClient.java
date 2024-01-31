package dev.njari.person.grpc;

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
public class PersonGrpcClient{

    @Getter
    @GrpcClient("iprs_svc_client")
    PersonServiceGrpc.PersonServiceBlockingStub client;

    public RegisterBirthCmd registerBirth(RegisterBirthCmd cmd) {
        return client.registerBirth(cmd);
    }

    public UpdatePersonDetailsCmd updatePersonDetails(UpdatePersonDetailsCmd cmd) {
        return client.updatePersonDetails(cmd);
    }

    public RecordDeathCmd recordDeath(RecordDeathCmd cmd) {
        return client.recordDeath(cmd);
    }
}
