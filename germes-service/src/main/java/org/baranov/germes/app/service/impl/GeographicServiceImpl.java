package org.baranov.germes.app.service.impl;

import org.baranov.germes.app.infra.util.CommonUtil;
import org.baranov.germes.app.model.entity.geography.City;
import org.baranov.germes.app.service.GeographicService;

import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of the {@link GeographicService}
 */
public class GeographicServiceImpl implements GeographicService {
    /**
     * Internal list of cities
     */
    private final List<City> cities;

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
            cities.add(city);
        }
    }

}