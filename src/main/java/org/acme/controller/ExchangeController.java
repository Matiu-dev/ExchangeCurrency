package org.acme.controller;

import com.google.gson.Gson;
import com.oracle.svm.core.annotate.Delete;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.API.NBPService;
import org.acme.Entity.Currency;
import org.acme.Exception.CustomNotAuthorizedException;
import org.acme.model.InputData;
import org.acme.model.RequestResponse;
import org.acme.repository.TableRepository;
import org.acme.service.CurrencyService;
import org.acme.service.TableService;
import org.acme.validation.ExchangeService;
import org.acme.validation.JWTDecoder;

import org.eclipse.microprofile.rest.client.inject.RestClient;

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
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getCurrency(@PathParam("table") String table,
                                @PathParam("value") int value) {

        try {
            return Response.status(Response.Status.OK)
                    .entity(new TableService().findByTableAndValue(table, value))
                    .build();
        } catch (IndexOutOfBoundsException e) {
            throw new InternalServerErrorException("Wartość poza indeksem.");
        } catch (NullPointerException e) {
            throw new InternalServerErrorException("Nie ma takiej tabeli");
        }

//        try {
//                Currency currency = new TableService().findByTableAndValue(table, value);
//
//                return Response.status(Response.Status.OK)
//                        .entity(new CurrencyReponseXML(
//                                currency.getCode(),
//                                currency.getNameOfCurrency(),
//                                currency.getExchangeRate()))
//                        .header("Content-Type", "application/xml")
//                        .build();
//            } catch (IndexOutOfBoundsException e) {
//                throw new InternalServerErrorException("Wartość poza indeksem.");
//            } catch (NullPointerException e) {
//                throw new InternalServerErrorException("Nie ma takiej tabeli");
//            }


//        throw new InternalServerErrorException("Niewłaściwa nazwa formatu danych.");
    }

    //pobieranie wielu wartosci
    @GET
    @Path("/getCurrencies/table/{table}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllCurrencies(@PathParam("table") String table) {

        return Response.status(Response.Status.OK)
                .entity(new TableRepository().findByTableName(table))
                .build();
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

        new TableService().createTable(service.getExchangeRatesTable(table).get(0));

        return Response.status(Response.Status.OK)
                .entity(new RequestResponse("Zaktualizowawno dane"))
                .build();
    }

    @POST
    @Path("/postCurrencyXML/table/{table}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed({"admin"})
    public Response postCurrencyXML(@PathParam("table") String table) {//@HeaderParam("Authorization") String authorizationHeader

        new TableService().createTable(service.getExchangeRatesTable(table).get(0));

        return Response.status(Response.Status.OK)
                .entity(new RequestResponse("Zaktualizowawno dane"))
                .build();
    }

    @Delete
    @Path("/deleteCurrency/{code}")
    @Transactional
    @RolesAllowed({"admin", "default-roles-master"})
    public void deleteCurrency(@PathParam("code") String code) {

        new CurrencyService().deleteCurrency(code);
    }

    @PUT
    @Path("/putCurrency/table/{table}/code/{code}")
    @RolesAllowed({"admin", "default-roles-master"})
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response putCurrency(@PathParam("table") String table, @PathParam("code") String code) {

        new CurrencyService().updateCurrency(service.getExchangeRateForCurrency(table, code));

        return Response.status(Response.Status.OK)
                .entity(new RequestResponse("Dodano walute: " + code))
                .build();
    }
}
