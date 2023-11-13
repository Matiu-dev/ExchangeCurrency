package org.acme.Exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class MoneyExchangeExceptionHandler implements ExceptionMapper<MoneyExchangeException> {

    @Override
    public Response toResponse(MoneyExchangeException exception) {
        return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }
}
