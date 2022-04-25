package delphi.backend.nrxcodegeneration.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Entity
@Table(name = NrxCodeConstant.NRX_CONTRACT_TABLE, schema = NrxCodeConstant.CHEMREG_SCHEMA)
@Immutable
@Data
public class Contract {
    /**
     * ID for the contract.
     * Cannot be Null.
     */
    @Id
    @Column(name = "CONTRACT_ID")
    private Long id;

    /**
     * Contract value for the nrx-contract.
     */
    @Column(name = "CONTRACT")
    private String contract;

    @Column(name = "OIC")
    private char oic;
}
