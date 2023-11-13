package org.acme.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.acme.API.KeyCloakService;
import org.acme.model.AppUserInput;
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

        MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
        form.add("client_id", "quarkus-be");
        form.add("client_secret", "SI42wNkzhwJCqmB0T3i0ibGrjpYYhuS8");//jeszcze api do tego
        form.add("grant_type", "password");
        form.add("username", appUserInput.getUsername());
        form.add("password", appUserInput.getPassword());

        return getToken(keyCloakService.getToken(form).readEntity(String.class));
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String register(String json) {

        AppUserInput appUserInput = new Gson().fromJson(json, AppUserInput.class);

        MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
        form.add("client_id", "quarkus-be");
        form.add("client_secret", "SI42wNkzhwJCqmB0T3i0ibGrjpYYhuS8");//jeszcze api do tego
        form.add("grant_type", "password");
        form.add("username", "admin");
        form.add("password", "admin");

        String authorizationToken = getToken(keyCloakService.getToken(form).readEntity(String.class));
        System.out.println(authorizationToken);

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("firstName", "mat");
        jsonData.addProperty("lastName", "wro");
        jsonData.addProperty("email", "mat@wro.com");
        jsonData.addProperty("enabled", true);
        jsonData.addProperty("username", appUserInput.getUsername());

        JsonArray credentialsArray = new JsonArray();
        JsonObject credentialsObject = new JsonObject();
        credentialsObject.addProperty("type", "password");
        credentialsObject.addProperty("value", appUserInput.getPassword());
        credentialsObject.addProperty("temporary", false);
        credentialsArray.add(credentialsObject);

        jsonData.add("credentials", credentialsArray);

        JsonObject clientRolesObject = new JsonObject();
        JsonArray clientRolesArray = new JsonArray();
        clientRolesArray.add("uma_authorization");
        clientRolesObject.add("client_id", clientRolesArray);

        jsonData.add("clientRoles", clientRolesObject);

        keyCloakService.
                register("Bearer " + authorizationToken, jsonData.toString());

        return "Zarejestrowany";
    }

    public String getToken(String json) {
        try {
            return new ObjectMapper().readTree(json).get("access_token").asText();
        }catch (Exception e) {

        }
        return "error";
    }
}
