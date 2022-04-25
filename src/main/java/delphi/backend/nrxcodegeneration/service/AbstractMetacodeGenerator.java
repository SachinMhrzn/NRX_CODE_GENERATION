package delphi.backend.nrxcodegeneration.service;

import java.util.Optional;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import delphi.backend.nrxcodegeneration.model.Metacode;
import delphi.backend.nrxcodegeneration.interfaces.IMetacodeGenerator;
import delphi.backend.nrxcodegeneration.repository.MetacodeRepository;
import delphi.backend.nrxcodegeneration.repository.AbstractMetacodeRepository;
import delphi.backend.nrxcodegeneration.foundation.error.exception.MetacodeGenerationException;

public abstract class AbstractMetacodeGenerator implements IMetacodeGenerator {

    @Inject
    private MetacodeRepository metacodeRepository;

    @Inject
    private AbstractMetacodeRepository abstractMetacodeRepository;

    /**
     * Fetches the existing metacode from the database, or creates a new one if it doesn't exist.
     *
     * @param metacode The metacode to be saved.
     * @return The metacode that was saved.
     */
    public Metacode getMetacode(Metacode metacode) {
        var existingMetacode = abstractMetacodeRepository.fetchExistingMetacode(metacode);

        return existingMetacode != null
                ? existingMetacode
                : saveMetacode(metacode);
    }

    /**
     * Create a new NrxMetacode object and save it to the database.
     *
     * We are parsing message that we get from `.getClause().getClause().getMessage()`
     * <p>
     * Example of message that we are getting:
     * - Using `.getMessage()`
     * ```
     * org.hibernate.exception.GenericJDBCException: could not execute statement
     * ```
     * <p>
     * - Using `getCause().getMessage()`
     * ```
     * could not execute statement
     * ```
     * <p>
     * - Using `getCause().getCause().getMessage()`
     * ```
     * ORA-20004: A Concept of DEL ligand requires a DEL Lineage.
     * ORA-06512: at "CHEMREG.NRX_METACODE_BIU", line 153
     * ORA-04088: error during execution of trigger 'CHEMREG.NRX_METACODE_BIU'
     * ```
     * <p>
     * ` A Concept of DEL ligand requires a DEL Lineage.` message is being parsed and use as exception message.
     *
     * @param metacode The NrxMetacode object to be created.
     * @return NrxMetacode.
     */
    private Metacode saveMetacode(Metacode metacode) {
        try {
            return metacodeRepository.save(metacode);

        } catch (PersistenceException persistenceException) {
            var errorMsg = persistenceException.getCause().getCause().getMessage();
            var preparedErrorMsg = getErrorMessage(errorMsg);

            throw new MetacodeGenerationException(preparedErrorMsg.orElse(errorMsg));
        }
    }

    /**
     * Given a string that is the error message from a SQL exception, return an optional string that is the error message
     *
     * @param sqlExceptionErrorMsg The error message from the exception.
     * @return The first line of the SQL exception message.
     */
    private Optional<String> getErrorMessage(String sqlExceptionErrorMsg) {
        var splitMsg = sqlExceptionErrorMsg.split("\n");

        if (splitMsg.length <= 0) {
            return Optional.empty();
        }
        var errorMsg = splitMsg[0].split(": ");
        return Optional.of(errorMsg.length > 0 ? errorMsg[1] : null);
    }
}
