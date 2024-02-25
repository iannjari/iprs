package dev.njari.common_utils.exception.custom_exception;

import dev.njari.common_utils.exception.CustomServerException;

import static io.grpc.Status.NOT_FOUND;

/**
 * @author njari_mathenge
 * on 25/02/2024.
 * github.com/iannjari
 */
public class NotFoundException extends CustomServerException {

    public NotFoundException(String message) {
        super(message, NOT_FOUND);
    }

    public NotFoundException(Throwable cause) {
        super(cause, NOT_FOUND);
    }


    public NotFoundException(String message, Throwable cause) {
        super(message, cause, NOT_FOUND);
    }

}
