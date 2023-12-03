package org.acme.Exception;

import com.google.gson.JsonObject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Scanner;

@Provider//404
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException exception) {

        JsonObject errorJson = new JsonObject();
        errorJson.addProperty("error", exception.getMessage());

        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorJson.toString())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}