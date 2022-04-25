package delphi.backend.nrxcodegeneration.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import delphi.backend.nrxcodegeneration.model.Metacode;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Repository
public interface MetacodeRepository extends CrudRepository<Metacode, Long> {
}
