package delphi.backend.nrxcodegeneration.repository;

import java.util.List;
import java.util.Optional;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import delphi.backend.nrxcodegeneration.model.Series;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Repository
public interface SeriesRepository extends CrudRepository<Series, Long> {

    /**
     * Find a NrxSeries by its series
     *
     * @param series The series to search for.
     * @return An Optional<NrxSeries> object.
     */
    Optional<Series> findBySeries(String series);


    /**
     * Find a list of all the series in the database, ordered by series name
     *
     * @return A list of NrxSeries objects.
     */
    List<Series> listOrderBySeries();

    /**
     * Find all the NrxSeries objects that have a series value that is in the seriesList
     *
     * @param seriesList a list of series names
     * @return A list of NrxSeries objects.
     */
    List<Series> findBySeriesIn(List<String> seriesList);
}
