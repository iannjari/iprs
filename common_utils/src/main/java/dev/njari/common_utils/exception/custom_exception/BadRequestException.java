package dev.njari.common_utils.exception.custom_exception;

import dev.njari.common_utils.exception.CustomServerException;

import static io.grpc.Status.INVALID_ARGUMENT;

/**
 * @author njari_mathenge
 * on 25/02/2024.
 * github.com/iannjari
 */
public class BadRequestException extends CustomServerException {

    public BadRequestException(String message) {
        super(message, INVALID_ARGUMENT);
    }

    public BadRequestException(Throwable cause) {
        super(cause, INVALID_ARGUMENT);
    }


    public BadRequestException(String message, Throwable cause) {
        super(message, cause, INVALID_ARGUMENT);
    }

}
