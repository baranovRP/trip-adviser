package org.baranov.germes.app.service.impl;

import org.baranov.germes.app.model.entity.geography.City;
import org.baranov.germes.app.model.entity.geography.Station;
import org.baranov.germes.app.model.search.criteria.StationCriteria;
import org.baranov.germes.app.model.search.criteria.range.RangeCriteria;
import org.baranov.germes.app.persistence.repository.CityRepository;
import org.baranov.germes.app.persistence.repository.StationRepository;
import org.baranov.germes.app.service.GeographicService;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Default implementation of the {@link GeographicService}
 */
public class GeographicServiceImpl implements GeographicService {

    private final CityRepository cityRepository;
    private final StationRepository stationRepository;

    @Inject
    public GeographicServiceImpl(CityRepository cityRepository,
                                 StationRepository stationRepository) {
        this.cityRepository = cityRepository;
        this.stationRepository = stationRepository;
    }

    @Override
    public List<City> findCities() {
        return cityRepository.findAll();
    }

    @Override
    public void saveCity(City city) {
        cityRepository.save(city);
    }

    @Override
    public Optional<City> findCityById(final int id) {
        return Optional.ofNullable(cityRepository.findById(id));
    }

    @Override
    public List<Station> searchStations(final StationCriteria criteria, final RangeCriteria rangeCriteria) {
        return stationRepository.findAllByCriteria(criteria);
    }
}