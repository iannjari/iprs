package dev.njari.common_utils.exception;

import io.grpc.Status;

/**
 * @author njari_mathenge
 * on 25/02/2024.
 * github.com/iannjari
 */
public class CustomServerException extends RuntimeException {

    public CustomServerException(String message, Status grpcExceptionstatus) {
        super(message);
        this.grpcExceptionstatus = grpcExceptionstatus;
    }

    public CustomServerException(String message, Throwable cause, Status grpcExceptionstatus) {
        super(message, cause);
        this.grpcExceptionstatus = grpcExceptionstatus;
    }
    public CustomServerException(Throwable cause, Status grpcExceptionstatus) {
        super(cause);
        this.grpcExceptionstatus = grpcExceptionstatus;
    }

    private final Status grpcExceptionstatus;

    public Status getGrpcExceptionstatus() {
        return grpcExceptionstatus;
    }
}
