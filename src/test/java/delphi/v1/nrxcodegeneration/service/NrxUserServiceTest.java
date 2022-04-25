package delphi.v1.nrxcodegeneration.service;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import delphi.backend.nrxcodegeneration.service.NrxUserService;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.repository.NrxUserRepository;
import delphi.v1.nrxcodegeneration.data.NrxProjectCodeMockDataService;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NrxUserServiceTest {

    private NrxProjectCodeMockDataService mockService = new NrxProjectCodeMockDataService();

    @InjectMocks
    private NrxUserService nrxUserService;

    @Mock
    private NrxUserRepository nrxUserRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    /**
     * Test for valid and existing username.
     */
    @Test
    void testNrxUserForValidAndExistingUsername() {
        // Arrange
        String validUsername = "Sam";
        var nrxUserList = mockService.findNrxUsersByUsername(validUsername);

        when(nrxUserRepository.findByUsername(anyString())).thenReturn(nrxUserList);

        //Act
        var actualUser = nrxUserService.findNrxUserByUsernameThrowErrorIfNotFound(anyString());

        // Assert
        assertEquals(validUsername, actualUser.getUsername());
    }

    /**
     * Test for invalid and non-existing username.
     */
    @Test
    void testNrxUserForInvalidAndNonExistingUsername() {
        // Arrange
        var invalidUsername = "invalid-username";
        var nrxUserList = mockService.findNrxUsersByUsername(invalidUsername);

        when(nrxUserRepository.findByUsername(anyString())).thenReturn(nrxUserList);

        // Act
        Throwable exception = assertThrows(BadRequestException.class,
                () -> nrxUserService.findNrxUserByUsernameThrowErrorIfNotFound(anyString()));

        // Assert
        assertEquals(String.format(NrxCodeConstant.NOT_FOUND, "User"), exception.getMessage());
    }
}
