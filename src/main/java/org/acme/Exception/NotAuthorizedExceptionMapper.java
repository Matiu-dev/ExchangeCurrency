package org.acme.Exception;

import com.google.gson.JsonObject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotAuthorizedExceptionMapper implements ExceptionMapper<CustomNotAuthorizedException> {

    @Override
    public Response toResponse(CustomNotAuthorizedException e) {

        JsonObject errorJson = new JsonObject();
        errorJson.addProperty("error", e.getMessage());
        errorJson.addProperty("message", e.getAdditionalMessage());

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(errorJson.toString())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
