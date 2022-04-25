package delphi.backend.nrxcodegeneration.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Entity
@Table(name = NrxCodeConstant.NRX_SERIES_TABLE, schema = NrxCodeConstant.CHEMREG_SCHEMA)
@Immutable
@Data
public class Series {
    /**
     * ID for the series.
     * Cannot be Null.
     */
    @Id
    @Column(name = "SERIES_ID")
    private Long id;

    /**
     * Value for series.
     */
    @Column(name = "SERIES")
    private String series;
}
