package dev.njari.person.service;

import com.netflix.dgs.codegen.generated.types.*;
import dev.njari.person.grpc.PersonGrpcClient;
import dev.njari.util.DateConverter;
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
import java.util.Objects;

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
        if (Objects.isNull(request.getTemplate().getFirstName())) throw new RuntimeException("First name must be provided!");
        if (Objects.isNull(request.getTemplate().getOtherNames())) throw new RuntimeException("Other names can be blank but not null!");
        if (Objects.isNull(request.getTemplate().getLastName())) throw new RuntimeException("Last name can be blank but not null!");
        if (Objects.isNull(request.getTemplate().getIsAlive())) throw new RuntimeException("Alive/dead is required!");
        if (Objects.isNull(request.getTemplate().getDateOfBirth())) throw new RuntimeException("DoB must be provided!!");
        if (request.getTemplate().getFirstName().isBlank()) throw new RuntimeException("First name must be provided!");
        if (request.getTemplate().getDateOfBirth().isBlank()) throw new RuntimeException("DoB must be provided!");
    }

    /**
     * Validate incoming payload
     * @param request - UpdatePersonDetailsRequest
     */
    private void validate(UpdatePersonDetailsRequest request) {
        if (Objects.isNull(request.getTemplate().getFirstName())) throw new RuntimeException("First name must be provided!");
        if (Objects.isNull(request.getTemplate().getId())) throw new RuntimeException("Id must be provided!");
        if (Objects.isNull(request.getTemplate().getOtherNames())) throw new RuntimeException("Other names can be blank but not null!");
        if (Objects.isNull(request.getTemplate().getLastName())) throw new RuntimeException("Last name can be blank but not null!");
        if (Objects.isNull(request.getTemplate().getIsAlive())) throw new RuntimeException("Alive/dead is required!");
        if (Objects.isNull(request.getTemplate().getDateOfBirth())) throw new RuntimeException("DoB must be provided!!");
        if (request.getTemplate().getId().isBlank()) throw new RuntimeException("Id must be provided!");
        if (request.getTemplate().getFirstName().isBlank()) throw new RuntimeException("First name must be provided!");
        if (request.getTemplate().getDateOfBirth().isBlank()) throw new RuntimeException("DoB must be provided!");
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
                        .setDateOfDeath(DateConverter.StringToProtoDate.INSTANCE.convert(
                                request.getTemplate().getDateOfDeath()))
                        .setDateOfBirth(DateConverter.StringToProtoDate.INSTANCE.convert(
                                request.getTemplate().getDateOfBirth()))
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
                        .setDateOfDeath(DateConverter.StringToProtoDate.INSTANCE.convert(
                                request.getTemplate().getDateOfDeath()))
                        .setDateOfBirth(DateConverter.StringToProtoDate.INSTANCE.convert(
                                request.getTemplate().getDateOfBirth()))
                        .setIprsDetails(
                                iprs.person.v1.IprsDetails.newBuilder()
                                        .setBirthCertificateNumber(request.getTemplate().getIprsDetails().getBirthCertificateNumber())
                                        .setWorkPermitNumber(request.getTemplate().getIprsDetails().getWorkPermitNumber())
                                        .setNationalIdNumber(request.getTemplate().getIprsDetails().getNationalIdNumber())
                                        .setPassportNumber(request.getTemplate().getIprsDetails().getPassportNumber())
                                        .setDateOfRegistration(DateConverter.StringToProtoDate.INSTANCE.convert(
                                                request.getTemplate().getIprsDetails().getDateOfRegistration()))
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
                        .dateOfBirth(DateConverter.ProtoDateToString.INSTANCE.convert(cmd.getTemplate().getDateOfBirth()))
                        .dateOfDeath(DateConverter.ProtoDateToString.INSTANCE.convert(cmd.getTemplate().getDateOfDeath()))
                        .firstName(cmd.getTemplate().getFirstName())
                        .iprsDetails(IprsDetails.newBuilder()
                                .birthCertificateNumber(cmd.getTemplate().getIprsDetails().getBirthCertificateNumber())
                                .workPermitNumber(cmd.getTemplate().getIprsDetails().getWorkPermitNumber())
                                .nationalIdNumber(cmd.getTemplate().getIprsDetails().getNationalIdNumber())
                                .passportNumber(cmd.getTemplate().getIprsDetails().getPassportNumber())
                                .dateOfRegistration(DateConverter.ProtoDateToString.INSTANCE.convert(
                                        cmd.getTemplate().getIprsDetails().getDateOfRegistration()))
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
                    .dateOfBirth(DateConverter.ProtoDateToString.INSTANCE.convert(cmd.getTemplate().getDateOfBirth()))
                    .dateOfDeath(DateConverter.ProtoDateToString.INSTANCE.convert(cmd.getTemplate().getDateOfDeath()))
                    .firstName(cmd.getTemplate().getFirstName())
                    .iprsDetails(IprsDetails.newBuilder()
                            .birthCertificateNumber(cmd.getTemplate().getIprsDetails().getBirthCertificateNumber())
                            .workPermitNumber(cmd.getTemplate().getIprsDetails().getWorkPermitNumber())
                            .nationalIdNumber(cmd.getTemplate().getIprsDetails().getNationalIdNumber())
                            .passportNumber(cmd.getTemplate().getIprsDetails().getPassportNumber())
                            .dateOfRegistration(DateConverter.ProtoDateToString.INSTANCE.convert(
                                    cmd.getTemplate().getIprsDetails().getDateOfRegistration()))
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
