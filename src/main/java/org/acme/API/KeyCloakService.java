package org.acme.API;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.logging.annotations.Pos;

@RegisterRestClient(baseUri = "http://localhost:8180/")
public interface KeyCloakService {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("realms/master/protocol/openid-connect/token")
    Response getToken(MultivaluedMap<String, String> formData);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("admin/realms/master/users")
    String register(@HeaderParam("Authorization") String authorizationHeader, String json);
}
