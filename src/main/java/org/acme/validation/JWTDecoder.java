package org.acme.validation;

import jakarta.json.*;
import jakarta.ws.rs.ForbiddenException;
import org.acme.Exception.CustomNotAuthorizedException;
import org.acme.Exception.ForbiddenExceptionMapper;
import org.acme.Exception.NotAuthorizedExceptionMapper;

import java.io.StringReader;
import java.time.Instant;

public class JWTDecoder {


    public boolean checkUpdateCurrencyValuesToken(String jwtToken) {

        String[] jwtParts = jwtToken.split("\\."); // Podziel token na części
        String encodedPayload = jwtParts[1]; // Zawartość znajduje się w drugiej części tokena

        byte[] decodedBytes = java.util.Base64.getUrlDecoder().decode(encodedPayload);
        String decodedPayload = new String(decodedBytes);

        JsonReader jsonReader = Json.createReader(new StringReader(decodedPayload));
        JsonObject jsonObject = jsonReader.readObject();

        // Pobranie wartości realm_access.roles
        JsonObject realmAccess = jsonObject.getJsonObject("realm_access");
        JsonArray rolesArray = realmAccess.getJsonArray("roles");

        boolean isAdmin = false;
        for(JsonValue a: rolesArray) {
            if (a.getValueType() == JsonValue.ValueType.STRING) {
                if (((JsonString) a).getString().equals("admin")) {
                    isAdmin = true;
                }
            }
        }

        // Pobranie daty wygaśnięcia tokenu (exp)
        long expirationTime = jsonObject.getInt("exp");

        if(expirationTime < Instant.now().getEpochSecond()) {
            throw new CustomNotAuthorizedException("", "Musisz się zalogować, aby skorzystać z tej opcji");
        }

        if(!isAdmin) {
            throw new ForbiddenException("Nie masz odpowiednich uprawnień, aby skorzystać z tej opcji");
        }

        return true;
    }
}
