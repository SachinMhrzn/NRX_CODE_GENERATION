package delphi.backend.nrxcodegeneration.service;

import java.util.List;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Collectors;

import delphi.backend.nrxcodegeneration.model.Contract;
import delphi.backend.nrxcodegeneration.model.MetacodeDto;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.repository.ContractRepository;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

@Singleton
public class ContractService {

    @Inject
    private ContractRepository contractRepository;

    /**
     * Given a list of metacodeDto objects, return a list of nrxContract objects
     *
     * @param metacodeDtoList A list of MetacodeDto objects.
     * @return A list of Contracts.
     */
    public List<Contract> getContractsForMetacodeList(List<MetacodeDto> metacodeDtoList) {
        var contracts = metacodeDtoList.stream()
                .map(MetacodeDto::getContract)
                .collect(Collectors.toList());

        return contracts.isEmpty()
                ? new ArrayList<>()
                : contractRepository.findByContractIn(contracts);
    }

    /**
     * Find the Contract by contract and throw an error if not found.
     *
     * @param contracts    The list of contracts.
     * @param contractName The contract name to be searched for.
     * @return Contract.
     */
    public Contract findContractByContractNameThrowErrorIfNotFound(List<Contract> contracts, String contractName) {
        return contracts.stream()
                .filter(nrxContract -> nrxContract.getContract().equals(contractName))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(String.format(NrxCodeConstant.NOT_FOUND, "Contract")));
    }
}
