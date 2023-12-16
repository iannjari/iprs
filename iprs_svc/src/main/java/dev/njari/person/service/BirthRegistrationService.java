package dev.njari.person.service;

import dev.njari.person.repository.PersonRepository;
import iprs.person.v1.Person;
import iprs.person.v1.RegisterBirthCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BirthRegistrationService {

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

    private void validate(RegisterBirthCmd cmd) {
        if (!cmd.hasTemplate()) throw new IllegalArgumentException("Command must have template!");
        if (cmd.getTemplate().getFirstName().isBlank() &&
                cmd.getTemplate().getFirstName().isBlank()) throw new IllegalArgumentException ("Must have first or last name!");
        if (!cmd.getTemplate().hasDateOfBirth()) throw new IllegalArgumentException("Command must have DoB!");
        if (!cmd.getTemplate().hasIprsDetails()) throw new IllegalArgumentException("Command must have Iprs Details!");
    }
}