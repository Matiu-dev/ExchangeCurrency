package org.acme.service;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import org.acme.API.ExchangeRateForCurrency;
import org.acme.Entity.Currency;
import org.acme.repository.CurrencyRepository;
import org.acme.repository.TableRepository;

import java.util.List;

public class CurrencyService {

    private CurrencyRepository currencyRepository;
    private TableRepository tableRepository;
    public CurrencyService() {
        tableRepository = new TableRepository();
        currencyRepository = new CurrencyRepository();
    }

    @Transactional
    public void deleteCurrency(String code) {
        Currency currency = currencyRepository.findByCode(code);
        if(currency != null) {
            currencyRepository.delete(currency);
        } else {
            throw new NotFoundException("Nie znaleziono waluty dla kodu: " + code);
        }

    }

    @Transactional
    public void updateCurrency(ExchangeRateForCurrency exchangeRateForCurrency) {
        Currency currency = new Currency();
        currency.setCode(exchangeRateForCurrency.getCode());
        currency.setNameOfCurrency(exchangeRateForCurrency.getCurrency());
        currency.setExchangeRate(exchangeRateForCurrency.getRates().get(0).getMid());
//        currency.setCurrencyTable();//find by currency table

        Currency nCurrency = currencyRepository.findByCode(currency.getCode());



        if (nCurrency != null) {
            currencyRepository.update("exchangeRate = ?1 where code = ?2",
                    currency.getExchangeRate(),
                    currency.getCode());
        } else {
            currencyRepository.persist(currency);
        }
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepository.listAll();
    }

    public Currency getCurrency(String table, String value) {
        return new Currency();
    }

    public Currency getCurrencyByCode(String code) {
        Currency currency = currencyRepository.findByCode(code);

        if (currency != null) {
            return currency;
        } else {
            throw new InternalServerErrorException("Nie znaleziono waluty dla kodu: " + code);
        }
    }

    public double getCurrencyValueByCode(String code) {
        Currency currency = currencyRepository.findByCode(code);

        if (currency != null) {
            return currency.getExchangeRate();
        } else {
            throw new InternalServerErrorException("Nie znaleziono waluty dla kodu: " + code);
        }
    }
}
