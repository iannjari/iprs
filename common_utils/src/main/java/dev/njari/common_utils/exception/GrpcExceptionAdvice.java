package dev.njari.common_utils.exception;

import io.grpc.StatusException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

import static io.grpc.Status.INTERNAL;

/**
 * @author njari_mathenge
 * on 25/02/2024.
 * github.com/iannjari
 */

@GrpcAdvice
public class GrpcExceptionAdvice {

    @GrpcExceptionHandler
    public StatusException handleCustomException(CustomServerException exception) {
        return exception.getGrpcExceptionstatus()
                .withCause(exception.getCause())
                .withDescription(exception.getMessage())
                .asException();
    }

    @GrpcExceptionHandler
    public StatusException handleGenericException(Exception exception) {
        return  INTERNAL
                .withCause(exception.getCause())
                .withDescription(exception.getMessage())
                .asException();
    }

}
