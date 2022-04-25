package delphi.backend.nrxcodegeneration.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Entity
@Table(name = NrxCodeConstant.NRX_VARIANT_TABLE, schema = NrxCodeConstant.CHEMREG_SCHEMA)
@Immutable
@Data
public class Variant {
    /**
     * ID for the varaint.
     * Cannot be Null.
     */
    @Id
    @Column(name = "VARIANT_ID")
    private Long id;

    /**
     * Value for variant.
     */
    @Column(name = "VARIANT")
    private String variant;
}
