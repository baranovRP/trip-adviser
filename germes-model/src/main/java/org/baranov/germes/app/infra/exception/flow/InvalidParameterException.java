package org.baranov.germes.app.infra.exception.flow;

import org.baranov.germes.app.infra.exception.FlowException;

/**
 * Signals that incorrect parameter was passed to method/constructor
 */
public class InvalidParameterException extends FlowException {

    private static final long serialVersionUID = 4990959228756992926L;

    public InvalidParameterException(String message) {
        super(message);
    }
}