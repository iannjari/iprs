package dev.njari.person.service;

import com.google.protobuf.Timestamp;
import dev.njari.common_utils.exception.custom_exception.InvalidArgumentException;
import dev.njari.common_utils.exception.custom_exception.NotFoundException;
import dev.njari.person.repository.PersonRepository;
import iprs.person.v1.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final PersonRepository personRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("kafka.person.topic")
    private String personTopic;

    public RegisterBirthCmd registerBirth(RegisterBirthCmd cmd) {
        // validate
        validate(cmd);
        // save
        Person.Builder builder = Person.newBuilder(cmd.getTemplate());
        builder.setIsAlive(true)
                .setIprsDetails(IprsDetails.newBuilder()
                        .setDateOfRegistration(Timestamp.newBuilder()
                                .setNanos(Instant.now().getNano())
                                .setSeconds(Instant.now().getEpochSecond()))
                .build());

        Person person = personRepository.save(builder.build());
        kafkaTemplate.send(personTopic, person.getId(), person);
        return cmd.toBuilder()
                .setTemplate(person)
                .build();
    }

    public UpdatePersonDetailsCmd updatePersonDetails(UpdatePersonDetailsCmd cmd) {
        // validate
        validate(cmd);
        // update and save
        Person person = personRepository.save(cmd.getTemplate());
        kafkaTemplate.send(personTopic, person.getId(), person);

        return cmd.toBuilder()
                .setTemplate(person)
                .build();
    }

    public RecordDeathCmd recordDeath(RecordDeathCmd cmd) {
        // validate
        validate(cmd);
        // update and save
        Person person = personRepository.findById(cmd.getPersonId());

        if (Objects.isNull(person)) throw new NotFoundException("Person with id: "
                .concat(cmd.getPersonId()).concat(" not found"));

        person = personRepository.save(person.toBuilder().setDateOfDeath(cmd.getTimeOfDeath()).setIsAlive(false).build());
        kafkaTemplate.send(personTopic, person.getId(), person);

        return cmd;
    }

    private void validate(RegisterBirthCmd cmd) {
        if (!cmd.hasTemplate()) throw new InvalidArgumentException("Command must have template!");
        if (cmd.getTemplate().getFirstName().isBlank() &&
                cmd.getTemplate().getFirstName().isBlank()) throw new InvalidArgumentException ("Must have first or last name!");
        if (!cmd.getTemplate().hasDateOfBirth()) throw new InvalidArgumentException("Command must have DoB!");
        if (cmd.getTemplate().hasIprsDetails()) throw new InvalidArgumentException("Command cannot have Iprs Details!");
    }

    private void validate(UpdatePersonDetailsCmd cmd) {
        if (!cmd.hasTemplate()) throw new InvalidArgumentException("Command must have template!");
        if (cmd.getTemplate().getFirstName().isBlank() &&
                cmd.getTemplate().getFirstName().isBlank()) throw new InvalidArgumentException ("Must have first or last name!");
        if (!cmd.getTemplate().hasDateOfBirth()) throw new InvalidArgumentException("Command must have DoB!");
    }

    private void validate(RecordDeathCmd cmd) {
        if (!cmd.hasTimeOfDeath()) throw new InvalidArgumentException("Command must have Date of death!");
        if (cmd.getPersonId().isBlank()) throw new InvalidArgumentException("Person id cannot be null");
    }
}