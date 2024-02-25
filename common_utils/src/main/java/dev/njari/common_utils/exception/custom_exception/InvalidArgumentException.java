package dev.njari.common_utils.exception.custom_exception;

import dev.njari.common_utils.exception.CustomServerException;

import static io.grpc.Status.INVALID_ARGUMENT;

/**
 * @author njari_mathenge
 * on 25/02/2024.
 * github.com/iannjari
 */
public class InvalidArgumentException extends CustomServerException {

    public InvalidArgumentException(String message) {
        super(message, INVALID_ARGUMENT);
    }

    public InvalidArgumentException(Throwable cause) {
        super(cause, INVALID_ARGUMENT);
    }


    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause, INVALID_ARGUMENT);
    }

}
