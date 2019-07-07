package org.baranov.germes.app.service.transform.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CachedFieldProviderTest {
    private FieldProvider provider;

    @Before
    public void setup() {
        provider = new CachedFieldProvider();
    }

    @Test
    public void testGetFieldNamesSuccess() {
        List<String> fields = provider.getFieldNames(Source.class, Destination.class);
        assertThat(fields).isNotEmpty();
        assertThat(fields).contains("value");
    }

    @Test
    public void testGetFieldNamesCachedSuccess() {
        List<String> fields = provider.getFieldNames(Source.class, Destination.class);
        List<String> fields2 = provider.getFieldNames(Source.class, Destination.class);
        assertThat(fields).isNotEmpty();
        assertThat(fields).isEqualTo(fields2);
    }
}

class Source {
    int value;
}

class Destination {
    int value;
}