package org.acme.Exception;

import com.google.gson.JsonObject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider//403
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {
    @Override
    public Response toResponse(ForbiddenException e) {
        JsonObject errorJson = new JsonObject();
        errorJson.addProperty("error", e.getMessage());

        return Response.status(Response.Status.FORBIDDEN)
                .entity(errorJson.toString())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
