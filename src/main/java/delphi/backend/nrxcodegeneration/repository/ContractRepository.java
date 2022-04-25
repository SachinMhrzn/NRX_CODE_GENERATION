package delphi.backend.nrxcodegeneration.repository;

import java.util.List;
import java.util.Optional;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import delphi.backend.nrxcodegeneration.model.Contract;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Repository
public interface ContractRepository extends CrudRepository<Contract, Long> {

    /**
     * Find a NrxContract by its contract.
     *
     * @param contract The contract of the contract to be found.
     * @return An Optional object.
     */
    Optional<Contract> findByContract(String contract);


    /**
     * List all contracts in the database, ordered by contract.
     *
     * @return A list of NrxContract objects.
     */
    List<Contract> listOrderByContract();

    /**
     * Find all contracts that are in the list of contracts.
     *
     * @param contracts a list of contracts
     * @return A List of NrxContract objects.
     */
    List<Contract> findByContractIn(List<String> contracts);
}
