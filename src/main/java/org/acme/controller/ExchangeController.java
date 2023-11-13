package org.acme.controller;

import com.google.gson.Gson;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.API.NBPService;
import org.acme.model.InputData;
import org.acme.service.CurrencyService;
import org.acme.service.ExchangeService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/currency")
public class ExchangeController {

    @RestClient
    private NBPService service;

    private CurrencyService currencyService;

    @POST
    @Path("/currencyExchangeValue")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin", "default-roles-master"})
    public String countCurrencyValue(String json) throws Exception {

        return new ExchangeService().
                countValue(new Gson().fromJson(json, InputData.class));
    }

    @POST
    @Path("/getCurrencyInfo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed({"admin"})
    public String updateCurrencyValues() {

        currencyService = new CurrencyService();
        currencyService.createCurrency(service.getExchangeRatesTable("A").get(0));

        return "OK";
    }

}

