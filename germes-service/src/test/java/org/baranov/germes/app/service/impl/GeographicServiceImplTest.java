package org.baranov.germes.app.service.impl;

import org.baranov.germes.app.model.entity.geography.City;
import org.baranov.germes.app.model.entity.geography.Station;
import org.baranov.germes.app.model.entity.transport.TransportType;
import org.baranov.germes.app.model.search.criteria.StationCriteria;
import org.baranov.germes.app.model.search.criteria.range.RangeCriteria;
import org.baranov.germes.app.persistence.repository.inmemory.InMemoryCityRepository;
import org.baranov.germes.app.service.GeographicService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Contain unit-tests for {@link GeographicServiceImpl}
 */
public class GeographicServiceImplTest {
    private static final int DEFAULT_CITY_ID = 1;
    private GeographicService service;

    @Before
    public void setup() {
        service = new GeographicServiceImpl(new InMemoryCityRepository());
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

    @Test
    public void testFindCityByIdSuccess() {
        City city = new City("Odessa");
        service.saveCity(city);

        Optional<City> foundCity = service.findCityById(DEFAULT_CITY_ID);
        assertThat(foundCity).isPresent();
        assertThat(foundCity.get().getId()).isEqualTo(DEFAULT_CITY_ID);
    }

    @Test
    public void testFindCityByIdNotFound() {
        Optional<City> foundCity = service.findCityById(DEFAULT_CITY_ID);
        assertThat(foundCity).isNotPresent();
    }

    @Test
    public void testSearchStationsByNameSuccess() {
        City city = new City("Odessa");
        city.setId(DEFAULT_CITY_ID);
        city.addStation(TransportType.AUTO);
        city.addStation(TransportType.RAILWAY);
        service.saveCity(city);

        List<Station> stations = service.searchStations(StationCriteria.byName("Odessa"), new RangeCriteria(1, 5));
        assertThat(stations).isNotEmpty().hasSize(2);
        assertThat(stations.get(0).getCity()).isEqualTo(city);
    }

    @Test
    public void testSearchStationsByNameNotFound() {
        List<Station> stations = service.searchStations(StationCriteria.byName("Odessa"), new RangeCriteria(1, 5));
        assertThat(stations).isNotNull().isEmpty();
    }

    @Test
    public void testSearchStationsByTransportTypeSuccess() {
        City city = new City("Odessa");
        city.addStation(TransportType.AUTO);
        service.saveCity(city);
        City city2 = new City("Kyiv");
        city2.setId(2);
        city2.addStation(TransportType.AUTO);
        service.saveCity(city2);

        List<Station> stations = service.searchStations(new StationCriteria(TransportType.AUTO), new RangeCriteria(1, 5));
        assertThat(stations).isNotEmpty().hasSize(2);
    }

    @Test
    public void testSearchStationsByTransportTypeNotFound() {
        City city = new City("Odessa");
        city.addStation(TransportType.AUTO);
        service.saveCity(city);
        City city2 = new City("Kyiv ");
        city2.setId(2);
        city2.addStation(TransportType.RAILWAY);
        service.saveCity(city2);

        List<Station> stations = service.searchStations(new StationCriteria(TransportType.AVIA), new RangeCriteria(1, 5));
        assertThat(stations).isNotNull().isEmpty();
    }
}