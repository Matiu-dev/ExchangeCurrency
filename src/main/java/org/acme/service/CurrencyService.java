package org.acme.service;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import org.acme.API.ExchangeRateForCurrency;
import org.acme.Entity.Currency;
import org.acme.model.ExchangeRatesTable;
import org.acme.model.Rate;
import org.acme.repository.CurrencyRepository;

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

    @Transactional
    public void deleteCurrency(String code) {
        Currency currency = currencyRepository.findByCode(code);
        if(currency == null) {
            throw new NotFoundException();//przetestowac bez tego
        }

        currencyRepository.delete(currency);
    }

    @Transactional
    public void updateCurrency(ExchangeRateForCurrency exchangeRateForCurrency) {
        Currency currency = new Currency();
        currency.setCode(exchangeRateForCurrency.getCode());
        currency.setNameOfCurrency(exchangeRateForCurrency.getCurrency());
        currency.setExchangeRate(exchangeRateForCurrency.getRates().get(0).getMid());
        currencyRepository.update("exchangeRate = ?1 where code = ?2",
                currency.getExchangeRate(),
                currency.getCode());
    }

    public double getCurrencyByCode(String code) {
        Currency currency = currencyRepository.findByCode(code);

        if (currency != null) {
            return currency.getExchangeRate();
        } else {
            throw new InternalServerErrorException("Nie znaleziono waluty dla kodu: " + code);
        }
    }
}
