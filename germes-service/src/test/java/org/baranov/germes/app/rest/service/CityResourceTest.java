package org.baranov.germes.app.rest.service;

import org.baranov.germes.app.rest.service.config.JerseyConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import java.util.List;

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
        List<String> cities = target("cities").request().get(List.class);
        assertThat(cities)
                .isNotEmpty()
                .contains("Odessa")
                .contains("Kyiv");
    }
}