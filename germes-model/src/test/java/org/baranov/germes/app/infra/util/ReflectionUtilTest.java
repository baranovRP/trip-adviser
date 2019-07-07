package org.baranov.germes.app.infra.util;

import org.baranov.germes.app.infra.exception.ConfigurationException;
import org.baranov.germes.app.infra.exception.flow.InvalidParameterException;
import org.baranov.germes.app.infra.util.annotaion.Ignore;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Verifies functionality of the {@link ReflectionUtil} unit
 */
public class ReflectionUtilTest {

    @Test
    public void createInstanceSuccess() {
        Object value = ReflectionUtil.createInstance(Source.class);
        assertThat(value).isNotNull();
    }

    @Test
    public void createInstanceFailure() {
        Throwable throwable =
                catchThrowable(() -> ReflectionUtil.createInstance(Restricted.class));
        assertThat(throwable).isInstanceOf(ConfigurationException.class);
    }

    @Test
    public void findSimilarFieldsSuccess() {
        List<String> fields = ReflectionUtil.findSimilarFields(Source.class,
                Destination.class);
        assertThat(fields).isNotEmpty();
        assertThat(fields).contains("value");
    }

    @Test
    public void copyFieldsSuccess() {
        Source src = new Source();
        src.setValue(10);
        Destination dest = new Destination();
        List<String> fields = ReflectionUtil.findSimilarFields(src.getClass(), dest.getClass());

        ReflectionUtil.copyFields(src, dest, fields);
        assertThat(dest.getValue()).isEqualTo(10);
    }

    @Test
    public void copyFindSimilarFieldsWithIgnoreSuccess() {
        List<String> fields = ReflectionUtil.findSimilarFields(Source.class, Destination.class);
        assertThat(fields).doesNotContain("ignored");
    }

    @Test
    public void copyFindSimilarFieldsForStaticAndFinalSuccess() {
        List<String> fields = ReflectionUtil.findSimilarFields(Source.class, Destination.class);
        assertThat(fields).doesNotContain("staticField");
        assertThat(fields).doesNotContain("finalField");
    }

    @Test
    public void copyFindSimilarFieldsForBaseFieldSuccess() {
        List<String> fields = ReflectionUtil.findSimilarFields(Source.class, Destination.class);
        assertThat(fields).contains("baseField");
    }

    @Test
    public void copyFieldsDestinationNullFailure() {
        Source src = new Source();
        Throwable throwable = catchThrowable(() ->
                ReflectionUtil.copyFields(src, null, Collections.emptyList()));
        assertThat(throwable).isInstanceOf(InvalidParameterException.class);
    }
}

class BaseSource {
    private int baseField;
}

class BaseDestination {
    private int baseField;
}

class Source extends BaseSource {
    private static int staticField;
    private final int finalField = 0;
    private int value;
    private String text;
    @Ignore
    private int ignored = 2;

    public void setValue(int value) {
        this.value = value;
    }
}

class Destination extends BaseDestination {
    private int value;
    private int ignored;
    private int staticField;
    private int finalField = 0;

    public int getValue() {
        return value;
    }
}

class Restricted {
    public Restricted(int value) {
    }
}