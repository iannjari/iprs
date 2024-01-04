package dev.njari.person.service;

import dev.njari.person.repository.PersonRepository;
import iprs.person.v1.Person;
import iprs.person.v1.RecordDeathCmd;
import iprs.person.v1.RegisterBirthCmd;
import iprs.person.v1.UpdatePersonDetailsCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final PersonRepository personRepository;

    public RegisterBirthCmd registerBirth(RegisterBirthCmd cmd) {
        // validate
        validate(cmd);
        // save
        Person person = personRepository.save(cmd.getTemplate());
        return cmd.toBuilder()
                .setTemplate(person)
                .build();
    }

    public UpdatePersonDetailsCmd updatePersonDetails(UpdatePersonDetailsCmd cmd) {
        // validate
        validate(cmd);
        // update and save
        Person person = personRepository.save(cmd.getTemplate());

        return cmd.toBuilder()
                .setTemplate(person)
                .build();
    }

    public RecordDeathCmd recordDeath(RecordDeathCmd cmd) {
        // validate
        validate(cmd);
        // update and save
        Person person = personRepository.findById(cmd.getPersonId());

        if (Objects.isNull(person)) throw new RuntimeException("Person with id: "
                .concat(cmd.getPersonId()).concat(" not found"));

        personRepository.save(person.toBuilder().setDateOfDeath(cmd.getTimeOfDeath()).setIsAlive(false).build());

        return cmd;
    }

    private void validate(RegisterBirthCmd cmd) {
        if (!cmd.hasTemplate()) throw new IllegalArgumentException("Command must have template!");
        if (cmd.getTemplate().getFirstName().isBlank() &&
                cmd.getTemplate().getFirstName().isBlank()) throw new IllegalArgumentException ("Must have first or last name!");
        if (!cmd.getTemplate().hasDateOfBirth()) throw new IllegalArgumentException("Command must have DoB!");
        if (!cmd.getTemplate().hasIprsDetails()) throw new IllegalArgumentException("Command must have Iprs Details!");
    }

    private void validate(UpdatePersonDetailsCmd cmd) {
        if (!cmd.hasTemplate()) throw new IllegalArgumentException("Command must have template!");
        if (cmd.getTemplate().getFirstName().isBlank() &&
                cmd.getTemplate().getFirstName().isBlank()) throw new IllegalArgumentException ("Must have first or last name!");
        if (!cmd.getTemplate().hasDateOfBirth()) throw new IllegalArgumentException("Command must have DoB!");
        if (!cmd.getTemplate().hasIprsDetails()) throw new IllegalArgumentException("Command must have Iprs Details!");
        if (!cmd.getTemplate().getId().isBlank()) throw new IllegalArgumentException("Id cannot be null");

    }

    private void validate(RecordDeathCmd cmd) {
        if (cmd.hasTimeOfDeath()) throw new IllegalArgumentException("Command must have Date of death!");
        if (cmd.getPersonId().isBlank()) throw new IllegalArgumentException("Person id cannot be null");
    }
}