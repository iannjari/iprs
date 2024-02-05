package dev.njari.person.service;


import iprs.person.v1.RegisterBirthCmd;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author njari_mathenge
 * on 05/02/2024.
 * github.com/iannjari
 */

@Slf4j
@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    RegistrationService registrationService;
    @Test
    void testTests() {
        registrationService.registerBirth(RegisterBirthCmd.newBuilder().build());

        assertTrue(true);
    }
}