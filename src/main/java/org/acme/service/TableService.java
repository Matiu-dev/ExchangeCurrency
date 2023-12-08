package org.acme.service;

import jakarta.transaction.Transactional;
import org.acme.API.Rates;
import org.acme.Entity.Currency;
import org.acme.Entity.CurrencyTable;
import org.acme.model.ExchangeRatesTable;
import org.acme.model.Rate;
import org.acme.repository.CurrencyRepository;
import org.acme.repository.TableRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TableService {

    private TableRepository tableRepository;

    private CurrencyService currencyService;

    private CurrencyRepository currencyRepository;

    public TableService() {
        this.tableRepository =  new TableRepository();
        this.currencyService = new CurrencyService();
        this.currencyRepository = new CurrencyRepository();
    }

    @Transactional
    public void createTable(ExchangeRatesTable exchangeRatesTable) {
        String tableName = exchangeRatesTable.getTable();
        CurrencyTable existingTable = tableRepository.findByTableName(tableName);

        if (existingTable == null) {
            // Tworzenie nowej tabeli, jeśli nie istnieje
            CurrencyTable newCurrencyTable = new CurrencyTable();
            newCurrencyTable.setTableName(tableName);
            newCurrencyTable.setNo(exchangeRatesTable.getNo());
            newCurrencyTable.setEffectiveDate(exchangeRatesTable.getEffectiveDate());

            Set<Currency> currencies = new HashSet<>();
            for (Rate rate : exchangeRatesTable.getRates()) {
                Currency existingCurrency = currencyRepository.findByCode(rate.getCode());
                if (existingCurrency == null) {
                    Currency currency = new Currency(rate.getCode(), rate.getCurrency(), rate.getMid(), newCurrencyTable);
                    currencies.add(currency);
                }
            }
            newCurrencyTable.setCurrencies(currencies);
            tableRepository.persist(newCurrencyTable);
        } else {
            // Aktualizacja istniejącej tabeli
            Set<Currency> existingCurrencies = existingTable.getCurrencies();
            for (Rate rate : exchangeRatesTable.getRates()) {
                Currency existingCurrency = currencyRepository.findByCode(rate.getCode());
                if (existingCurrency == null) {
                    Currency currency = new Currency(rate.getCode(), rate.getCurrency(), rate.getMid(), existingTable);
                    existingCurrencies.add(currency);
                }
            }
            existingTable.setCurrencies(existingCurrencies);
            tableRepository.persist(existingTable);
        }
    }

    public Currency findByTableAndValue(String table, int value) {
        return new TableRepository().findByTableName(table)
                .getCurrencies()
                .stream()
                .sorted(Comparator.comparing(Currency::getCode))
                .collect(Collectors.toList()).get(value);
    }
}
