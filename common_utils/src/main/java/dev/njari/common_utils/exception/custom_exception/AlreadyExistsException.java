package dev.njari.common_utils.exception.custom_exception;

import dev.njari.common_utils.exception.CustomServerException;

import static io.grpc.Status.ALREADY_EXISTS;

/**
 * @author njari_mathenge
 * on 25/02/2024.
 * github.com/iannjari
 */
public class AlreadyExistsException extends CustomServerException {



    public AlreadyExistsException(String message) {
        super(message, ALREADY_EXISTS);
    }

    public AlreadyExistsException(Throwable cause) {
        super(cause, ALREADY_EXISTS);
    }


    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause, ALREADY_EXISTS);
    }

}
