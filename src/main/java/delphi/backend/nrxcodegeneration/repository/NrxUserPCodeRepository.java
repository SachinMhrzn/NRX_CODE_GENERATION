package delphi.backend.nrxcodegeneration.repository;

import java.util.List;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import delphi.backend.nrxcodegeneration.model.NrxUserPCode;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Repository
public interface NrxUserPCodeRepository extends CrudRepository<NrxUserPCode, String> {

    @Query(value = "SELECT USERPCODE FROM CHEMREG.NRX_USERPCODE"
            + " WHERE REGEXP_LIKE (USERPCODE, 'DEL[0-9]{4,}')"
            + " ORDER BY CAST(LTRIM(SUBSTR(USERPCODE, 4, LENGTH(USERPCODE) - 1), '0') AS NUMBER) DESC"
            + " FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
    List<NrxUserPCode> fetchLatestDelAppendedProjectCode();
}
