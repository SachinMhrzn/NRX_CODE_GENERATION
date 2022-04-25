package delphi.backend.nrxcodegeneration.model;

import javax.persistence.*;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Entity
@Table(name = NrxCodeConstant.NRX_METACODE_TABLE, schema = NrxCodeConstant.CHEMREG_SCHEMA)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Metacode {

    /**
     * The auto generated metacode value.
     * Cannot be Null.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "METACODE_MID")
    private Long metacodeId;

    /**
     * ID from the Nrx_Program table.
     */
    @Column(name = "PROGRAM_MID")
    private Long programId;

    /**
     * ID from the Nrx_Contract table.
     */
    @Column(name = "CONTRACT_MID")
    private Long contractId;

    /**
     * ID from the Nrx_Concept table.
     */
    @Column(name = "CONCEPT_MID")
    private Long conceptId;

    /**
     * ID from the Nrx_Variant table.
     */
    @Column(name = "VARIANT_MID")
    private Long variantId;

    /**
     * ID from the Nrx_Targets table.
     */
    @Column(name = "TARGET_1_MID")
    private Long targetOneId;

    /**
     * ID from the Nrx_Targets table.
     */
    @Column(name = "TARGET_2_MID")
    private Long targetTwoId;

    /**
     * ID from the Nrx_Series table.
     */
    @Column(name = "SERIES_MID")
    private Long seriesId;

    /**
     * If concept is DEL ligand and variant is onDNA or cleaved, then delLineage should not null.
     */
    @Column(name = "DEL_LINEAGEMC")
    private String delLineage;
}
