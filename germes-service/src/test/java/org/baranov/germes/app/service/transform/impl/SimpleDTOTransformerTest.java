package org.baranov.germes.app.service.transform.impl;

import org.baranov.germes.app.infra.exception.flow.InvalidParameterException;
import org.baranov.germes.app.model.entity.geography.City;
import org.baranov.germes.app.rest.dto.CityDTO;
import org.baranov.germes.app.service.transform.Transformer;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Verifies functionality of the {@link SimpleDTOTransformer}
 * unit
 */
public class SimpleDTOTransformerTest {
    private Transformer transformer;

    @Before
    public void setup() {
        transformer = new SimpleDTOTransformer();
    }

    @Test
    public void testTransformCitySuccess() {
        City city = new City("Odessa");
        city.setId(1);
        city.setRegion("Od");
        city.setDistrict("None");

        CityDTO dto = transformer.transform(city, CityDTO.class);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(city.getId());
        assertThat(dto.getName()).isEqualTo(city.getName());
        assertThat(dto.getDistrict()).isEqualTo(city.getDistrict());
        assertThat(dto.getRegion()).isEqualTo(city.getRegion());
    }

    @Test
    public void testTransformNullCityFailure() {
        Throwable throwable = catchThrowable(() -> transformer.transform(null, CityDTO.class));
        assertThat(throwable).isInstanceOf(InvalidParameterException.class);
    }

    @Test
    public void testTransformNullDTOClassFailure() {
        Throwable throwable = catchThrowable(() -> transformer.transform(new City("Odessa"), null));
        assertThat(throwable).isInstanceOf(InvalidParameterException.class);
    }

    @Test
    public void testUnTransformCitySuccess() {
        CityDTO dto = new CityDTO();
        dto.setId(1);
        dto.setRegion("Od");
        dto.setDistrict("None");
        dto.setName("Odessa");

        City city = transformer.untransform(dto, City.class);
        assertThat(city).isNotNull();
        assertThat(city.getId()).isEqualTo(dto.getId());
        assertThat(city.getName()).isEqualTo(dto.getName());
        assertThat(city.getDistrict()).isEqualTo(dto.getDistrict());
        assertThat(city.getRegion()).isEqualTo(dto.getRegion());
    }

    @Test
    public void testUnTransformNullCityFailure() {
        Throwable throwable = catchThrowable(() -> transformer.untransform(null, City.class));
        assertThat(throwable).isInstanceOf(InvalidParameterException.class);
    }

    @Test
    public void testUnTransformNullEntityClassFailure() {
        Throwable throwable = catchThrowable(() -> transformer.untransform(new CityDTO(), null));
        assertThat(throwable).isInstanceOf(InvalidParameterException.class);
    }
}