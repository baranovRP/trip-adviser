package org.baranov.germes.app.service.transform.impl;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import org.baranov.germes.app.infra.util.ReflectionUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class CachedFieldProviderTestWithJMockit {
    private FieldProvider provider;

    @Before
    public void setup() {
        provider = new CachedFieldProvider();
    }

    @Test
    @Ignore
    public void testGetFieldNamesAreCachedUsingJMockit() {
        new Expectations() {
            {
                ReflectionUtil.findSimilarFields(Src.class, Dest.class);
                result = Collections.singletonList("name");
            }
        };

        List<String> fields = provider.getFieldNames(Src.class, Dest.class);

        new Expectations() {
            {
                ReflectionUtil.findSimilarFields(Src.class, Dest.class);
                result = Collections.emptyList();
            }
        };

        assertThat(ReflectionUtil.findSimilarFields(Src.class, Dest.class)).isEmpty();
        List<String> fields2 = provider.getFieldNames(Src.class, Dest.class);
        assertThat(fields).isNotEmpty();
        assertThat(fields).isEqualTo(fields2);
    }

    @Test
    public void testGetFieldNamesAreCachedUsingMockupsAPI() {
        new MockReflectionUtil();

        MockReflectionUtil.fields = Collections.singletonList("name");

        List<String> fields = provider.getFieldNames(Src.class, Dest.class);

        MockReflectionUtil.fields = Collections.emptyList();

        assertThat(ReflectionUtil.findSimilarFields(Src.class, Dest.class)).isEmpty();
        List<String> fields2 = provider.getFieldNames(Src.class, Dest.class);
        assertThat(fields).isNotEmpty();
        assertThat(fields).isEqualTo(fields2);
    }

    public static final class MockReflectionUtil extends MockUp<ReflectionUtil> {
        protected static List<String> fields;

        @Mock
        public static List<String> findSimilarFields(Class<?> clz1, Class<?> clz2) {
            return fields;
        }
    }
}
