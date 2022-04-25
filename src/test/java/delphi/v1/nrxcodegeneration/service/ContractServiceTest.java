package delphi.v1.nrxcodegeneration.service;

import java.util.List;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import delphi.backend.nrxcodegeneration.service.ContractService;
import delphi.v1.nrxcodegeneration.data.MetacodeMockDataService;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.repository.ContractRepository;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContractServiceTest {

    private MetacodeMockDataService mockService = new MetacodeMockDataService();

    @InjectMocks
    private ContractService contractService;

    @Mock
    private ContractRepository contractRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    /**
     * Test for a valid and existing contract name.
     */
    @Test
    void testContractForValidAndExistingContractName() {
        // Arrange
        var contractName = "no contract";
        var contracts = mockService.getAllContracts();
        var contractNames = List.of(contractName);

        when(contractRepository.findByContractIn(contractNames)).thenReturn(contracts);

        //Act
        var actualContract = contractService.findContractByContractNameThrowErrorIfNotFound(contracts, contractName);

        // Assert
        assertEquals(contractName, actualContract.getContract());
    }

    /**
     * Test for a valid and existing contract name.
     */
    @Test
    void testNrxContractForInvalidAndNonExistingContractName() {
        // Arrange
        var contractName = "non-existing-contract-name";
        var contracts = mockService.getAllContracts();
        var contractNames = List.of(contractName);

        when(contractRepository.findByContractIn(contractNames)).thenReturn(contracts);

        // Act
        Throwable exception = assertThrows(BadRequestException.class,
                () -> contractService.findContractByContractNameThrowErrorIfNotFound(contracts, contractName));

        // Assert
        assertEquals(String.format(NrxCodeConstant.NOT_FOUND, "Contract"), exception.getMessage());
    }
}
