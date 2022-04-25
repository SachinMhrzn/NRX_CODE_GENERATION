package delphi.backend.nrxcodegeneration.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Entity
@Table(name = NrxCodeConstant.NRX_CONCEPT_TABLE, schema = NrxCodeConstant.CHEMREG_SCHEMA)
@Immutable
@Data
public class Concept {
    /**
     * ID for the concept.
     * Cannot be Null.
     */
    @Id
    @Column(name = "CONCEPT_ID")
    private Long id;

    /**
     * Concept value for the nrx-concept.
     */
    @Column(name = "CONCEPT")
    private String concept;
}
