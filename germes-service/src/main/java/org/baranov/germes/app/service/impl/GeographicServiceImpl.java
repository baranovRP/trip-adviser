package org.baranov.germes.app.service.impl;

import org.baranov.germes.app.infra.util.CommonUtil;
import org.baranov.germes.app.model.entity.geography.City;
import org.baranov.germes.app.model.entity.geography.Station;
import org.baranov.germes.app.model.search.criteria.StationCriteria;
import org.baranov.germes.app.model.search.criteria.range.RangeCriteria;
import org.baranov.germes.app.service.GeographicService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Default implementation of the {@link GeographicService}
 */
public class GeographicServiceImpl implements GeographicService {
    /**
     * Internal list of cities
     */
    private final List<City> cities;

    /**
     * Auto-increment counter for entity id generation
     */
    private int counter = 0;

    private int stationCounter = 0;

    public GeographicServiceImpl() {
        cities = new ArrayList<>();
    }

    @Override
    public List<City> findCities() {
        return CommonUtil.getSafeList(cities);
    }

    @Override
    public void saveCity(final City city) {
        if (!cities.contains(city)) {
            city.setId(++counter);
            cities.add(city);
        }
        city.getStations().forEach(station -> {
            if (station.getId() == 0) {
                station.setId(++stationCounter);
            }
        });
    }

    @Override
    public Optional<City> findCityById(final int id) {
        return cities.stream().filter(city -> city.getId() == id).findAny();
    }

    @Override
    public List<Station> searchStations(final StationCriteria criteria, final RangeCriteria rangeCriteria) {

        return cities.stream()
                .flatMap(city -> city.getStations().stream())
                .collect(Collectors.toSet()).stream()
                .filter(station -> station.match(criteria))
                .collect(Collectors.toList());
    }
}