package delphi.v1.nrxcodegeneration.service;

import java.util.List;
import java.util.ArrayList;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import delphi.backend.nrxcodegeneration.service.SeriesService;
import delphi.v1.nrxcodegeneration.data.MetacodeMockDataService;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.repository.SeriesRepository;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SeriesServiceTest {

    private MetacodeMockDataService mockService = new MetacodeMockDataService();

    @InjectMocks
    private SeriesService seriesService;

    @Mock
    private SeriesRepository seriesRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    /**
     * Test for a valid and existing series name.
     */
    @Test
    void testSeriesForValidAndExistingSeriesName() {
        // Arrange
        var seriesName = "Tracer";
        var seriesList = mockService.getAllSeries();
        var seriesNames = List.of(seriesName);

        when(seriesRepository.findBySeriesIn(seriesNames)).thenReturn(seriesList);

        //Act
        var actualSeries = seriesService.findSeriesBySeriesNameThrowErrorIfInvalid(seriesList, seriesName);

        // Assert
        assertEquals(seriesName, actualSeries.getSeries());
    }

    /**
     * Test for null value for series name.
     */
    @Test
    void testSeriesForNullSeriesName() {
        // Arrange
        String seriesName = null;
        var seriesList = mockService.getAllSeries();
        var seriesNames = new ArrayList<String>();

        when(seriesRepository.findBySeriesIn(seriesNames)).thenReturn(seriesList);

        //Act
        var actualSeries = seriesService.findSeriesBySeriesNameThrowErrorIfInvalid(seriesList, seriesName);

        // Assert
        assertEquals(seriesName, actualSeries);
    }

    /**
     * Test for a valid and existing series name.
     */
    @Test
    void testSeriesForInvalidAndNonExistingSeriesName() {
        // Arrange
        var seriesName = "non-existing-series-name";
        var seriesList = mockService.getAllSeries();
        var seriesNames = List.of(seriesName);

        when(seriesRepository.findBySeriesIn(seriesNames)).thenReturn(seriesList);

        // Act
        Throwable exception = assertThrows(BadRequestException.class,
                () -> seriesService.findSeriesBySeriesNameThrowErrorIfInvalid(seriesList, seriesName));

        // Assert
        assertEquals(String.format(NrxCodeConstant.NOT_FOUND, "Series"), exception.getMessage());
    }
}
