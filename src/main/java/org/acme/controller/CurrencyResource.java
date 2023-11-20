package org.acme.controller;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.Entity.Currency;
import org.acme.repository.CurrencyRepository;
import org.acme.service.CurrencyService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@Path("/")
public class CurrencyResource {

    @CheckedTemplate
    public static class ExchangeValueTemplate {
        public static native TemplateInstance exchangeCurrency(List<String> codes);

        public static native TemplateInstance login();
        public static native TemplateInstance register();
        public static native TemplateInstance index();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("index")
    public TemplateInstance index() {
        return ExchangeValueTemplate.index();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("exchangeCurrency")
    @Blocking
    public TemplateInstance exchangeCurrency$() {
        List<String> currencyCodes = new CurrencyService().getAllCurrencyCodes();

        return ExchangeValueTemplate.exchangeCurrency(currencyCodes);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("login")
    public TemplateInstance login() {
        return ExchangeValueTemplate.login();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("register")
    public TemplateInstance register() {
        return ExchangeValueTemplate.register();
    }
}
