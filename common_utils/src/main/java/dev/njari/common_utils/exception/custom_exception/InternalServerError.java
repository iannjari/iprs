package dev.njari.common_utils.exception.custom_exception;

import dev.njari.common_utils.exception.CustomServerException;

import static io.grpc.Status.INTERNAL;

/**
 * @author njari_mathenge
 * on 25/02/2024.
 * github.com/iannjari
 */
public class InternalServerError extends CustomServerException {

    public InternalServerError(String message) {
        super(message, INTERNAL);
    }

    public InternalServerError(Throwable cause) {
        super(cause, INTERNAL);
    }


    public InternalServerError(String message, Throwable cause) {
        super(message, cause, INTERNAL);
    }

}
