package delphi.backend.nrxcodegeneration.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

import io.micronaut.core.annotation.Introspected;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class MetacodeDto {

    /**
     * Generated metacode.
     */
    private Long metaCode;

    /**
     * Id to map the metacode. Required when metacode is generated in bulk.
     */
    private Long id;

    /**
     * Hold the message if metacode generation is unsuccessful.
     */
    private String message;

    /**
     * Name of the program from NRX_PROGRAM table.
     * Should not be null and empty.
     */
    @NotNull(message = NrxCodeConstant.REQUIRED_PROGRAM)
    @NotEmpty(message = NrxCodeConstant.NON_EMPTY_PROGRAM)
    private String program;

    /**
     * Name of the contract from NRX_CONTRACT table.
     * Should not be null and empty.
     */
    @NotNull(message = NrxCodeConstant.REQUIRED_CONTRACT)
    @NotEmpty(message = NrxCodeConstant.NON_EMPTY_CONTRACT)
    private String contract;

    /**
     * Name of the concept from NRX_CONCEPT table.
     * Should not be null and empty.
     */
    @NotNull(message = NrxCodeConstant.REQUIRED_CONCEPT)
    @NotEmpty(message = NrxCodeConstant.NON_EMPTY_CONCEPT)
    private String concept;

    /**
     * Name of the variant form the NRX_VARIANT table.
     * Should not be null and empty.
     */
    @NotNull(message = NrxCodeConstant.REQUIRED_VARIANT)
    @NotEmpty(message = NrxCodeConstant.NON_EMPTY_VARIANT)
    private String variant;

    /**
     * Name of the target from NRX_TARGETS table.
     * Should not be null and empty.
     */
    @NotNull(message = NrxCodeConstant.REQUIRED_TARGET_ONE)
    @NotEmpty(message = NrxCodeConstant.NON_EMPTY_TARGET_ONE)
    private String targetOne;

    /**
     * Name of the target from NRX_TARGETS table.
     * Should not be empty.
     */
    @NotEmpty(message = NrxCodeConstant.NON_EMPTY_TARGET_TWO)
    private String targetTwo;

    /**
     * Name of the series from NRX_SERIES table.
     * Should not be empty.
     */
    @NotEmpty(message = NrxCodeConstant.NON_EMPTY_SERIES)
    private String series;

    /**
     * DEL Lineage String.
     * If concept is DEL ligand and variant is onDNA or cleaved, then delLineage should not null.
     * Should not be empty.
     */
    @NotEmpty(message = NrxCodeConstant.NON_EMPTY_DEL_LINEAGE)
    private String delLineage;

    /**
     * ID for TargetOne..
     */
    @JsonIgnore
    private Long targetOneId;

    /**
     * ID for TargetTwo.
     */
    @JsonIgnore
    private Long targetTwoId;

    /**
     * ID for Program.
     */
    @JsonIgnore
    private Long programId;

    /**
     * ID for Contract.
     */
    @JsonIgnore
    private Long contractId;

    /**
     * ID for Concept.
     */
    @JsonIgnore
    private Long conceptId;

    /**
     * ID for Variant.
     */
    @JsonIgnore
    private Long variantId;

    /**
     * ID for Series.
     */
    @JsonIgnore
    private Long seriesId;
}
