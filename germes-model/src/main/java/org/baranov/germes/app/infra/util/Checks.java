package org.baranov.germes.app.infra.util;

import org.baranov.germes.app.infra.exception.flow.InvalidParameterException;

/**
 * Contains common check routines
 */
public final class Checks {

    private Checks() {
    }

    /**
     * Verifies that specified check passed and throws exception otherwise
     *
     * @param check
     * @param message
     * @throws InvalidParameterException
     */
    public static void checkParameter(boolean check, String message)
            throws InvalidParameterException {
        if (!check) {
            throw new InvalidParameterException(message);
        }
    }
}