package org.baranov.germes.app.rest.service;

import org.baranov.germes.app.rest.dto.CityDTO;
import org.baranov.germes.app.rest.service.config.JerseyConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link CityResourceTest} is integration test that verifies
 * {@link CityResource}
 */
public class CityResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new JerseyConfig();
    }

    @Test
    public void testFindCitiesSuccess() {
        List<Map<String, String>> cities = target("cities").request().get(List.class);
        assertThat(cities).isNotNull().hasSize(1);

        Map<String, String> city = cities.get(0);
        assertThat(city.get("name")).isEqualTo("Odessa");
    }

    @Test
    public void testFindCityByIdSuccess() {
        CityDTO city = target("cities/1").request().get(CityDTO.class);
        assertThat(city).isNotNull();
        assertThat(city.getId()).isEqualTo(1);
        assertThat(city.getName()).isEqualTo("Odessa");
    }

    @Test
    public void testFindCityByIdNotFound() {
        Response response = target("cities/2").request().get(Response.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatus())
                .isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void testFindCityByIdInvalidId() {
        Response response = target("cities/aaab").request().get(Response.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatus())
                .isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void testSaveCitySuccess() {
        CityDTO city = new CityDTO();
        city.setName("Kiev");

        Response response = target("cities").request()
                .post(Entity.entity(city, MediaType.APPLICATION_JSON));
        assertThat(response.getStatus())
                .isEqualTo(Response.Status.NO_CONTENT.getStatusCode());
    }
}