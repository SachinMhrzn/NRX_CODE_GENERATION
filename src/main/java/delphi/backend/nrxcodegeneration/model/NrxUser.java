package delphi.backend.nrxcodegeneration.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Entity
@Table(name = NrxCodeConstant.PERSONNEL_TABLE, schema = NrxCodeConstant.CHEMREG_SCHEMA)
@Immutable
@Data
public class NrxUser {

    /**
     * ChemReg-Personnel ID for the user.
     * Cannot be Null.
     */
    @Id
    @Column(name = "PERSON_ID")
    private Long id;

    /**
     * ChemReg-Personnel-Username for the user.
     */
    @Column(name = "USERNAME")
    private String username;
}
