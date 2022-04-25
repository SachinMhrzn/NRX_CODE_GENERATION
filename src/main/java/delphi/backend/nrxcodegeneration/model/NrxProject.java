package delphi.backend.nrxcodegeneration.model;

import javax.persistence.*;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Entity
@Table(name = NrxCodeConstant.PROJECT_TABLE, schema = NrxCodeConstant.CHEMREG_SCHEMA)
@Immutable
@Data
public class NrxProject {

    /**
     * ChemReg-Project-Id for the project.
     * Cannot be Null.
     */
    @Id
    @Column(name = "PROJECT_ID")
    private Long id;

    /**
     * ChemReg-Project-Name for the project.
     */
    @Column(name = "PROJECT_NAME")
    private String name;
}
