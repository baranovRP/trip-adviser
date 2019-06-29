package org.baranov.germes.app.model.entity.geography;

import org.baranov.germes.app.infra.util.CommonUtil;
import org.baranov.germes.app.model.entity.base.AbstractEntity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Any locality that contains transport stations
 */
public class City extends AbstractEntity {

    static final String ERR_MSG_STATION_PARAM_IS_NOT_INITIALIZED = "station parameter is not initialized";
    private String name;

    /**
     * Name of the district where city is placed
     */
    private String district;

    /**
     * Name of the region where district is located.
     * Region is top-level area in the country
     */
    private String region;

    /**
     * Set of transport stations that is linked to this
     * loyality
     */
    private Set<Station> stations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Set<Station> getStations() {
        return CommonUtil.getSafeSet(stations);
    }

    public void setStations(Set<Station> stations) {
        this.stations = stations;
    }

    /**
     * Adds specified station to the city station list
     *
     * @param station
     */
    public void addStation(final Station station) {
        Objects.requireNonNull(station, ERR_MSG_STATION_PARAM_IS_NOT_INITIALIZED);
        if (stations == null) {
            stations = new HashSet<>();
        }
        stations.add(station);
        station.setCity(this);
    }

    /**
     * Removes specified station from city station list
     *
     * @param station
     */
    public void removeStation(final Station station) {
        Objects.requireNonNull(station, ERR_MSG_STATION_PARAM_IS_NOT_INITIALIZED);
        if (stations == null) {
            return;
        }
        stations.remove(station);
    }
}
