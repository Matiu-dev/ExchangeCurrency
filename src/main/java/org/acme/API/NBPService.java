package org.acme.API;

import jakarta.ws.rs.PathParam;
import org.acme.model.ExchangeRatesTable;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;


import java.util.List;

@Path("/api/exchangerates")
@RegisterRestClient(baseUri = "http://api.nbp.pl")
public interface NBPService {

    @GET
    @Path("/tables/{table}")
    List<ExchangeRatesTable> getExchangeRatesTable(@PathParam("table") String table);

    @GET
    @Path("/rates/{table}/{code}")
    ExchangeRateForCurrency getExchangeRateForCurrency(@PathParam("table") String table,
                                                       @PathParam("code") String code);

}
