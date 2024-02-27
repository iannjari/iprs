package dev.njari.person.service;


import com.google.protobuf.Timestamp;
import dev.njari.common_utils.exception.custom_exception.InvalidArgumentException;
import iprs.person.v1.IprsDetails;
import iprs.person.v1.Person;
import iprs.person.v1.RegisterBirthCmd;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author njari_mathenge
 * on 05/02/2024.
 * github.com/iannjari
 */

@Slf4j
@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @InjectMocks
    RegistrationService registrationService;
    @Test
    @DisplayName("Given a command without a template, fails")
    void failsOnNoTemplate() {
        Exception exception = assertThrows(InvalidArgumentException.class,
                ()-> registrationService.registerBirth(RegisterBirthCmd.newBuilder().build()));

        assertEquals("Command must have template!", exception.getMessage());
    }

    @Test
    @DisplayName("Should not have all names being blank")
    void failsOnAllBlankNames() {
        RegisterBirthCmd cmd = RegisterBirthCmd.newBuilder()
                .setTemplate(Person.newBuilder()
                        .setDateOfBirth(Timestamp.newBuilder()
                                .build())
                        .build())
                .build();

        Exception exception = assertThrows(InvalidArgumentException.class,
                ()-> registrationService.registerBirth(cmd));

        assertEquals("Must have first or last name!", exception.getMessage());
    }

    @Test
    @DisplayName("Given a template without a time of birth, fails")
    void failsOnNoDoB() {
        RegisterBirthCmd cmd = RegisterBirthCmd.newBuilder()
                .setTemplate(Person.newBuilder()
                        .setFirstName("John")
                        .build())
                .build();

        Exception exception = assertThrows(InvalidArgumentException.class,
                ()-> registrationService.registerBirth(cmd));

        assertEquals("Command must have DoB!", exception.getMessage());
    }
}