package dev.njari.document.service;

import com.netflix.dgs.codegen.generated.types.*;
import dev.njari.document.grpc.DocumentGrpcClient;
import dev.njari.util.DateConverter;
import iprs.document.v1.AmendDocumentCmd;
import iprs.document.v1.ApplyDocumentCmd;
import iprs.document.v1.DocumentType;
import iprs.document.v1.IssueDocumentCmd;
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
public class DocumentService {

    private final DocumentGrpcClient documentGrpcClient;

    /**
     * Apply for document
     * @param request - graphql application request
     * @return - ApplyDocumentResponse
     */
    public ApplyDocumentResponse applyForDocument(ApplyDocumentRequest request) {
        // validate the incoming payload
        validate(request);

        // map the graphql type to protobuf
        ApplyDocumentCmd cmd = map(request);

        // make call to server
        cmd = documentGrpcClient.applyForDocument(cmd);

        // map to graphql response and return
        return map(cmd);
    }

    /**
     * Amend document
     * @param request - graphql amend request
     * @return - ApplyDocumentResponse
     */
    public AmendDocumentResponse amendDocument(AmendDocumentRequest request) {
        // validate the incoming payload
        validate(request);

        // map the graphql type to protobuf
        AmendDocumentCmd cmd = map(request);

        // make call to server
        cmd = documentGrpcClient.amendDocument(cmd);

        // map to graphql response and return
        return map(cmd);
    }

    /**
     * Issue document
     * @param request - graphql issue request
     * @return - IssueDocumentResponse
     */
    public IssueDocumentResponse issueDocument(IssueDocumentRequest request) {
        // validate the incoming payload
        validate(request);

        // map the graphql type to protobuf
        IssueDocumentCmd cmd = map(request);

        // make call to server
        cmd = documentGrpcClient.issueDocument(cmd);

        // map to graphql response and return
        return map(cmd);
    }

    /**
     * Validate incoming payload
     * @param request - ApplyDocumentRequest
     */
    private void validate(ApplyDocumentRequest request) {

    }

    /**
     * Validate incoming payload
     * @param request - AmendDocumentRequest
     */
    private void validate(AmendDocumentRequest request) {

    }


    /**
     * Validate incoming payload
     * @param request - IssueDocumentRequest
     */
    private void validate(IssueDocumentRequest request) {
        if (request.getDocumentId().isBlank()) throw new RuntimeException("Must have document id to be issued!");
        if (Objects.isNull(request.getDocumentType())) throw new RuntimeException("Must have document type to be issued!");
        if (Objects.isNull(request.getApproved())) throw new RuntimeException("Specify whether approved or not!");
    }

    /**
     * Convert AmendDocumentRequest (graphql) to ApplyDocumentCmd (protobuf)
     * @param request - AmendDocumentRequest
     * @return - AmendDocumentCmd
     */
    private AmendDocumentCmd map(AmendDocumentRequest request) {
        AmendDocumentCmd.Builder cmd = AmendDocumentCmd.newBuilder();

        switch (request.getDocumentTemplate().getType()){
            case PASSPORT:
                cmd.setDocumentTemplate(iprs.document.v1.Document.newBuilder()
                                .setPassport(iprs.document.v1.Passport.newBuilder()
                                        .setId(request.getDocumentTemplate().getPassport().getId())
                                        .setNumber(request.getDocumentTemplate().getPassport().getNumber())
                                        .setPersonId(request.getDocumentTemplate().getPassport().getPersonId())
                                        .setFullName(request.getDocumentTemplate().getPassport().getFullName())
                                        .setBirthCertificateNumber(Integer.parseInt(request.getDocumentTemplate().getPassport().getBirthCertificateNumber()))
                                        .setMothersFullName(request.getDocumentTemplate().getPassport().getMothersFullName())
                                        .setFathersFullName(request.getDocumentTemplate().getPassport().getFathersFullName())
                                        .setMothersIprsId(request.getDocumentTemplate().getPassport().getMothersIprsId())
                                        .setFathersIprsId(request.getDocumentTemplate().getPassport().getFathersIprsId())
                                        .setCountyOfBirth(request.getDocumentTemplate().getPassport().getCountyOfBirth())
                                        .setPlaceOfBirth(request.getDocumentTemplate().getPassport().getPlaceOfBirth())
//                                        .setTimeOfIssue(Timestamp.newBuilder()
//                                                .setSeconds()
//                                                        .build())
                                        .setPlaceOfIssue(request.getDocumentTemplate().getPassport().getPlaceOfIssue())
                                        .build()));
                break;
            case NATIONAL_ID:
                cmd.setDocumentTemplate(iprs.document.v1.Document.newBuilder()
                        .setIdCard(iprs.document.v1.NationalIdCard.newBuilder()
                                .setId(request.getDocumentTemplate().getIdCard().getId())
                                .setIdNo(request.getDocumentTemplate().getIdCard().getIdNo())
                                .setPersonId(request.getDocumentTemplate().getIdCard().getPersonId())
                                .setFullName(request.getDocumentTemplate().getIdCard().getFullName())
                                .setBirthCertificateNumber(request.getDocumentTemplate().getIdCard().getBirthCertificateNumber())
                                .setMothersFullName(request.getDocumentTemplate().getIdCard().getMothersFullName())
                                .setFathersFullName(request.getDocumentTemplate().getIdCard().getFathersFullName())
                                .setMothersIprsId(request.getDocumentTemplate().getIdCard().getMothersIprsId())
                                .setFathersIprsId(request.getDocumentTemplate().getIdCard().getFathersIprsId())
                                .setCountyOfBirth(request.getDocumentTemplate().getIdCard().getCountyOfBirth())
                                .setPlaceOfBirth(request.getDocumentTemplate().getIdCard().getPlaceOfBirth())
//                                .timeOfIssue()
                                .setPlaceOfIssue(request.getDocumentTemplate().getIdCard().getPlaceOfIssue())
                                .build()).build());
                break;
            case INTERSTATE_PASS:
                cmd.setDocumentTemplate(iprs.document.v1.Document.newBuilder()
                        .setInterstatePass(iprs.document.v1.InterstatePass.newBuilder()
                                .setId(cmd.getDocumentTemplate().getInterstatePass().getId())
                                .setPersonId(cmd.getDocumentTemplate().getInterstatePass().getPersonId())
                                .setFullName(cmd.getDocumentTemplate().getInterstatePass().getFullName())
//                                .setTimeOfIssue()
//                                .setValidTill()
                                .build()).build());
                break;
            case BIRTH_CERTIFICATE:
                cmd.setDocumentTemplate(iprs.document.v1.Document.newBuilder()
                        .setBirthCertificate(iprs.document.v1.BirthCertificate.newBuilder()
                                .setId(cmd.getDocumentTemplate().getBirthCertificate().getId())
                                .setPersonId(cmd.getDocumentTemplate().getBirthCertificate().getPersonId())
                                .setFullName(cmd.getDocumentTemplate().getBirthCertificate().getFullName())
                                .setBirthRegistrationNumber(cmd.getDocumentTemplate().getBirthCertificate().getBirthRegistrationNumber())
                                .setMothersFullName(cmd.getDocumentTemplate().getBirthCertificate().getMothersFullName())
                                .setFathersFullName(cmd.getDocumentTemplate().getBirthCertificate().getFathersFullName())
                                .setMothersIprsId(cmd.getDocumentTemplate().getBirthCertificate().getMothersIprsId())
                                .setFathersIprsId(cmd.getDocumentTemplate().getBirthCertificate().getFathersIprsId())
                                .setCountyOfBirth(cmd.getDocumentTemplate().getBirthCertificate().getCountyOfBirth())
                                .setPlaceOfBirth(cmd.getDocumentTemplate().getBirthCertificate().getPlaceOfBirth())
//                                .timeOfIssue()
                                .build()).build());
                break;
            case DEATH_CERTIFICATE:
                cmd.setDocumentTemplate(iprs.document.v1.Document.newBuilder()
                        .setDeathCertificate(iprs.document.v1.DeathCertificate.newBuilder()
                                .setId(cmd.getDocumentTemplate().getDeathCertificate().getId())
                                .setPersonId(cmd.getDocumentTemplate().getDeathCertificate().getPersonId())
                                .setFullName(cmd.getDocumentTemplate().getDeathCertificate().getFullName())
                                .setNumber(cmd.getDocumentTemplate().getDeathCertificate().getNumber())
                                .setCountyOfDeath(cmd.getDocumentTemplate().getDeathCertificate().getCountyOfDeath())
                                .setPlaceOfDeath(cmd.getDocumentTemplate().getDeathCertificate().getPlaceOfDeath())
//                                .setTimeOfIssue()
                                .build()).build());
                break;
        }

        return cmd.build();
    }

    /**
     * Convert AmendDocumentCmd (protobuf) to AmendDocumentResponse (graphql)
     * @param cmd - AmendDocumentCmd
     * @return - AmendDocumentResponse
     */
    private AmendDocumentResponse map(AmendDocumentCmd cmd) {

        return AmendDocumentResponse.newBuilder()
                .documentTemplate(
                        Document.newBuilder()
                                .birthCertificate(
                                        BirthCertificate.newBuilder()
                                                .id(cmd.getDocumentTemplate().getBirthCertificate().getId())
                                                .personId(cmd.getDocumentTemplate().getBirthCertificate().getPersonId())
                                                .fullName(cmd.getDocumentTemplate().getBirthCertificate().getFullName())
                                                .birthRegistrationNumber(cmd.getDocumentTemplate().getBirthCertificate().getBirthRegistrationNumber())
                                                .mothersFullName(cmd.getDocumentTemplate().getBirthCertificate().getMothersFullName())
                                                .fathersFullName(cmd.getDocumentTemplate().getBirthCertificate().getFathersFullName())
                                                .mothersIprsId(cmd.getDocumentTemplate().getBirthCertificate().getMothersIprsId())
                                                .fathersIprsId(cmd.getDocumentTemplate().getBirthCertificate().getFathersIprsId())
                                                .countyOfBirth(cmd.getDocumentTemplate().getBirthCertificate().getCountyOfBirth())
                                                .placeOfBirth(cmd.getDocumentTemplate().getBirthCertificate().getPlaceOfBirth())
                                                .timeOfIssue(DateConverter.ProtoDateToString.INSTANCE.convert(
                                                        cmd.getDocumentTemplate().getBirthCertificate()
                                                                .getTimeOfIssue()))
                                                .build()
                                )
                                .deathCertificate(DeathCertificate.newBuilder()
                                        .id(cmd.getDocumentTemplate().getDeathCertificate().getId())
                                        .personId(cmd.getDocumentTemplate().getDeathCertificate().getPersonId())
                                        .fullName(cmd.getDocumentTemplate().getDeathCertificate().getFullName())
                                        .number(cmd.getDocumentTemplate().getDeathCertificate().getNumber())
                                        .countyOfDeath(cmd.getDocumentTemplate().getDeathCertificate().getCountyOfDeath())
                                        .placeOfDeath(cmd.getDocumentTemplate().getDeathCertificate().getPlaceOfDeath())
                                        .timeOfIssue(DateConverter.ProtoDateToString.INSTANCE.convert(
                                                cmd.getDocumentTemplate().getBirthCertificate()
                                                        .getTimeOfIssue()))
                                        .build()
                                )
                                .passport(Passport.newBuilder()
                                        .id(cmd.getDocumentTemplate().getPassport().getId())
                                        .number((int) cmd.getDocumentTemplate().getPassport().getNumber())
                                        .personId(cmd.getDocumentTemplate().getPassport().getPersonId())
                                        .fullName(cmd.getDocumentTemplate().getPassport().getFullName())
                                        .birthCertificateNumber(String.valueOf(cmd.getDocumentTemplate().getPassport().getBirthCertificateNumber()))
                                        .mothersFullName(cmd.getDocumentTemplate().getPassport().getMothersFullName())
                                        .fathersFullName(cmd.getDocumentTemplate().getPassport().getFathersFullName())
                                        .mothersIprsId(cmd.getDocumentTemplate().getPassport().getMothersIprsId())
                                        .fathersIprsId(cmd.getDocumentTemplate().getPassport().getFathersIprsId())
                                        .countyOfBirth(cmd.getDocumentTemplate().getPassport().getCountyOfBirth())
                                        .placeOfBirth(cmd.getDocumentTemplate().getPassport().getPlaceOfBirth())
                                        .timeOfIssue(DateConverter.ProtoDateToString.INSTANCE.convert(
                                                cmd.getDocumentTemplate().getPassport()
                                                        .getTimeOfIssue()))
                                        .placeOfIssue(cmd.getDocumentTemplate().getPassport().getPlaceOfIssue())
                                        .build()
                                )
                                .idCard(
                                        NationalIdCard.newBuilder()
                                                .id(cmd.getDocumentTemplate().getIdCard().getId())
                                                .idNo((int) cmd.getDocumentTemplate().getIdCard().getIdNo())
                                                .personId(cmd.getDocumentTemplate().getIdCard().getPersonId())
                                                .fullName(cmd.getDocumentTemplate().getIdCard().getFullName())
                                                .birthCertificateNumber(String.valueOf(cmd.getDocumentTemplate().getIdCard().getBirthCertificateNumber()))
                                                .mothersFullName(cmd.getDocumentTemplate().getIdCard().getMothersFullName())
                                                .fathersFullName(cmd.getDocumentTemplate().getIdCard().getFathersFullName())
                                                .mothersIprsId(cmd.getDocumentTemplate().getIdCard().getMothersIprsId())
                                                .fathersIprsId(cmd.getDocumentTemplate().getIdCard().getFathersIprsId())
                                                .countyOfBirth(cmd.getDocumentTemplate().getIdCard().getCountyOfBirth())
                                                .placeOfBirth(cmd.getDocumentTemplate().getIdCard().getPlaceOfBirth())
                                                .timeOfIssue(DateConverter.ProtoDateToString.INSTANCE.convert(
                                                        cmd.getDocumentTemplate().getIdCard()
                                                                .getTimeOfIssue()))
                                                .placeOfIssue(cmd.getDocumentTemplate().getIdCard().getPlaceOfIssue())
                                                .build()
                                )
                                .interstatePass(
                                        InterstatePass.newBuilder()
                                                .id(cmd.getDocumentTemplate().getInterstatePass().getId())
                                                .personId(cmd.getDocumentTemplate().getInterstatePass().getPersonId())
                                                .fullName(cmd.getDocumentTemplate().getInterstatePass().getFullName())
                                                .timeOfIssue(DateConverter.ProtoDateToString.INSTANCE.convert(
                                                        cmd.getDocumentTemplate().getInterstatePass()
                                                                .getTimeOfIssue()))
                                                .validTill(DateConverter.ProtoDateToString.INSTANCE.convert(
                                                        cmd.getDocumentTemplate().getInterstatePass()
                                                                .getValidTill()))
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    /**
     * Convert IssueDocumentRequest (graphql) to IssueDocumentCmd (protobuf)
     * @param request - IssueDocumentRequest
     * @return - IssueDocumentCmd
     */
    private IssueDocumentCmd map(IssueDocumentRequest request) {

        return IssueDocumentCmd.newBuilder()
                .setDocumentId(request.getDocumentId())
                .setApproved(request.getApproved())
                .setDocumentType(DocumentType.valueOf(request.getDocumentType().name()))
                .build();
    }

    /**
     * Convert IssueDocumentCmd (protobuf) to IssueDocumentResponse (graphql)
     * @param cmd - IssueDocumentCmd
     * @return - IssueDocumentResponse
     */
    private IssueDocumentResponse map(IssueDocumentCmd cmd) {

        return IssueDocumentResponse.newBuilder()
                .documentId(cmd.getDocumentId())
                .approved(cmd.getApproved())
                .id(cmd.getId())
                .documentType(com.netflix.dgs.codegen.generated.types.DocumentType
                        .valueOf(cmd.getDocumentType().name()))
                .build();
    }



    /**
     * Convert ApplyDocumentRequest (graphql) to ApplyDocumentCmd (protobuf)
     * @param request - ApplyDocumentRequest
     * @return - ApplyDocumentCmd
     */
    private ApplyDocumentCmd map(ApplyDocumentRequest request) {
        ApplyDocumentCmd.Builder cmd = ApplyDocumentCmd.newBuilder();

        switch (request.getDocumentTemplate().getType()){
            case PASSPORT:
                cmd.setDocumentTemplate(iprs.document.v1.Document.newBuilder()
                        .setPassport(iprs.document.v1.Passport.newBuilder()
                                .setId(request.getDocumentTemplate().getPassport().getId())
                                .setNumber(request.getDocumentTemplate().getPassport().getNumber())
                                .setPersonId(request.getDocumentTemplate().getPassport().getPersonId())
                                .setFullName(request.getDocumentTemplate().getPassport().getFullName())
                                .setBirthCertificateNumber(Integer.parseInt(request.getDocumentTemplate().getPassport().getBirthCertificateNumber()))
                                .setMothersFullName(request.getDocumentTemplate().getPassport().getMothersFullName())
                                .setFathersFullName(request.getDocumentTemplate().getPassport().getFathersFullName())
                                .setMothersIprsId(request.getDocumentTemplate().getPassport().getMothersIprsId())
                                .setFathersIprsId(request.getDocumentTemplate().getPassport().getFathersIprsId())
                                .setCountyOfBirth(request.getDocumentTemplate().getPassport().getCountyOfBirth())
                                .setPlaceOfBirth(request.getDocumentTemplate().getPassport().getPlaceOfBirth())
                                .setTimeOfIssue(DateConverter.StringToProtoDate.INSTANCE.convert(request.getDocumentTemplate().getPassport().getTimeOfIssue()))
                                .setPlaceOfIssue(request.getDocumentTemplate().getPassport().getPlaceOfIssue())
                                .build()));
                break;
            case NATIONAL_ID:
                cmd.setDocumentTemplate(iprs.document.v1.Document.newBuilder()
                        .setIdCard(iprs.document.v1.NationalIdCard.newBuilder()
                                .setId(request.getDocumentTemplate().getIdCard().getId())
                                .setIdNo(request.getDocumentTemplate().getIdCard().getIdNo())
                                .setPersonId(request.getDocumentTemplate().getIdCard().getPersonId())
                                .setFullName(request.getDocumentTemplate().getIdCard().getFullName())
                                .setBirthCertificateNumber(request.getDocumentTemplate().getIdCard().getBirthCertificateNumber())
                                .setMothersFullName(request.getDocumentTemplate().getIdCard().getMothersFullName())
                                .setFathersFullName(request.getDocumentTemplate().getIdCard().getFathersFullName())
                                .setMothersIprsId(request.getDocumentTemplate().getIdCard().getMothersIprsId())
                                .setFathersIprsId(request.getDocumentTemplate().getIdCard().getFathersIprsId())
                                .setCountyOfBirth(request.getDocumentTemplate().getIdCard().getCountyOfBirth())
                                .setPlaceOfBirth(request.getDocumentTemplate().getIdCard().getPlaceOfBirth())
                                .setTimeOfIssue(DateConverter.StringToProtoDate.INSTANCE.convert(request.getDocumentTemplate().getPassport().getTimeOfIssue()))
                                .setPlaceOfIssue(request.getDocumentTemplate().getIdCard().getPlaceOfIssue())
                                .build()).build());
                break;
            case INTERSTATE_PASS:
                cmd.setDocumentTemplate(iprs.document.v1.Document.newBuilder()
                        .setInterstatePass(iprs.document.v1.InterstatePass.newBuilder()
                                .setId(cmd.getDocumentTemplate().getInterstatePass().getId())
                                .setPersonId(cmd.getDocumentTemplate().getInterstatePass().getPersonId())
                                .setFullName(cmd.getDocumentTemplate().getInterstatePass().getFullName())
                                .setTimeOfIssue(DateConverter.StringToProtoDate.INSTANCE.convert(request.getDocumentTemplate().getInterstatePass().getTimeOfIssue()))
                                .setValidTill(DateConverter.StringToProtoDate.INSTANCE.convert(request.getDocumentTemplate().getInterstatePass().getValidTill()))
                                .build()).build());
                break;
            case BIRTH_CERTIFICATE:
                cmd.setDocumentTemplate(iprs.document.v1.Document.newBuilder()
                        .setBirthCertificate(iprs.document.v1.BirthCertificate.newBuilder()
                                .setId(cmd.getDocumentTemplate().getBirthCertificate().getId())
                                .setPersonId(cmd.getDocumentTemplate().getBirthCertificate().getPersonId())
                                .setFullName(cmd.getDocumentTemplate().getBirthCertificate().getFullName())
                                .setBirthRegistrationNumber(cmd.getDocumentTemplate().getBirthCertificate().getBirthRegistrationNumber())
                                .setMothersFullName(cmd.getDocumentTemplate().getBirthCertificate().getMothersFullName())
                                .setFathersFullName(cmd.getDocumentTemplate().getBirthCertificate().getFathersFullName())
                                .setMothersIprsId(cmd.getDocumentTemplate().getBirthCertificate().getMothersIprsId())
                                .setFathersIprsId(cmd.getDocumentTemplate().getBirthCertificate().getFathersIprsId())
                                .setCountyOfBirth(cmd.getDocumentTemplate().getBirthCertificate().getCountyOfBirth())
                                .setPlaceOfBirth(cmd.getDocumentTemplate().getBirthCertificate().getPlaceOfBirth())
                                .setTimeOfIssue(DateConverter.StringToProtoDate.INSTANCE.convert(request.getDocumentTemplate().getBirthCertificate().getTimeOfIssue()))
                                .build()).build());
                break;
            case DEATH_CERTIFICATE:
                cmd.setDocumentTemplate(iprs.document.v1.Document.newBuilder()
                        .setDeathCertificate(iprs.document.v1.DeathCertificate.newBuilder()
                                .setId(cmd.getDocumentTemplate().getDeathCertificate().getId())
                                .setPersonId(cmd.getDocumentTemplate().getDeathCertificate().getPersonId())
                                .setFullName(cmd.getDocumentTemplate().getDeathCertificate().getFullName())
                                .setNumber(cmd.getDocumentTemplate().getDeathCertificate().getNumber())
                                .setCountyOfDeath(cmd.getDocumentTemplate().getDeathCertificate().getCountyOfDeath())
                                .setPlaceOfDeath(cmd.getDocumentTemplate().getDeathCertificate().getPlaceOfDeath())
                                .setTimeOfIssue(DateConverter.StringToProtoDate.INSTANCE.convert(request.getDocumentTemplate().getDeathCertificate().getTimeOfIssue()))
                                .build()).build());
                break;
        }

        return cmd.build();
    }

    /**
     * Convert ApplyDocumentCmd (protobuf) to ApplyDocumentResponse (graphql)
     * @param cmd - ApplyDocumentCmd
     * @return - ApplyDocumentResponse
     */
    private ApplyDocumentResponse map(ApplyDocumentCmd cmd) {
        return ApplyDocumentResponse.newBuilder()
                .documentTemplate(
                        Document.newBuilder()
                                .birthCertificate(
                                        BirthCertificate.newBuilder()
                                                .id(cmd.getDocumentTemplate().getBirthCertificate().getId())
                                                .personId(cmd.getDocumentTemplate().getBirthCertificate().getPersonId())
                                                .fullName(cmd.getDocumentTemplate().getBirthCertificate().getFullName())
                                                .birthRegistrationNumber(cmd.getDocumentTemplate().getBirthCertificate().getBirthRegistrationNumber())
                                                .mothersFullName(cmd.getDocumentTemplate().getBirthCertificate().getMothersFullName())
                                                .fathersFullName(cmd.getDocumentTemplate().getBirthCertificate().getFathersFullName())
                                                .mothersIprsId(cmd.getDocumentTemplate().getBirthCertificate().getMothersIprsId())
                                                .fathersIprsId(cmd.getDocumentTemplate().getBirthCertificate().getFathersIprsId())
                                                .countyOfBirth(cmd.getDocumentTemplate().getBirthCertificate().getCountyOfBirth())
                                                .placeOfBirth(cmd.getDocumentTemplate().getBirthCertificate().getPlaceOfBirth())
                                                .timeOfIssue(DateConverter.ProtoDateToString.INSTANCE.convert(
                                                        cmd.getDocumentTemplate().getBirthCertificate()
                                                                .getTimeOfIssue()))
                                                .build()
                                )
                                .deathCertificate(DeathCertificate.newBuilder()
                                        .id(cmd.getDocumentTemplate().getDeathCertificate().getId())
                                        .personId(cmd.getDocumentTemplate().getDeathCertificate().getPersonId())
                                        .fullName(cmd.getDocumentTemplate().getDeathCertificate().getFullName())
                                        .number(cmd.getDocumentTemplate().getDeathCertificate().getNumber())
                                        .countyOfDeath(cmd.getDocumentTemplate().getDeathCertificate().getCountyOfDeath())
                                        .placeOfDeath(cmd.getDocumentTemplate().getDeathCertificate().getPlaceOfDeath())
                                        .timeOfIssue(DateConverter.ProtoDateToString.INSTANCE.convert(
                                                cmd.getDocumentTemplate().getBirthCertificate()
                                                        .getTimeOfIssue()))
                                        .build()
                                )
                                .passport(Passport.newBuilder()
                                        .id(cmd.getDocumentTemplate().getPassport().getId())
                                        .number((int) cmd.getDocumentTemplate().getPassport().getNumber())
                                        .personId(cmd.getDocumentTemplate().getPassport().getPersonId())
                                        .fullName(cmd.getDocumentTemplate().getPassport().getFullName())
                                        .birthCertificateNumber(String.valueOf(cmd.getDocumentTemplate().getPassport().getBirthCertificateNumber()))
                                        .mothersFullName(cmd.getDocumentTemplate().getPassport().getMothersFullName())
                                        .fathersFullName(cmd.getDocumentTemplate().getPassport().getFathersFullName())
                                        .mothersIprsId(cmd.getDocumentTemplate().getPassport().getMothersIprsId())
                                        .fathersIprsId(cmd.getDocumentTemplate().getPassport().getFathersIprsId())
                                        .countyOfBirth(cmd.getDocumentTemplate().getPassport().getCountyOfBirth())
                                        .placeOfBirth(cmd.getDocumentTemplate().getPassport().getPlaceOfBirth())
                                        .timeOfIssue(DateConverter.ProtoDateToString.INSTANCE.convert(
                                                cmd.getDocumentTemplate().getPassport()
                                                        .getTimeOfIssue()))
                                        .placeOfIssue(cmd.getDocumentTemplate().getPassport().getPlaceOfIssue())
                                        .build()
                                )
                                .idCard(
                                        NationalIdCard.newBuilder()
                                                .id(cmd.getDocumentTemplate().getIdCard().getId())
                                                .idNo((int) cmd.getDocumentTemplate().getIdCard().getIdNo())
                                                .personId(cmd.getDocumentTemplate().getIdCard().getPersonId())
                                                .fullName(cmd.getDocumentTemplate().getIdCard().getFullName())
                                                .birthCertificateNumber(String.valueOf(cmd.getDocumentTemplate().getIdCard().getBirthCertificateNumber()))
                                                .mothersFullName(cmd.getDocumentTemplate().getIdCard().getMothersFullName())
                                                .fathersFullName(cmd.getDocumentTemplate().getIdCard().getFathersFullName())
                                                .mothersIprsId(cmd.getDocumentTemplate().getIdCard().getMothersIprsId())
                                                .fathersIprsId(cmd.getDocumentTemplate().getIdCard().getFathersIprsId())
                                                .countyOfBirth(cmd.getDocumentTemplate().getIdCard().getCountyOfBirth())
                                                .placeOfBirth(cmd.getDocumentTemplate().getIdCard().getPlaceOfBirth())
                                                .timeOfIssue(DateConverter.ProtoDateToString.INSTANCE.convert(
                                                        cmd.getDocumentTemplate().getIdCard()
                                                                .getTimeOfIssue()))
                                                .placeOfIssue(cmd.getDocumentTemplate().getIdCard().getPlaceOfIssue())
                                                .build()
                                )
                                .interstatePass(
                                        InterstatePass.newBuilder()
                                                .id(cmd.getDocumentTemplate().getInterstatePass().getId())
                                                .personId(cmd.getDocumentTemplate().getInterstatePass().getPersonId())
                                                .fullName(cmd.getDocumentTemplate().getInterstatePass().getFullName())
                                                .timeOfIssue(DateConverter.ProtoDateToString.INSTANCE.convert(
                                                        cmd.getDocumentTemplate().getInterstatePass()
                                                                .getTimeOfIssue()))
                                                .validTill(DateConverter.ProtoDateToString.INSTANCE.convert(
                                                        cmd.getDocumentTemplate().getInterstatePass()
                                                                .getValidTill()))
                                                .build()
                                )
                                .build()
                )
                .build();
    }
}
