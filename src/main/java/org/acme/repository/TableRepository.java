package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.Entity.Currency;
import org.acme.Entity.CurrencyTable;

public class TableRepository implements PanacheRepository<CurrencyTable> {

    public CurrencyTable findByTableName(String tableName) {
        return find("tableName", tableName).firstResult();
    }
}
