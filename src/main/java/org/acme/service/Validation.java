package org.acme.service;

import org.acme.Exception.MoneyExchangeException;
import org.acme.model.CurrencyCodeEnum;
import org.acme.model.InputData;

public class Validation {
    public void inputDataValidation(InputData inputData) throws MoneyExchangeException {

        if (inputData.getAmount() == null || inputData.getAmount().isEmpty()) {
            throw new MoneyExchangeException("Amount is null.");
        }

        if (inputData.getMyCurrency() == null || inputData.getMyCurrency().isEmpty()) {
            throw new MoneyExchangeException("MyCurrency is null.");
        }

        if (inputData.getTargetCurrency() == null || inputData.getTargetCurrency().isEmpty()) {
            throw new MoneyExchangeException("TargetCurrency is null.");
        }

        //sprawdzenie czy nazwy kodowe walut sa prawdziwe
        if(!checkCurrencyIsPresent(inputData.getMyCurrency())) {
            throw new MoneyExchangeException("Invalid myCurrency value.");
        }

        if(!checkCurrencyIsPresent(inputData.getTargetCurrency())) {
            throw new MoneyExchangeException("Invalid targetCurrency value.");
        }
    }

    public boolean checkCurrencyIsPresent(String currency) {
        for(CurrencyCodeEnum currencyCodeEnum: CurrencyCodeEnum.values()){
            if(currencyCodeEnum.toString().equals(currency)) {
                return true;
            }
        }

        return currency.equals("PLN");

    }
}
