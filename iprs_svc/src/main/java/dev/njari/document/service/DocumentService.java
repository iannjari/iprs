package dev.njari.document.service;

import com.google.protobuf.Timestamp;
import dev.njari.document.repository.*;
import dev.njari.person.repository.PersonRepository;
import iprs.document.v1.*;
import iprs.person.v1.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * @author njari_mathenge
 * on 13/01/2024.
 * github.com/iannjari
 */

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final InterstatePassRepository interstatePassRepo;
    private final PassportRepository passportRepo;
    private final NationalIdRepository nationalIdRepo;
    private final BirthCertificateRepository birthCertificateRepo;
    private final DeathCertificateRepository deathCertificateRepo;
    private final PersonRepository personRepo;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("kafka.document.topic")
    private String docTopic;

    public ApplyDocumentCmd applyForDocument(ApplyDocumentCmd cmd) {
        validate(cmd);
        Query query;
        List<Person> persons;

        switch (cmd.getDocumentTemplate().getType()) {

            case BIRTH_CERTIFICATE:
                query = Query.query(Criteria.where("person_id")
                        .is(cmd.getDocumentTemplate().getBirthCertificate().getPersonId()));
                persons = personRepo.findAll(query);
                if (persons.isEmpty()) throw new RuntimeException("Person with id: "
                        .concat(cmd.getDocumentTemplate().getBirthCertificate().getPersonId())
                        .concat(" not found!"));
                BirthCertificate birthCert = birthCertificateRepo.save(cmd.getDocumentTemplate().getBirthCertificate());
                kafkaTemplate.send(docTopic, birthCert.getId(), birthCert);

                cmd = cmd.toBuilder().setDocumentTemplate(cmd.getDocumentTemplate().toBuilder()
                        .setBirthCertificate(birthCert.toBuilder())).build();
                break;

            case NATIONAL_ID:
                query = Query.query(Criteria.where("person_id")
                        .is(cmd.getDocumentTemplate().getIdCard().getPersonId()));
                persons = personRepo.findAll(query);
                if (persons.isEmpty()) throw new RuntimeException("Person with id: "
                        .concat(cmd.getDocumentTemplate().getIdCard().getPersonId())
                        .concat(" not found!"));
                NationalIdCard id = nationalIdRepo.save(cmd.getDocumentTemplate().getIdCard());
                kafkaTemplate.send(docTopic, id.getId(), id);

                cmd = cmd.toBuilder().setDocumentTemplate(cmd.getDocumentTemplate().toBuilder()
                        .setIdCard(id)).build();
                break;

            case DEATH_CERTIFICATE:
                query = Query.query(Criteria.where("person_id")
                        .is(cmd.getDocumentTemplate().getDeathCertificate().getPersonId()));
                persons = personRepo.findAll(query);
                if (persons.isEmpty()) throw new RuntimeException("Person with id: "
                        .concat(cmd.getDocumentTemplate().getDeathCertificate().getPersonId())
                        .concat(" not found!"));
                DeathCertificate deathCert = deathCertificateRepo.save(cmd.getDocumentTemplate().getDeathCertificate());
                kafkaTemplate.send(docTopic, deathCert.getId(), deathCert);

                cmd = cmd.toBuilder().setDocumentTemplate(cmd.getDocumentTemplate().toBuilder()
                        .setDeathCertificate(deathCert)).build();
                break;

            case PASSPORT:
                query = Query.query(Criteria.where("person_id")
                        .is(cmd.getDocumentTemplate().getPassport().getPersonId()));
                persons = personRepo.findAll(query);
                if (persons.isEmpty()) throw new RuntimeException("Person with id: "
                        .concat(cmd.getDocumentTemplate().getPassport().getPersonId())
                        .concat(" not found!"));
                Passport passport = passportRepo.save(cmd.getDocumentTemplate().getPassport());
                kafkaTemplate.send(docTopic, passport.getId(), passport);

                cmd = cmd.toBuilder().setDocumentTemplate(cmd.getDocumentTemplate().toBuilder()
                        .setPassport(passport)).build();
                break;

            case INTERSTATE_PASS:
                query = Query.query(Criteria.where("person_id")
                        .is(cmd.getDocumentTemplate().getInterstatePass().getPersonId()));
                persons = personRepo.findAll(query);
                if (persons.isEmpty()) throw new RuntimeException("Person with id: "
                        .concat(cmd.getDocumentTemplate().getInterstatePass().getPersonId())
                        .concat(" not found!"));
                InterstatePass pass = interstatePassRepo.save(cmd.getDocumentTemplate().getInterstatePass());
                kafkaTemplate.send(docTopic, pass.getId(), pass);

                cmd = cmd.toBuilder().setDocumentTemplate(cmd.getDocumentTemplate().toBuilder()
                        .setInterstatePass(pass)).build();
                break;

            case UNRECOGNIZED:
                throw new RuntimeException("Unrecognized/un-implemented type of document: "
                        .concat(cmd.getDocumentTemplate().getType().name()));
        }

        return cmd;
    }

    public IssueDocumentCmd issueDocument(IssueDocumentCmd cmd) {

        // validate
        validate(cmd);
        // update and save


        switch (cmd.getDocumentType()) {
            case BIRTH_CERTIFICATE:
                BirthCertificate birthCert = birthCertificateRepo.findById(cmd.getId());
                if (Objects.isNull(birthCert)) throw new RuntimeException("Birth cert with id: "
                        .concat(cmd.getId()).concat(" not found"));

                birthCert = birthCert.toBuilder().setTimeOfIssue(Timestamp.newBuilder()
                        .setSeconds(Instant.now().getEpochSecond())
                        .build()).build();
                birthCertificateRepo.save(birthCert);
                kafkaTemplate.send(docTopic, birthCert.getId(), birthCert);

                break;
            case NATIONAL_ID:
                NationalIdCard idCard = nationalIdRepo.findById(cmd.getId());
                if (Objects.isNull(idCard)) throw new RuntimeException("National id card with id: "
                        .concat(cmd.getId()).concat(" not found"));

                idCard = idCard.toBuilder().setTimeOfIssue(Timestamp.newBuilder()
                        .setSeconds(Instant.now().getEpochSecond())
                        .build()).build();
                nationalIdRepo.save(idCard);
                kafkaTemplate.send(docTopic, idCard.getId(), idCard);

                break;
            case DEATH_CERTIFICATE:
                DeathCertificate deathCert = deathCertificateRepo.findById(cmd.getId());
                if (Objects.isNull(deathCert)) throw new RuntimeException("Death cert with id: "
                        .concat(cmd.getId()).concat(" not found"));

                deathCert = deathCert.toBuilder().setTimeOfIssue(Timestamp.newBuilder()
                        .setSeconds(Instant.now().getEpochSecond())
                        .build()).build();
                deathCertificateRepo.save(deathCert);
                kafkaTemplate.send(docTopic, deathCert.getId(), deathCert);

                break;
            case PASSPORT:
                Passport passport = passportRepo.findById(cmd.getId());
                if (Objects.isNull(passport)) throw new RuntimeException("Passport with id: "
                        .concat(cmd.getId()).concat(" not found"));

                passport = passport.toBuilder().setTimeOfIssue(Timestamp.newBuilder()
                        .setSeconds(Instant.now().getEpochSecond())
                        .build()).build();
                passportRepo.save(passport);
                kafkaTemplate.send(docTopic, passport.getId(), passport);

                break;
            case INTERSTATE_PASS:
                InterstatePass pass = interstatePassRepo.findById(cmd.getId());
                if (Objects.isNull(pass)) throw new RuntimeException("Interstate pass with id: "
                        .concat(cmd.getId()).concat(" not found"));

                pass = pass.toBuilder().setTimeOfIssue(Timestamp.newBuilder()
                        .setSeconds(Instant.now().getEpochSecond())
                        .build()).build();
                interstatePassRepo.save(pass);
                kafkaTemplate.send(docTopic, pass.getId(), pass);

                break;
            default:
        }

        return cmd;

    }

    public AmendDocumentCmd updateDocument(AmendDocumentCmd cmd) {
        // validate
        validate(cmd);

        switch (cmd.getDocumentTemplate().getType()) {
            case BIRTH_CERTIFICATE:
                if (cmd.getDocumentTemplate().getBirthCertificate().getId().isBlank())
                    throw new IllegalArgumentException("Birth cert id cannot be blank!");
                break;
            case NATIONAL_ID:
                if (cmd.getDocumentTemplate().getIdCard().getId().isBlank())
                    throw new IllegalArgumentException("National id card id cannot be blank!");
                break;
            case DEATH_CERTIFICATE:
                if (cmd.getDocumentTemplate().getDeathCertificate().getId().isBlank())
                    throw new IllegalArgumentException("Death cert id cannot be blank!");
                break;
            case PASSPORT:
                if (cmd.getDocumentTemplate().getPassport().getId().isBlank())
                    throw new IllegalArgumentException("Passport id cannot be blank!");
                break;
            case INTERSTATE_PASS:
                if (cmd.getDocumentTemplate().getInterstatePass().getId().isBlank())
                    throw new IllegalArgumentException("Interstate pass id cannot be blank!");
                break;
            default:
        }

        return cmd;

    }

    private void validate(ApplyDocumentCmd cmd) {
    }

    private void validate(IssueDocumentCmd cmd) {
        if (cmd.getDocumentId().isBlank()) throw new IllegalArgumentException("Document id cannot be blank");
    }

    private void validate(AmendDocumentCmd cmd) {
        switch (cmd.getDocumentTemplate().getType()) {
            case BIRTH_CERTIFICATE:
                if (cmd.getDocumentTemplate().getBirthCertificate().getId().isBlank())
                    throw new IllegalArgumentException("Birth cert id cannot be blank!");
                break;
            case NATIONAL_ID:
                if (cmd.getDocumentTemplate().getIdCard().getId().isBlank())
                    throw new IllegalArgumentException("National id card id cannot be blank!");
                break;
            case DEATH_CERTIFICATE:
                if (cmd.getDocumentTemplate().getDeathCertificate().getId().isBlank())
                    throw new IllegalArgumentException("Death cert id cannot be blank!");
                break;
            case PASSPORT:
                if (cmd.getDocumentTemplate().getPassport().getId().isBlank())
                    throw new IllegalArgumentException("Passport id cannot be blank!");
                break;
            case INTERSTATE_PASS:
                if (cmd.getDocumentTemplate().getInterstatePass().getId().isBlank())
                    throw new IllegalArgumentException("Interstate pass id cannot be blank!");
                break;
            default:
        }
    }

}
