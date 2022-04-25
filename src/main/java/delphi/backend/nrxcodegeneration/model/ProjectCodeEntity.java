package delphi.backend.nrxcodegeneration.model;

import javax.persistence.*;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Entity
@Table(name = NrxCodeConstant.NRX_PROJECTCODE_TABLE, schema = NrxCodeConstant.CHEMREG_SCHEMA)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCodeEntity {

    /**
     * Project-Code-ID for the project-code-entity.
     * Cannot be Null.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PCODE_ID")
    private Long projectCodeID;

    /**
     * Generated Project-Code for the project-code-entity.
     */
    @Column(name = "PROJECT_CODE")
    private String projectCode;

    /**
     * Project-ID from the Chemreg PROJECT table.
     */
    @Column(name = "PROJECT_PID")
    private Long projectPId;

    /**
     * Personnel-ID from the Chemreg PERSONNEL table.
     */
    @Column(name = "PERSON_PID")
    private Long personPId;

    /**
     * Generated Project-Code.
     */
    @Column(name = "USERPCODE")
    private String userPCode;

    /**
     * Target-ID from the Chemreg - NRX_TARGETS table.
     */
    @Column(name = "TARGET_ID")
    private Long targetId;

    /**
     * Property that tells it the project-code is currently active or not.
     */
    @Builder.Default
    private String active = NrxCodeConstant.PROJECT_CODE_ACTIVE_STATUS;
}
