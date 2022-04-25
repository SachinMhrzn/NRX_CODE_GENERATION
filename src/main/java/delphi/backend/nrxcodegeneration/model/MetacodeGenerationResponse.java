package delphi.backend.nrxcodegeneration.model;

import java.util.List;
import java.util.ArrayList;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetacodeGenerationResponse {

    /**
     * A boolean flag indicating if all the metacode has been generated successfully.
     */
    @Builder.Default
    private Boolean error = false;

    /**
     * List of successfully generated metacode.
     */
    @Builder.Default
    private List<MetacodeDto> succeeded = new ArrayList<>();

    /**
     * List of unsuccessfully generated metacode, along with reason describing why it could not be generated.
     */
    @Builder.Default
    private List<MetacodeDto> failed = new ArrayList<>();
}
