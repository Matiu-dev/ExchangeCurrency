package org.acme.controller;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.API.KeyCloakService;
import org.acme.model.AppUserInput;
import org.acme.service.UserService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/")
public class UserController {

    @RestClient
    private KeyCloakService keyCloakService;

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String login(String json) {

        AppUserInput appUserInput = new Gson().fromJson(json, AppUserInput.class);
        String token = new UserService().getUserToken(appUserInput.getUsername(), appUserInput.getPassword(), keyCloakService);

        return token;
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String register(String json) {

        new UserService().registerUser(json, keyCloakService);

        return "Zarejestrowany";
    }
}
