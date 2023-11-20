package org.acme.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.acme.API.KeyCloakService;
import org.acme.model.AppUserInput;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

public class UserService {

    private final static String CLIENT_ID = "quarkus-be";
    private final static String GRANT_TYPE = "password";
    private final static String CLIENT_SECRET = "Hl6dgPBMVds5nFVFsuxfzxAYgbbFmaBK";

    public String getUserToken(String username, String password, KeyCloakService keyCloakService) {
//        AppUserInput appUserInput = new Gson().fromJson(json, AppUserInput.class);

        MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
        form.add("client_id", CLIENT_ID);
        form.add("client_secret", CLIENT_SECRET);//nie znalazłem endpointa do tego
        form.add("grant_type", GRANT_TYPE);
        form.add("username", username);
        form.add("password", password);

        return getToken(keyCloakService.getToken(form).readEntity(String.class));
    }

    public String registerUser(String json, KeyCloakService keyCloakService) {

        AppUserInput appUserInput = new Gson().fromJson(json, AppUserInput.class);

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("firstName", appUserInput.getFirstName());
        jsonData.addProperty("lastName", appUserInput.getLastName());
        jsonData.addProperty("email", appUserInput.getEmail());
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
                register("Bearer " + getUserToken("admin", "admin", keyCloakService), jsonData.toString());

        return "Zarejestrowany";
    }

    private String getToken(String json) {
        try {
            return new ObjectMapper().readTree(json).get("access_token").asText();
        }catch (Exception e) {

        }
        return "error";
    }

}
