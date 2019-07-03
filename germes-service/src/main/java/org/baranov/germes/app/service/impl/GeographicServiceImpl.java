package org.baranov.germes.app.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.baranov.germes.app.infra.util.CommonUtil;
import org.baranov.germes.app.model.entity.geography.City;
import org.baranov.germes.app.model.entity.geography.Station;
import org.baranov.germes.app.model.search.criteria.StationCriteria;
import org.baranov.germes.app.model.search.criteria.range.RangeCriteria;
import org.baranov.germes.app.service.GeographicService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public GeographicServiceImpl() {
        cities = new ArrayList<>();
    }

    @Override
    public List<City> findCities() {
        return CommonUtil.getSafeList(cities);
    }

    @Override
    public void saveCity(City city) {
        if (!cities.contains(city)) {
            city.setId(++counter);
            cities.add(city);
        }
    }

    @Override
    public Optional<City> findCityById(final int id) {
        return cities.stream().filter(city -> city.getId() == id).findAny();
    }

    @Override
    public List<Station> searchStations(final StationCriteria criteria, final RangeCriteria rangeCriteria) {
        Stream<City> stream = cities.stream().filter(
                city -> StringUtils.isEmpty(criteria.getName()) || city.getName().equals(criteria.getName()));

        Optional<Set<Station>> stations = stream.map(City::getStations).reduce((stations1, stations2) -> {
            Set<Station> newStations = new HashSet<>(stations2);
            newStations.addAll(stations1);
            return newStations;
        });
        return stations.map(stationSet -> stationSet.stream()
                .filter(station -> criteria.getTransportType() == null
                        || station.getTransportType() == criteria.getTransportType())
                .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}