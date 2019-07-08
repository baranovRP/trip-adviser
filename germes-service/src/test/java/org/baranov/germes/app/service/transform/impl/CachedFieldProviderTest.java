package org.baranov.germes.app.service.transform.impl;

import org.baranov.germes.app.infra.util.ReflectionUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ReflectionUtil.class)
public class CachedFieldProviderTest {
    private FieldProvider provider;

    @Before
    public void setup() {
        provider = new CachedFieldProvider();
    }

    @Test
    public void testGetFieldNamesSuccess() {
        List<String> fields = provider.getFieldNames(Src.class, Dest.class);
        assertThat(fields).isNotEmpty();
        assertThat(fields).contains("value");
    }

    @Test
    public void testGetFieldNamesCachedSuccess() {
        List<String> fields = provider.getFieldNames(Src.class, Dest.class);
        List<String> fields2 = provider.getFieldNames(Src.class, Dest.class);
        assertThat(fields).isNotEmpty();
        assertThat(fields).isEqualTo(fields2);
    }

    @Test
    public void testGetFieldNamesAreCached() {
        List<String> fields = provider.getFieldNames(Src.class, Dest.class);

        PowerMockito.mockStatic(ReflectionUtil.class);
        when(ReflectionUtil.findSimilarFields(Src.class, Dest.class)).thenReturn(Collections.emptyList());

        assertThat(ReflectionUtil.findSimilarFields(Src.class, Dest.class)).isEmpty();
        List<String> fields2 = provider.getFieldNames(Src.class, Dest.class);
        assertThat(fields).isNotEmpty();
        assertThat(fields).isEqualTo(fields2);
    }

}

class Src {
    int value;
}

class Dest {
    int value;
}