package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.Entity.Currency;

@ApplicationScoped
public class CurrencyRepository implements PanacheRepository<Currency> {

    public Currency findByCode(String code) {
        return find("code", code).firstResult();
    }

}
