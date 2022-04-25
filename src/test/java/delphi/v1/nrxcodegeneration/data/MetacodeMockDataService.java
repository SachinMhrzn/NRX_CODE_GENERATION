package delphi.v1.nrxcodegeneration.data;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.inject.Singleton;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import io.micronaut.http.server.exceptions.InternalServerException;

import delphi.backend.nrxcodegeneration.model.*;
import delphi.v1.nrxcodegeneration.constant.UnitTestConstants;
import delphi.backend.nrxcodegeneration.util.ObjectMapperFactory;

@Singleton
public class MetacodeMockDataService {

    private static final String SERIES_JSON_FILE = "src/test/resources/nrxcodegeneration/series.json";
    private static final String VARIANTS_JSON_FILE = "src/test/resources/nrxcodegeneration/variants.json";
    private static final String PROGRAMS_JSON_FILE = "src/test/resources/nrxcodegeneration/programs.json";
    private static final String CONCEPTS_JSON_FILE = "src/test/resources/nrxcodegeneration/concepts.json";
    private static final String METACODE_JSON_FILE = "src/test/resources/nrxcodegeneration/metacodes.json";
    private static final String CONTRACTS_JSON_FILE = "src/test/resources/nrxcodegeneration/contracts.json";
    private static final String NRX_TARGETS_JSON_FILE = "src/test/resources/nrxcodegeneration/nrx-targets.json";
    private static final String METACODE_DTO_JSON_FILE = "src/test/resources/nrxcodegeneration/metacode-dtos.json";

    /**
     * Get a list of all the nrx users.
     *
     * @return A List of User objects.
     */
    public List<Program> getAllPrograms() {
        TypeReference<List<Program>> TYPE_REFERENCE = new TypeReference<>() {
        };
        return parseJsonFile(TYPE_REFERENCE, PROGRAMS_JSON_FILE);
    }

    /**
     * Get a list of all  NrxTargets
     *
     * @return A list of NrxTarget objects.
     */
    public List<NrxTarget> getAllNrxTargets() {
        TypeReference<List<NrxTarget>> TYPE_REFERENCE = new TypeReference<>() {
        };
        return parseJsonFile(TYPE_REFERENCE, NRX_TARGETS_JSON_FILE);
    }

    /**
     * Get a list of all  Concepts
     *
     * @return A list of Concept objects.
     */
    public List<Concept> getAllConcepts() {
        TypeReference<List<Concept>> TYPE_REFERENCE = new TypeReference<>() {
        };
        return parseJsonFile(TYPE_REFERENCE, CONCEPTS_JSON_FILE);
    }

    /**
     * Get a list of all  Variants
     *
     * @return A list of Variant objects.
     */
    public List<Variant> getAllVariants() {
        TypeReference<List<Variant>> TYPE_REFERENCE = new TypeReference<>() {
        };
        return parseJsonFile(TYPE_REFERENCE, VARIANTS_JSON_FILE);
    }

    /**
     * Get a list of all  Contracts
     *
     * @return A list of Contract objects.
     */
    public List<Contract> getAllContracts() {
        TypeReference<List<Contract>> TYPE_REFERENCE = new TypeReference<>() {
        };
        return parseJsonFile(TYPE_REFERENCE, CONTRACTS_JSON_FILE);
    }

    /**
     * Get a list of all  SeriesList
     *
     * @return A list of Series objects.
     */
    public List<Series> getAllSeries() {
        TypeReference<List<Series>> TYPE_REFERENCE = new TypeReference<>() {
        };
        return parseJsonFile(TYPE_REFERENCE, SERIES_JSON_FILE);
    }

    /**
     * Get a list of all MetacodeDtos
     *
     * @return A list of MetacodeDto objects.
     */
    public List<MetacodeDto> getAllMetacodeDto() {
        return getMetacodeDtoMap().values().stream().collect(Collectors.toList());
    }

    /**
     * Get map of Metacodes.
     *
     * @return A Map of Metacode objects.
     */
    public Map<String, Metacode> getAllMetacodesMap() {
        TypeReference<Map<String, Metacode>> TYPE_REFERENCE = new TypeReference<>() {
        };
        return parseJsonFile(TYPE_REFERENCE, METACODE_JSON_FILE);
    }

    /**
     * Get a list of all  Metacodes
     *
     * @return A list of Metacode objects.
     */
    public List<Metacode> getAllMetacodes() {
        return getAllMetacodesMap().values().stream().collect(Collectors.toList());
    }

    /**
     * Get a map of metacode Dto.
     *
     * @return A map of metacode dto objects.
     */
    public Map<String, MetacodeDto> getMetacodeDtoMap() {
        TypeReference<Map<String, MetacodeDto>> TYPE_REFERENCE = new TypeReference<>() {
        };
        return parseJsonFile(TYPE_REFERENCE, METACODE_DTO_JSON_FILE);
    }

    /**
     * Parses json file and converts it into specified type.
     *
     * @param typeReference The TypeReference that will be used to deserialize the JSON file.
     * @param name          The name of the file to be parsed.
     * @return Parsed Object from json.
     */
    private <T> T parseJsonFile(TypeReference<T> typeReference, String name) {
        try {
            return ObjectMapperFactory.getInstance().readValue(
                    new File(name), typeReference
            );
        } catch (IOException ioException) {
            throw new InternalServerException(ioException.getMessage());
        }
    }

    /**
     * Find the Program that have the same name as the given name
     *
     * @param name The name of the program to find.
     * @return Program objects.
     */
    public Program findProgramByName(String name) {
        return getAllPrograms()
                .stream()
                .filter(nrxProgram -> nrxProgram.getProgram().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Find the NrxTargets that have the same name as the targetName parameter
     *
     * @param targetName The name of the target to find.
     * @return NrxTarget objects.
     */
    public NrxTarget findNrxTargetByName(String targetName) {
        return getAllNrxTargets()
                .stream()
                .filter(nrxTarget -> nrxTarget.getName().equals(targetName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Find the Concepts that have the same name as the conceptName parameter
     *
     * @param conceptName The name of the concept to find.
     * @return Concept objects.
     */
    public Concept findConceptByName(String conceptName) {
        return getAllConcepts()
                .stream()
                .filter(nrxConcept -> nrxConcept.getConcept().equals(conceptName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Find the variants that have the same name as the variantName parameter
     *
     * @param variantName The name of the variant to find.
     * @return variant objects.
     */
    public Variant findVariantByName(String variantName) {
        return getAllVariants()
                .stream()
                .filter(nrxVariant -> nrxVariant.getVariant().equals(variantName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Find the contracts that have the same name as the contractName parameter
     *
     * @param contractName The name of the contract to find.
     * @return contract objects.
     */
    public Contract findContractByName(String contractName) {
        return getAllContracts()
                .stream()
                .filter(nrxContract -> nrxContract.getContract().equals(contractName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Find the seriesList that have the same name as the seriesName parameter
     *
     * @param seriesName The name of the series to find.
     * @return series objects.
     */
    public Series findSeriesByName(String seriesName) {
        return getAllSeries()
                .stream()
                .filter(nrxSeries -> nrxSeries.getSeries().equals(seriesName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get the metacodeDto for the inhibitor metacode
     *
     * @return The metacodeDto for the inhibitor metacode.
     */
    public MetacodeDto getInhibitorMetacodeDto() {
        return getMetacodeDtoMap().get(UnitTestConstants.INHIBITOR_METACODE_DTO);
    }

    /**
     * Get the metacodeDto for the DEL metacode
     *
     * @return The metacodeDto for the DEL metacode.
     */
    public MetacodeDto getDelMetacodeDto() {
        return getMetacodeDtoMap().get(UnitTestConstants.DEL_METACODE_DTO);
    }

    /**
     * Get the metacodeDto for the CTM metacode
     *
     * @return The metacodeDto for the CTM metacode.
     */
    public MetacodeDto getCtmMetacodeDto() {
        return getMetacodeDtoMap().get(UnitTestConstants.CTM_METACODE_DTO);
    }

    /**
     * Get the metacodeDto for the Tracer metacode
     *
     * @return The metacodeDto for the Tracer metacode.
     */
    public MetacodeDto getTracerMetacodeDto() {
        return getMetacodeDtoMap().get(UnitTestConstants.TRACER_METACODE_DTO);
    }

    /**
     * Get the metacodeDto for the Default metacode
     *
     * @return The metacodeDto for the Default metacode.
     */
    public MetacodeDto getDefaultMetacodeDto() {
        return getMetacodeDtoMap().get(UnitTestConstants.DEFAULT_METACODE_DTO);
    }

    /**
     * Get existing metacode.
     *
     * @return The metacode in the list of metacodes.
     */
    public Metacode getExistingMetacode() {
        return getAllMetacodesMap().get(UnitTestConstants.EXISTING_METACODE);
    }

    /**
     * Get new saved metacode.
     *
     * @return The metacode in the list of metacodes.
     */
    public Metacode getNewMetacode() {
        return getAllMetacodesMap().get(UnitTestConstants.NEW_METACODE);
    }
}
