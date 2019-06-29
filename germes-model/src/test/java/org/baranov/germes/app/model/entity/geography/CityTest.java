package org.baranov.germes.app.model.entity.geography;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.baranov.germes.app.model.entity.geography.City.ERR_MSG_STATION_PARAM_IS_NOT_INITIALIZED;


/**
 * Contains unit-tests to check functionality of {@link City} class
 */
public class CityTest {

    private City city;

    @Before
    public void setUp() {
        city = new City();
    }

    @Test
    public void testAddValidStationSuccess() {
        Station station = new Station();

        city.addStation(station);

        assertThat(city.getStations()).contains(station);
        assertThat(station.getCity()).isEqualTo(city);
    }

    @Test
    public void testAddNullStationFailure() {
        Throwable throwable = catchThrowable(() -> city.addStation(null));

        assertThat(throwable).isInstanceOf(NullPointerException.class);
        assertThat(throwable).hasMessage(ERR_MSG_STATION_PARAM_IS_NOT_INITIALIZED);
    }

    @Test
    public void testAddDuplicateStationFailure() {
        Station station = new Station();

        city.addStation(station);
        city.addStation(station);

        assertThat(city.getStations()).hasSize(1);
    }

    @Test
    public void testRemoveStationSuccess() {
        Station station = new Station();
        city.addStation(station);

        city.removeStation(station);

        assertThat(city.getStations()).hasSize(0);
    }

    @Test
    public void testRemoveNullStationFailure() {
        Throwable throwable = catchThrowable(() -> city.removeStation(null));

        assertThat(throwable).isInstanceOf(NullPointerException.class);
        assertThat(throwable).hasMessage(ERR_MSG_STATION_PARAM_IS_NOT_INITIALIZED);
    }
}