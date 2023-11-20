package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.Entity.Currency;

@ApplicationScoped
public class CurrencyRepository implements PanacheRepository<Currency> {

    //metoda klasyczna
    public Currency findByCode(String code) {
        return find("code", code).firstResult();
    }

    //metoda reaktywna
//    public Uni<Currency> findByCode(String code) {
//        return find("code", code).firstResult();
//    }

}
