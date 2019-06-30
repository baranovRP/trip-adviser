package org.baranov.germes.app.infra.util;

import org.baranov.germes.app.infra.exception.flow.InvalidParameterException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Verifies functionality of {@link Checks} class
 */
public class ChecksTest {

    static final String MSG = "test";

    @Test
    public void testCheckParameterGetException() {
        Throwable throwable = catchThrowable(() -> Checks.checkParameter(false, MSG));
        assertThat(throwable).isInstanceOf(InvalidParameterException.class);
        assertThat(throwable).hasMessage("test");
    }

    @Test
    public void testCheckParameterNoException() {
        Throwable throwable = catchThrowable(() -> Checks.checkParameter(true, MSG));
        assertThat(throwable).isNull();
    }
}