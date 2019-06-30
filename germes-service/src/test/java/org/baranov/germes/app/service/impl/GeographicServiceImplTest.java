package org.baranov.germes.app.service.impl;

import org.baranov.germes.app.model.entity.geography.City;
import org.baranov.germes.app.service.GeographicService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Contain unit-tests for {@link GeographicServiceImpl}
 */
public class GeographicServiceImplTest {
    private GeographicService service;

    @Before
    public void setup() {
        service = new GeographicServiceImpl();
    }

    @Test
    public void testNoDataReturnedAtStart() {
        assertThat(service.findCities()).isEmpty();
    }

    @Test
    public void testSaveNewCitySuccess() {
        String name = "Odessa";
        City city = new City(name);
        service.saveCity(city);

        List<City> cities = service.findCities();
        assertThat(cities).hasSize(1);
        assertThat(cities.get(0).getName()).isEqualTo(name);
    }
}