package org.acme.controller;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.API.KeyCloakService;
import org.acme.model.UserLogin;
import org.acme.model.UserRegister;
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

        UserLogin userLogin = new Gson().fromJson(json, UserLogin.class);
        String token = new UserService().getUserToken(userLogin.getUsername(), userLogin.getPassword(), keyCloakService);

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
