package org.acme.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.oracle.svm.core.annotate.Delete;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Status;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.API.NBPService;
import org.acme.Exception.CustomNotAuthorizedException;
import org.acme.model.InputData;
import org.acme.service.CurrencyService;
import org.acme.service.ExchangeService;
import org.acme.service.JWTDecoder;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.hibernate.annotations.Parameter;
import org.jboss.resteasy.reactive.RestHeader;

@Path("/currency")
public class ExchangeController {

    @RestClient
    private NBPService service;

    @GET
    @Path("/currencyExchangeValue")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response countCurrencyValue(String json) throws Exception {

        return Response.status(Response.Status.OK).entity(new ExchangeService().
                countValue(new Gson().fromJson(json, InputData.class))).build();
    }

    @POST
    @Path("/getCurrencyInfo/table/{table}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateCurrencyValues(@HeaderParam("Auth") String authorizationHeader,
                                         @PathParam("table") String table) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {//sprawdzanie czy jest odpowiednia rola i czy token nie wygasl
            throw new CustomNotAuthorizedException("", "Brak tokenu uwierzytelniania");
        }

        if (!new JWTDecoder().checkUpdateCurrencyValuesToken(authorizationHeader)) {//sprawdzanie czy jest odpowiednia rola i czy token nie wygasl
            throw new CustomNotAuthorizedException("", "Musisz się zalogować, aby skorzystać z tej opcji");
        }

        new CurrencyService().createCurrency(service.getExchangeRatesTable(table).get(0));

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("message", "Zaktualizowawno dane");

        return Response.status(Response.Status.OK).entity(jsonData).build();
    }
//forbidden
    @POST
    @Path("/getCurrencyInfoXML/table/{table}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed({"admin"})
    public Response updateCurrencyValuesByCSV(@PathParam("table") String table) {//@HeaderParam("Authorization") String authorizationHeader

        new CurrencyService().createCurrency(service.getExchangeRatesTable(table).get(0));

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("message", "Zaktualizowawno dane");

        return Response.status(Response.Status.OK).entity(jsonData).build();
    }

    @Delete
    @Path("/deleteCurrency/{code}")
    @Transactional
    @RolesAllowed({"admin", "default-roles-master"})
    public Response deleteCurrency(@PathParam("code") String code) {

        new CurrencyService().deleteCurrency(code);

        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/updateCurrency/{code}")
    @RolesAllowed({"admin", "default-roles-master"})
    @Transactional
    public Response updateCurrency(@PathParam("code") String code) {

        new CurrencyService().updateCurrency(service.getExchangeRateForCurrency("A", code));

        return Response.status(Response.Status.OK).build();
    }
}
