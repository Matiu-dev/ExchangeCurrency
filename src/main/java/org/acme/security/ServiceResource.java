package org.acme.security;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/authorization")
public class ServiceResource {

    @Inject
    JsonWebToken jwt;

    @GET
    @Produces("text/plain")
    @RolesAllowed("admin")
    public Uni<String> getName() {
        return Uni.createFrom().item("Hello," + jwt.getName());
    }
}
