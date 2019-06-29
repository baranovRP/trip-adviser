package org.baranov.germes.app.model.entity.geography;

import org.baranov.germes.app.model.entity.transport.TransportType;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.baranov.germes.app.model.entity.geography.City.ERR_MSG_STATION_PARAM_IS_NOT_INITIALIZED;
import static org.baranov.germes.app.model.entity.geography.City.ERR_MSG_TRANSPORT_TYPE_PARAM_IS_NOT_INITIALIZED;


/**
 * Contains unit-tests to check functionality of {@link City} class
 */
public class CityTest {

    private City city;

    @Before
    public void setUp() {
        city = new City("Odessa");
    }

    @Test
    public void testAddValidStationSuccess() {
        Station station = city.addStation(TransportType.AUTO);

        assertThat(city.getStations()).contains(station);
        assertThat(station.getCity()).isEqualTo(city);
    }

    @Test
    public void testAddStationNullTransportTypeFailure() {
        Throwable throwable = catchThrowable(() -> city.addStation(null));

        assertThat(throwable).isInstanceOf(NullPointerException.class);
        assertThat(throwable).hasMessage(ERR_MSG_TRANSPORT_TYPE_PARAM_IS_NOT_INITIALIZED);
    }

    @Test
    public void testRemoveStationSuccess() {
        Station station = city.addStation(TransportType.AVIA);

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