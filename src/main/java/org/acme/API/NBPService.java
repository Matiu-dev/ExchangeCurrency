package org.acme.API;

import jakarta.ws.rs.PathParam;
import org.acme.model.ExchangeRatesTable;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;


import java.util.List;

@Path("/api/exchangerates/tables")
@RegisterRestClient(baseUri = "http://api.nbp.pl")
public interface NBPService {

    @GET
    @Path("/{table}")
    List<ExchangeRatesTable> getExchangeRatesTable(@PathParam("table") String table);
}
