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

import java.util.Objects;

import static com.netflix.dgs.codegen.generated.types.DocumentType.*;

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

        if (Objects.isNull(request.getDocumentTemplate().getType())) throw new RuntimeException("Document type must be specified!");
        if (request.getDocumentTemplate().getType().equals(BIRTH_CERTIFICATE)) {
            if (Objects.isNull(request.getDocumentTemplate().getBirthCertificate())) throw new RuntimeException("Birth Certificate template cannot be null if document type is specified as such!");
            var bCert = request.getDocumentTemplate().getBirthCertificate();
            if (Objects.isNull(bCert.getFullName())) throw new RuntimeException("Full name must be provided!");
            if (Objects.isNull(bCert.getCountyOfBirth())) throw new RuntimeException("County of birth must be provided!");
            if (Objects.isNull(bCert.getFathersFullName())) throw new RuntimeException("Father's name can be blank but not null!");
            if (Objects.isNull(bCert.getMothersFullName())) throw new RuntimeException("Mother's full name must be provided!");
            if (Objects.isNull(bCert.getFathersIprsId())) throw new RuntimeException("Father's IPRS ID can be blank but not null!");
            if (Objects.isNull(bCert.getMothersIprsId())) throw new RuntimeException("Mother's IPRS ID must be provided!");
            if (Objects.isNull(bCert.getPersonId())) throw new RuntimeException("Child's IPRS id must be provided");
            if (Objects.isNull(bCert.getBirthRegistrationNumber())) throw new RuntimeException("Child's birth registration number must be provided");
            if (bCert.getPlaceOfBirth().isBlank()) throw new RuntimeException("Place of birth must be provided!");
            if (bCert.getFullName().isBlank()) throw new RuntimeException("Full name must be provided!");
            if (bCert.getCountyOfBirth().isBlank()) throw new RuntimeException("County of birth must be provided!");
            if (bCert.getMothersFullName().isBlank()) throw new RuntimeException("Mother's full name must be provided!");
            if (bCert.getMothersIprsId().isBlank()) throw new RuntimeException("Mother's IPRS ID must be provided!");
            if (bCert.getPersonId().isBlank()) throw new RuntimeException("Child's IPRS id must be provided");
            if (bCert.getBirthRegistrationNumber().equals(0)) throw new RuntimeException("Child's birth registration number must be provided");
            if (bCert.getPlaceOfBirth().isBlank()) throw new RuntimeException("Place of birth must be provided!");
        }

        if (request.getDocumentTemplate().getType().equals(PASSPORT)) {
            if (Objects.isNull(request.getDocumentTemplate().getPassport())) throw new RuntimeException("Passport template cannot be null if document type is specified as such!");
            var pP = request.getDocumentTemplate().getPassport();
            if (Objects.isNull(pP.getFullName())) throw new RuntimeException("Full name must be provided!");
            if (Objects.isNull(pP.getCountyOfBirth())) throw new RuntimeException("County of birth must be provided!");
            if (Objects.isNull(pP.getFathersFullName())) throw new RuntimeException("Father's name can be blank but not null!");
            if (Objects.isNull(pP.getMothersFullName())) throw new RuntimeException("Mother's full name must be provided!");
            if (Objects.isNull(pP.getFathersIprsId())) throw new RuntimeException("Father's IPRS ID can be blank but not null!");
            if (Objects.isNull(pP.getMothersIprsId())) throw new RuntimeException("Mother's IPRS ID must be provided!");
            if (Objects.isNull(pP.getPersonId())) throw new RuntimeException("Applicant's IPRS id must be provided");
            if (Objects.isNull(pP.getBirthCertificateNumber())) throw new RuntimeException("Applicant's birth cert number must be provided");
            if (pP.getPlaceOfBirth().isBlank()) throw new RuntimeException("Place of birth must be provided!");
            if (pP.getFullName().isBlank()) throw new RuntimeException("Full name must be provided!");
            if (pP.getCountyOfBirth().isBlank()) throw new RuntimeException("County of birth must be provided!");
            if (pP.getMothersFullName().isBlank()) throw new RuntimeException("Mother's full name must be provided!");
            if (pP.getMothersIprsId().isBlank()) throw new RuntimeException("Mother's IPRS ID must be provided!");
            if (pP.getPersonId().isBlank()) throw new RuntimeException("Applicant's IPRS id must be provided");
            if (pP.getBirthCertificateNumber().isBlank()) throw new RuntimeException("Applicant's birth cert number must be provided");
            if (pP.getPlaceOfBirth().isBlank()) throw new RuntimeException("Place of birth must be provided!");
        }

        if (request.getDocumentTemplate().getType().equals(DEATH_CERTIFICATE)) {
            if (Objects.isNull(request.getDocumentTemplate().getDeathCertificate())) throw new RuntimeException("Death Certificate template cannot be null if document type is specified as such!");
            var dCert = request.getDocumentTemplate().getDeathCertificate();
            if (Objects.isNull(dCert.getFullName())) throw new RuntimeException("Full name must be provided!");
            if (Objects.isNull(dCert.getCountyOfDeath())) throw new RuntimeException("County of death must be provided!");
            if (Objects.isNull(dCert.getPlaceOfDeath())) throw new RuntimeException("Place of death must be provided!");
            if (Objects.isNull(dCert.getPersonId())) throw new RuntimeException("IPRS id must be provided");
            if (dCert.getFullName().isBlank()) throw new RuntimeException("Full name must be provided!");
            if (dCert.getCountyOfDeath().isBlank()) throw new RuntimeException("County of death must be provided!");
            if (dCert.getPersonId().isBlank()) throw new RuntimeException("IPRS id must be provided");
            if (dCert.getPlaceOfDeath().isBlank()) throw new RuntimeException("Place of death must be provided!");
        }

        if (request.getDocumentTemplate().getType().equals(INTERSTATE_PASS)) {
            if (Objects.isNull(request.getDocumentTemplate().getInterstatePass())) throw new RuntimeException("Interstate pass template cannot be null if document type is specified as such!");
            var pass = request.getDocumentTemplate().getInterstatePass();
            if (Objects.isNull(pass.getFullName())) throw new RuntimeException("Full name must be provided!");
            if (Objects.isNull(pass.getBirthRegistrationNumber())) throw new RuntimeException("County of death must be provided!");
            if (Objects.isNull(pass.getPersonId())) throw new RuntimeException("IPRS id must be provided");
            if (pass.getFullName().isBlank()) throw new RuntimeException("Full name must be provided!");
            if (pass.getBirthRegistrationNumber().equals(0)) throw new RuntimeException("County of birth must be provided!");
            if (pass.getPersonId().isBlank()) throw new RuntimeException("IPRS id must be provided");
        }

        if (request.getDocumentTemplate().getType().equals(NATIONAL_ID)) {
            if (Objects.isNull(request.getDocumentTemplate().getIdCard())) throw new RuntimeException("Id card template cannot be null if document type is specified as such!");
            var id = request.getDocumentTemplate().getPassport();
            if (Objects.isNull(id.getFullName())) throw new RuntimeException("Full name must be provided!");
            if (Objects.isNull(id.getCountyOfBirth())) throw new RuntimeException("County of birth must be provided!");
            if (Objects.isNull(id.getFathersFullName())) throw new RuntimeException("Father's name can be blank but not null!");
            if (Objects.isNull(id.getMothersFullName())) throw new RuntimeException("Mother's full name must be provided!");
            if (Objects.isNull(id.getFathersIprsId())) throw new RuntimeException("Father's IPRS ID can be blank but not null!");
            if (Objects.isNull(id.getMothersIprsId())) throw new RuntimeException("Mother's IPRS ID must be provided!");
            if (Objects.isNull(id.getPersonId())) throw new RuntimeException("Applicant's IPRS id must be provided");
            if (Objects.isNull(id.getBirthCertificateNumber())) throw new RuntimeException("Applicant's birth cert number must be provided");
            if (id.getPlaceOfBirth().isBlank()) throw new RuntimeException("Place of birth must be provided!");
            if (id.getFullName().isBlank()) throw new RuntimeException("Full name must be provided!");
            if (id.getCountyOfBirth().isBlank()) throw new RuntimeException("County of birth must be provided!");
            if (id.getMothersFullName().isBlank()) throw new RuntimeException("Mother's full name must be provided!");
            if (id.getMothersIprsId().isBlank()) throw new RuntimeException("Mother's IPRS ID must be provided!");
            if (id.getPersonId().isBlank()) throw new RuntimeException("Applicant's IPRS id must be provided");
            if (id.getBirthCertificateNumber().isBlank()) throw new RuntimeException("Applicant's birth cert number must be provided");
            if (id.getPlaceOfBirth().isBlank()) throw new RuntimeException("Place of birth must be provided!");
        }
    }

    /**
     * Validate incoming payload
     * @param request - AmendDocumentRequest
     */
    private void validate(AmendDocumentRequest request) {

        if (Objects.isNull(request.getDocumentTemplate().getType())) throw new RuntimeException("Document type must be specified!");
        if (request.getDocumentTemplate().getType().equals(BIRTH_CERTIFICATE)) {
            if (Objects.isNull(request.getDocumentTemplate().getBirthCertificate())) throw new RuntimeException("Birth Certificate template cannot be null if document type is specified as such!");
            var bCert = request.getDocumentTemplate().getBirthCertificate();
            if (Objects.isNull(bCert.getId())) throw new RuntimeException("Id must be provided!");
            if (Objects.isNull(bCert.getFullName())) throw new RuntimeException("Full name must be provided!");
            if (Objects.isNull(bCert.getCountyOfBirth())) throw new RuntimeException("County of birth must be provided!");
            if (Objects.isNull(bCert.getFathersFullName())) throw new RuntimeException("Father's name can be blank but not null!");
            if (Objects.isNull(bCert.getMothersFullName())) throw new RuntimeException("Mother's full name must be provided!");
            if (Objects.isNull(bCert.getFathersIprsId())) throw new RuntimeException("Father's IPRS ID can be blank but not null!");
            if (Objects.isNull(bCert.getMothersIprsId())) throw new RuntimeException("Mother's IPRS ID must be provided!");
            if (Objects.isNull(bCert.getPersonId())) throw new RuntimeException("Child's IPRS id must be provided");
            if (Objects.isNull(bCert.getBirthRegistrationNumber())) throw new RuntimeException("Child's birth registration number must be provided");
            if (bCert.getPlaceOfBirth().isBlank()) throw new RuntimeException("Place of birth must be provided!");
            if (bCert.getId().isBlank()) throw new RuntimeException("Id must be provided!");
            if (bCert.getFullName().isBlank()) throw new RuntimeException("Full name must be provided!");
            if (bCert.getCountyOfBirth().isBlank()) throw new RuntimeException("County of birth must be provided!");
            if (bCert.getMothersFullName().isBlank()) throw new RuntimeException("Mother's full name must be provided!");
            if (bCert.getMothersIprsId().isBlank()) throw new RuntimeException("Mother's IPRS ID must be provided!");
            if (bCert.getPersonId().isBlank()) throw new RuntimeException("Child's IPRS id must be provided");
            if (bCert.getBirthRegistrationNumber().equals(0)) throw new RuntimeException("Child's birth registration number must be provided");
            if (bCert.getPlaceOfBirth().isBlank()) throw new RuntimeException("Place of birth must be provided!");
        }

        if (request.getDocumentTemplate().getType().equals(PASSPORT)) {
            if (Objects.isNull(request.getDocumentTemplate().getPassport())) throw new RuntimeException("Passport template cannot be null if document type is specified as such!");
            var pP = request.getDocumentTemplate().getPassport();
            if (Objects.isNull(pP.getFullName())) throw new RuntimeException("Full name must be provided!");
            if (Objects.isNull(pP.getId())) throw new RuntimeException("Id must be provided!");
            if (Objects.isNull(pP.getCountyOfBirth())) throw new RuntimeException("County of birth must be provided!");
            if (Objects.isNull(pP.getFathersFullName())) throw new RuntimeException("Father's name can be blank but not null!");
            if (Objects.isNull(pP.getMothersFullName())) throw new RuntimeException("Mother's full name must be provided!");
            if (Objects.isNull(pP.getFathersIprsId())) throw new RuntimeException("Father's IPRS ID can be blank but not null!");
            if (Objects.isNull(pP.getMothersIprsId())) throw new RuntimeException("Mother's IPRS ID must be provided!");
            if (Objects.isNull(pP.getPersonId())) throw new RuntimeException("Applicant's IPRS id must be provided");
            if (Objects.isNull(pP.getBirthCertificateNumber())) throw new RuntimeException("Applicant's birth cert number must be provided");
            if (pP.getPlaceOfBirth().isBlank()) throw new RuntimeException("Place of birth must be provided!");
            if (pP.getFullName().isBlank()) throw new RuntimeException("Full name must be provided!");
            if (pP.getCountyOfBirth().isBlank()) throw new RuntimeException("County of birth must be provided!");
            if (pP.getMothersFullName().isBlank()) throw new RuntimeException("Mother's full name must be provided!");
            if (pP.getMothersIprsId().isBlank()) throw new RuntimeException("Mother's IPRS ID must be provided!");
            if (pP.getPersonId().isBlank()) throw new RuntimeException("Applicant's IPRS id must be provided");
            if (pP.getBirthCertificateNumber().isBlank()) throw new RuntimeException("Applicant's birth cert number must be provided");
            if (pP.getPlaceOfBirth().isBlank()) throw new RuntimeException("Place of birth must be provided!");
            if (pP.getId().isBlank()) throw new RuntimeException("Id must be provided!");
        }

        if (request.getDocumentTemplate().getType().equals(DEATH_CERTIFICATE)) {
            if (Objects.isNull(request.getDocumentTemplate().getDeathCertificate())) throw new RuntimeException("Death Certificate template cannot be null if document type is specified as such!");
            var dCert = request.getDocumentTemplate().getDeathCertificate();
            if (Objects.isNull(dCert.getId())) throw new RuntimeException("Id must be provided!");
            if (Objects.isNull(dCert.getFullName())) throw new RuntimeException("Full name must be provided!");
            if (Objects.isNull(dCert.getCountyOfDeath())) throw new RuntimeException("County of death must be provided!");
            if (Objects.isNull(dCert.getPlaceOfDeath())) throw new RuntimeException("Place of death must be provided!");
            if (Objects.isNull(dCert.getPersonId())) throw new RuntimeException("IPRS id must be provided");
            if (dCert.getFullName().isBlank()) throw new RuntimeException("Full name must be provided!");
            if (dCert.getCountyOfDeath().isBlank()) throw new RuntimeException("County of death must be provided!");
            if (dCert.getPersonId().isBlank()) throw new RuntimeException("IPRS id must be provided");
            if (dCert.getPlaceOfDeath().isBlank()) throw new RuntimeException("Place of death must be provided!");
            if (dCert.getId().isBlank()) throw new RuntimeException("Id must be provided!");
        }

        if (request.getDocumentTemplate().getType().equals(INTERSTATE_PASS)) {
            if (Objects.isNull(request.getDocumentTemplate().getInterstatePass())) throw new RuntimeException("Interstate pass template cannot be null if document type is specified as such!");
            var pass = request.getDocumentTemplate().getInterstatePass();
            if (Objects.isNull(pass.getId())) throw new RuntimeException("Id must be provided!");
            if (Objects.isNull(pass.getFullName())) throw new RuntimeException("Full name must be provided!");
            if (Objects.isNull(pass.getBirthRegistrationNumber())) throw new RuntimeException("County of death must be provided!");
            if (Objects.isNull(pass.getPersonId())) throw new RuntimeException("IPRS id must be provided");
            if (pass.getFullName().isBlank()) throw new RuntimeException("Full name must be provided!");
            if (pass.getBirthRegistrationNumber().equals(0)) throw new RuntimeException("County of birth must be provided!");
            if (pass.getId().isBlank()) throw new RuntimeException("Id must be provided!");
            if (pass.getPersonId().isBlank()) throw new RuntimeException("IPRS id must be provided");
        }

        if (request.getDocumentTemplate().getType().equals(NATIONAL_ID)) {
            if (Objects.isNull(request.getDocumentTemplate().getIdCard())) throw new RuntimeException("Id card template cannot be null if document type is specified as such!");
            var id = request.getDocumentTemplate().getPassport();
            if (Objects.isNull(id.getId())) throw new RuntimeException("Id must be provided!");
            if (Objects.isNull(id.getFullName())) throw new RuntimeException("Full name must be provided!");
            if (Objects.isNull(id.getCountyOfBirth())) throw new RuntimeException("County of birth must be provided!");
            if (Objects.isNull(id.getFathersFullName())) throw new RuntimeException("Father's name can be blank but not null!");
            if (Objects.isNull(id.getMothersFullName())) throw new RuntimeException("Mother's full name must be provided!");
            if (Objects.isNull(id.getFathersIprsId())) throw new RuntimeException("Father's IPRS ID can be blank but not null!");
            if (Objects.isNull(id.getMothersIprsId())) throw new RuntimeException("Mother's IPRS ID must be provided!");
            if (Objects.isNull(id.getPersonId())) throw new RuntimeException("Applicant's IPRS id must be provided");
            if (Objects.isNull(id.getBirthCertificateNumber())) throw new RuntimeException("Applicant's birth cert number must be provided");
            if (id.getPlaceOfBirth().isBlank()) throw new RuntimeException("Place of birth must be provided!");
            if (id.getFullName().isBlank()) throw new RuntimeException("Full name must be provided!");
            if (id.getCountyOfBirth().isBlank()) throw new RuntimeException("County of birth must be provided!");
            if (id.getMothersFullName().isBlank()) throw new RuntimeException("Mother's full name must be provided!");
            if (id.getMothersIprsId().isBlank()) throw new RuntimeException("Mother's IPRS ID must be provided!");
            if (id.getPersonId().isBlank()) throw new RuntimeException("Applicant's IPRS id must be provided");
            if (id.getBirthCertificateNumber().isBlank()) throw new RuntimeException("Applicant's birth cert number must be provided");
            if (id.getId().isBlank()) throw new RuntimeException("Id must be provided!");
            if (id.getPlaceOfBirth().isBlank()) throw new RuntimeException("Place of birth must be provided!");
        }
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
