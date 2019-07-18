package org.baranov.germes.app.service.impl;

import org.baranov.germes.app.model.entity.geography.City;
import org.baranov.germes.app.model.entity.geography.Station;
import org.baranov.germes.app.model.entity.transport.TransportType;
import org.baranov.germes.app.model.search.criteria.StationCriteria;
import org.baranov.germes.app.model.search.criteria.range.RangeCriteria;
import org.baranov.germes.app.persistence.hibernate.SessionFactoryBuilder;
import org.baranov.germes.app.persistence.repository.CityRepository;
import org.baranov.germes.app.persistence.repository.StationRepository;
import org.baranov.germes.app.persistence.repository.hibernate.HibernateCityRepository;
import org.baranov.germes.app.persistence.repository.hibernate.HibernateStationRepository;
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
        SessionFactoryBuilder builder = new SessionFactoryBuilder();
        CityRepository repository = new HibernateCityRepository(builder);
        StationRepository stationRepository = new HibernateStationRepository(builder);
        service = new GeographicServiceImpl(repository, stationRepository);
    }

    @Test
    public void testNoDataReturnedAtStart() {
        assertThat(service.findCities()).isNotEmpty();
    }

    @Test
    public void testSaveNewCitySuccess() {
        City city = createCity();
        service.saveCity(city);

        List<City> cities = service.findCities();
        assertThat(cities).hasSize(5);
        assertThat(cities.get(0).getName()).isEqualTo("Odessa");
    }

    @Test
    public void testFindCityByIdSuccess() {
        City city = createCity();
        service.saveCity(city);

        Optional<City> foundCity = service.findCityById(DEFAULT_CITY_ID);
        assertThat(foundCity).isPresent();
        assertThat(foundCity.get().getId()).isEqualTo(DEFAULT_CITY_ID);
    }

    @Test
    public void testFindCityByIdNotFound() {
        Optional<City> foundCity = service.findCityById(DEFAULT_CITY_ID);
        assertThat(foundCity.isPresent()).isTrue();
    }

    @Test
    public void testSearchStationsByNameSuccess() {
        City city = createCity();
        city.addStation(TransportType.AUTO);
        city.addStation(TransportType.RAILWAY);
        service.saveCity(city);

        List<Station> stations = service.searchStations(StationCriteria.byName("Odessa"), new RangeCriteria(1, 5));
        assertThat(stations).isNotEmpty().hasSize(2);
        assertThat(stations.get(0).getCity()).isEqualTo(city);
    }

    @Test
    public void testSearchStationsByNameNotFound() {
        List<Station> stations = service.searchStations(StationCriteria.byName("London"), new RangeCriteria(1, 5));
        assertThat(stations).isNotNull().isEmpty();
    }

    @Test
    public void testSearchStationsByTransportTypeSuccess() {
        City city = createCity();
        city.addStation(TransportType.AUTO);
        service.saveCity(city);
        City city2 = new City("Kyiv");
        city2.setDistrict("Kiev");
        city2.setRegion("Kiev");
        city2.addStation(TransportType.AUTO);
        service.saveCity(city2);

        List<Station> stations = service.searchStations(new StationCriteria(TransportType.AUTO), new RangeCriteria(1, 5));
        assertThat(stations).isNotEmpty().hasSize(3);
    }

    @Test
    public void testSearchStationsByTransportTypeNotFound() {
        City city = createCity();
        city.addStation(TransportType.AUTO);
        service.saveCity(city);
        City city2 = new City("Kyiv ");
        city2.setId(2);
        city2.addStation(TransportType.RAILWAY);
        service.saveCity(city2);

        List<Station> stations = service.searchStations(new StationCriteria(TransportType.AVIA), new RangeCriteria(1, 5));
        assertThat(stations).isNotNull().isEmpty();
    }

    private City createCity() {
        City city = new City("Odessa");
        city.setDistrict("Odessa");
        city.setRegion("Odessa");

        return city;
    }
}