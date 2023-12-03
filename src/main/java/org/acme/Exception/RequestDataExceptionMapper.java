package org.acme.Exception;

import com.google.gson.JsonObject;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider//500
public class RequestDataExceptionMapper implements ExceptionMapper<InternalServerErrorException> {
    @Override
    public Response toResponse(InternalServerErrorException e) {
        JsonObject errorJson = new JsonObject();
        errorJson.addProperty("error", e.getMessage());

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorJson.toString())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
