package delphi.backend.nrxcodegeneration.service;

import java.util.List;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Collectors;

import delphi.backend.nrxcodegeneration.model.Series;
import delphi.backend.nrxcodegeneration.model.MetacodeDto;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.repository.SeriesRepository;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

@Singleton
public class SeriesService {

    @Inject
    private SeriesRepository seriesRepository;

    /**
     * Given a list of metacodeDto objects, return a list of nrxSeries objects
     *
     * @param metacodeDtoList A list of MetacodeDto objects.
     * @return A list of Series objects.
     */
    public List<Series> getSeriesForMetacodeList(List<MetacodeDto> metacodeDtoList) {
        var seriesList = metacodeDtoList.stream()
                .map(MetacodeDto::getSeries)
                .collect(Collectors.toList());

        return seriesList.isEmpty()
                ? new ArrayList<>()
                : seriesRepository.findBySeriesIn(seriesList);
    }

    /**
     * Find the Series object in the list of Series objects that has the same series as the series parameter
     *
     * @param seriesList The list of series.
     * @param seriesName The series to be searched for.
     * @return The Series object.
     */
    public Series findSeriesBySeriesNameThrowErrorIfInvalid(List<Series> seriesList, String seriesName) {
        if (seriesName == null) return null;

        return seriesList.stream()
                .filter(series -> series.getSeries().equals(seriesName))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(String.format(NrxCodeConstant.NOT_FOUND, "Series")));
    }
}
