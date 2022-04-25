package delphi.backend.nrxcodegeneration.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Entity
@Table(name = NrxCodeConstant.NRX_TARGETS_TABLE, schema = NrxCodeConstant.CHEMREG_SCHEMA)
@Immutable
@Data
public class NrxTarget {

    /**
     * ChemReg-Target-ID for the target.
     * Cannot be Null.
     */
    @Id
    @Column(name = "TARGET_ID")
    private Long id;

    /**
     * ChemReg-Target-Name for the target.
     */
    @Column(name = "TARGET")
    private String name;
}
