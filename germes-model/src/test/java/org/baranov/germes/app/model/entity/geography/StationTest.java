package org.baranov.germes.app.model.entity.geography;

import org.baranov.germes.app.model.entity.transport.TransportType;
import org.baranov.germes.app.model.search.criteria.StationCriteria;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Verifies functionality of the {@link Station} domain entity
 */
public class StationTest {

    @Test
    public void testMatchCriteriaNotInitialized() {
        City city = new City("Odessa");
        Station station = new Station(city, TransportType.AUTO);

        Throwable throwable = catchThrowable(() -> station.match(null));
        assertThat(throwable).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void testMatchByNameSuccess() {
        City city = new City("Odessa");
        Station station = new Station(city, TransportType.AUTO);

        assertThat(station.match(StationCriteria.byName("Odessa"))).isTrue();
    }

    @Test
    public void testMatchByNameNotFound() {
        City city = new City("Odessa");
        Station station = new Station(city, TransportType.AUTO);

        assertThat(station.match(StationCriteria.byName("Kiev"))).isFalse();
    }
}