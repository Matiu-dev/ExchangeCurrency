package org.acme.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.oracle.svm.core.annotate.Delete;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.API.NBPService;
import org.acme.Entity.Currency;
import org.acme.Entity.CurrencyTable;
import org.acme.Exception.CustomNotAuthorizedException;
import org.acme.model.InputData;
import org.acme.repository.TableRepository;
import org.acme.service.CurrencyService;
import org.acme.service.TableService;
import org.acme.validation.ExchangeService;
import org.acme.validation.JWTDecoder;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.stream.Collectors;


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

    //CRUD ponizej

    //pobieranie jednej wartosci np Tabela A,B,C / wartosc 1,2,3...

    @GET
    @Path("/getCurrency/table/{table}/value/{value}")
    public Response getCurrency(@PathParam("table") String table, @PathParam("value") int value) {

        try {
            return Response.status(Response.Status.OK)
                    .entity( new TableRepository().findByTableName(table).getCurrencies().stream().collect(Collectors.toList()).get(value))
                    .build();
        } catch (IndexOutOfBoundsException e) {
            throw new InternalServerErrorException("Wartość poza indeksem.");
        } catch (NullPointerException e) {
            throw new InternalServerErrorException("Nie ma takiej tabeli");
        }

    }

    //pobieranie wielu wartosci
    @GET
    @Path("/getCurrencies/{responseType}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCurrencies(@PathParam("responseType") String responseType) {

        List<Currency> currencies = new CurrencyService().getAllCurrencies();
//        System.out.println(new Gson().toJson(currencies));

        if(responseType.equals("JSON")) {
            return Response.status(Response.Status.OK)
                    .entity(new CurrencyService().getAllCurrencies())
                    .header("Content-Type", "application/json")
                    .build();
        }

        if(responseType.equals("XML")) {
            return Response.status(Response.Status.OK)
                    .entity(new CurrencyService().getAllCurrencies())
                    .header("Content-Type", "application/xml")
                    .build();
        }

        throw new InternalServerErrorException("Niewłaściwa nazwa formatu danych.");
    }

    @POST
    @Path("/postCurrency/table/{table}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response postCurrencyJSON(@HeaderParam("Auth") String authorizationHeader,
                                         @PathParam("table") String table) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {//sprawdzanie czy jest odpowiednia rola i czy token nie wygasl
            throw new CustomNotAuthorizedException("", "Brak tokenu uwierzytelniania");
        }

        if (!new JWTDecoder().checkUpdateCurrencyValuesToken(authorizationHeader)) {//sprawdzanie czy jest odpowiednia rola i czy token nie wygasl
            throw new CustomNotAuthorizedException("", "Musisz się zalogować, aby skorzystać z tej opcji");
        }

//        new CurrencyService().createCurrency(service.getExchangeRatesTable(table).get(0));
        new TableService().createTable(service.getExchangeRatesTable(table).get(0));

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("message", "Zaktualizowawno dane");

        return Response.status(Response.Status.OK).entity(jsonData).build();
    }

    @POST
    @Path("/postCurrencyXML/table/{table}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed({"admin"})
    public Response postCurrencyXML(@PathParam("table") String table) {//@HeaderParam("Authorization") String authorizationHeader

        new TableService().createTable(service.getExchangeRatesTable(table).get(0));

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("message", "Zaktualizowawno dane");

        return Response.status(Response.Status.OK).entity(jsonData).build();
    }

    @Delete
    @Path("/deleteCurrency/{code}")
    @Transactional
    @RolesAllowed({"admin", "default-roles-master"})
    public void deleteCurrency(@PathParam("code") String code) {

        new CurrencyService().deleteCurrency(code);
    }

    @PUT//poprawic tego puta jeszcze
    @Path("/putCurrency/table/{table}/code/{code}")
    @RolesAllowed({"admin", "default-roles-master"})
    @Transactional
    public Response putCurrency(@PathParam("table") String table, @PathParam("code") String code) {

        new CurrencyService().updateCurrency(service.getExchangeRateForCurrency(table, code));

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("message", "Dodano walute: " + code);

        return Response.status(Response.Status.OK).entity(jsonData).build();
    }
}
