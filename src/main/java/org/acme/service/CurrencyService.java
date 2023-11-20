package org.acme.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.smallrye.common.annotation.Blocking;
import jakarta.transaction.Transactional;
import org.acme.Entity.Currency;
import org.acme.model.CurrencyCodeEnum;
import org.acme.model.ExchangeRatesTable;
import org.acme.model.Rate;
import org.acme.repository.CurrencyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CurrencyService {

    private CurrencyRepository currencyRepository;
    public CurrencyService() {
        currencyRepository = new CurrencyRepository();
    }

    @Transactional
    public void createCurrency(ExchangeRatesTable exchangeRatesTable) {

        for (Rate rate : exchangeRatesTable.getRates()) {
            Currency currency = currencyRepository.findByCode(rate.getCode());

            if(currency == null) {
                currencyRepository.persist(
                    new Currency(rate.getCode(),
                            rate.getCurrency(),
                            rate.getMid()));
            } else {
                if(rate.getMid() != currency.getExchangeRate()) {
                    currency.setExchangeRate(rate.getMid());
                    currencyRepository.persist(currency);
                }
            }
        }
    }

    public double getCurrencyByCode(String code) {
        return currencyRepository.findByCode(code).getExchangeRate();
    }

    public List<String> getAllCurrencyCodes() {
        return currencyRepository.findAll().stream().
                map(c -> c.getCode().toUpperCase()).collect(Collectors.toList());

    }
}
