package org.acme.controller;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/")
public class CurrencyResource {

    @CheckedTemplate
    public static class ExchangeValueTemplate {

        public static native TemplateInstance exchangeCurrency();
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
    public TemplateInstance exchangeCurrency() {
        return ExchangeValueTemplate.exchangeCurrency();
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
