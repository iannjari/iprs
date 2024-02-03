package dev.njari.person.service;

import com.netflix.dgs.codegen.generated.types.*;
import dev.njari.person.grpc.PersonGrpcClient;
import iprs.document.v1.DocumentType;
import iprs.migration.v1.Movement;
import iprs.migration.v1.MovementType;
import iprs.migration.v1.RecordMovementCmd;
import iprs.migration.v1.UpdateMovementCmd;
import iprs.person.v1.RegisterBirthCmd;
import iprs.person.v1.UpdatePersonDetailsCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * @author njari_mathenge
 * on 31/01/2024.
 * github.com/iannjari
 */

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonGrpcClient personGrpcClient;

    /**
     * Register a birth
     * @param request - graphql request
     * @return - RegisterBirthResponse
     */
    public RegisterBirthResponse registerBirth(RegisterBirthRequest request) {
        // validate the incoming payload
        validate(request);

        // map the graphql type to protobuf
        RegisterBirthCmd cmd = map(request);

        // make call to server
        cmd = personGrpcClient.registerBirth(cmd);

        // map to graphql response and return
        return map(cmd);
    }

    /**
     * Update Person details
     * @param request - graphql request
     * @return - UpdatePersonDetailsResponse
     */
    public UpdatePersonDetailsResponse updatePersonDetails(UpdatePersonDetailsRequest request) {
        // validate the incoming payload
        validate(request);

        // map the graphql type to protobuf
        UpdatePersonDetailsCmd cmd = map(request);

        // make call to server
        cmd = personGrpcClient.updatePersonDetails(cmd);

        // map to graphql response and return
        return map(cmd);
    }

    /**
     * Validate incoming payload
     * @param request - RegisterBirthRequest
     */
    private void validate(RegisterBirthRequest request) {

    }

    /**
     * Validate incoming payload
     * @param request - UpdatePersonDetailsRequest
     */
    private void validate(UpdatePersonDetailsRequest request) {

    }

    /**
     * Convert RegisterBirthRequest (graphql) to RegisterBirthCmd (protobuf)
     * @param request - RegisterBirthRequest
     * @return - RegisterBirthCmd
     */
    private RegisterBirthCmd map(RegisterBirthRequest request) {

            return RegisterBirthCmd.newBuilder()
                .setTemplate(iprs.person.v1.Person.newBuilder()
                        .setIsAlive(request.getTemplate().getIsAlive())
//                        .setDateOfDeath()
//                        .setDateOfBirth(request.getTemplate().getI)
                        .setFirstName(request.getTemplate().getFirstName())
                        .setLastName(request.getTemplate().getLastName())
                        .setOtherNames(request.getTemplate().getOtherNames())
                        .build())
                    .build();
    }

    /**
     * Convert UpdatePersonDetailsRequest (graphql) to UpdatePersonDetailsCmd (protobuf)
     * @param request - UpdatePersonDetailsRequest
     * @return - UpdatePersonDetailsCmd
     */
    private UpdatePersonDetailsCmd map(UpdatePersonDetailsRequest request) {

            return UpdatePersonDetailsCmd.newBuilder()
                .setTemplate(iprs.person.v1.Person.newBuilder()
                        .setIsAlive(request.getTemplate().getIsAlive())
//                        .setDateOfDeath()
//                        .setDateOfBirth(request.getTemplate().getI)
                        .setIprsDetails(
                                iprs.person.v1.IprsDetails.newBuilder()
                                        .setBirthCertificateNumber(request.getTemplate().getIprsDetails().getBirthCertificateNumber())
                                        .setWorkPermitNumber(request.getTemplate().getIprsDetails().getWorkPermitNumber())
                                        .setNationalIdNumber(request.getTemplate().getIprsDetails().getNationalIdNumber())
                                        .setPassportNumber(request.getTemplate().getIprsDetails().getPassportNumber())
//                                        .setDateOfRegistration(request.getTemplate().getIprsDetails().getDateOfRegistration())
                                        .setResidencyPermitNumber(request.getTemplate().getIprsDetails().getResidencyPermitNumber())
                                        .build()
                        )
                        .setFirstName(request.getTemplate().getFirstName())
                        .setLastName(request.getTemplate().getLastName())
                        .setOtherNames(request.getTemplate().getOtherNames())
                        .build()).build();
    }

    /**
     * Convert RegisterBirthCmd (protobuf) to RegisterBirthResponse (graphql)
     * @param cmd - RegisterBirthCmd
     * @return - RegisterBirthResponse
     */
    private RegisterBirthResponse map(RegisterBirthCmd cmd) {

            return RegisterBirthResponse.newBuilder()
                    .person(Person.newBuilder()
                        .dateOfBirth(Instant.ofEpochSecond(cmd.getTemplate().getDateOfBirth().getSeconds()).toString())
                        .dateOfDeath(Instant.ofEpochSecond(cmd.getTemplate().getDateOfDeath().getSeconds()).toString())
                        .firstName(cmd.getTemplate().getFirstName())
                        .iprsDetails(IprsDetails.newBuilder()
                                .birthCertificateNumber(cmd.getTemplate().getIprsDetails().getBirthCertificateNumber())
                                .workPermitNumber(cmd.getTemplate().getIprsDetails().getWorkPermitNumber())
                                .nationalIdNumber(cmd.getTemplate().getIprsDetails().getNationalIdNumber())
                                .passportNumber(cmd.getTemplate().getIprsDetails().getPassportNumber())
                                .dateOfRegistration(String.valueOf(Instant.ofEpochSecond(cmd.getTemplate().getIprsDetails().getDateOfRegistration().getSeconds())))
                                .residencyPermitNumber(cmd.getTemplate().getIprsDetails().getResidencyPermitNumber())
                                .build())
                        .id(cmd.getTemplate().getId())
                        .lastName(cmd.getTemplate().getLastName())
                        .isAlive(cmd.getTemplate().getIsAlive())
                        .otherNames(cmd.getTemplate().getOtherNames())
                        .build())
                .build();
    }


    /**
     * Convert UpdatePersonDetailsCmd (protobuf) to UpdatePersonDetailsResponse (graphql)
     * @param cmd - UpdatePersonDetailsCmd
     * @return - UpdatePersonDetailsResponse
     */
    private UpdatePersonDetailsResponse map(UpdatePersonDetailsCmd cmd) {

            return UpdatePersonDetailsResponse.newBuilder()
            .template(
                    Person.newBuilder()
                    .dateOfBirth(Instant.ofEpochSecond(cmd.getTemplate().getDateOfBirth().getSeconds()).toString())
                    .dateOfDeath(Instant.ofEpochSecond(cmd.getTemplate().getDateOfDeath().getSeconds()).toString())
                    .firstName(cmd.getTemplate().getFirstName())
                    .iprsDetails(IprsDetails.newBuilder()
                            .birthCertificateNumber(cmd.getTemplate().getIprsDetails().getBirthCertificateNumber())
                            .workPermitNumber(cmd.getTemplate().getIprsDetails().getWorkPermitNumber())
                            .nationalIdNumber(cmd.getTemplate().getIprsDetails().getNationalIdNumber())
                            .passportNumber(cmd.getTemplate().getIprsDetails().getPassportNumber())
                            .dateOfRegistration(String.valueOf(Instant.ofEpochSecond(cmd.getTemplate().getIprsDetails().getDateOfRegistration().getSeconds())))
                            .residencyPermitNumber(cmd.getTemplate().getIprsDetails().getResidencyPermitNumber())
                            .build())
                    .id(cmd.getTemplate().getId())
                    .lastName(cmd.getTemplate().getLastName())
                    .isAlive(cmd.getTemplate().getIsAlive())
                    .otherNames(cmd.getTemplate().getOtherNames())
                    .build()
                    )
                .build();
    }
}
