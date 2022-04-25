package delphi.backend.nrxcodegeneration.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Entity
@Table(name = NrxCodeConstant.NRX_PROGRAM_TABLE, schema = NrxCodeConstant.CHEMREG_SCHEMA)
@Immutable
@Data
public class Program {
    /**
     * ID for the program.
     * Cannot be Null.
     */
    @Id
    @Column(name = "PROGRAM_ID")
    private Long id;

    /**
     * Name of the program.
     */
    @Column(name = "PROGRAM")
    private String program;
}
