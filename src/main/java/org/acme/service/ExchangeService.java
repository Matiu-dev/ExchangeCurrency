package org.acme.service;

import org.acme.model.InputData;

import java.math.BigDecimal;
import java.math.MathContext;

public class ExchangeService {

    private CurrencyService currencyService;

    public ExchangeService() {
        this.currencyService = new CurrencyService();
    }

    public String countValue(InputData inputData) throws Exception {

        new Validation().inputDataValidation(inputData);

        BigDecimal myCurrencyValue;
        BigDecimal targetCurrencyValue;

        if(inputData.getMyCurrency().equals("PLN")) {
            myCurrencyValue = new BigDecimal("1");
        } else {
            myCurrencyValue = BigDecimal.valueOf(
                    currencyService.getCurrencyByCode(inputData.getMyCurrency()));
        }

        if(inputData.getTargetCurrency().equals("PLN")) {
            targetCurrencyValue = new BigDecimal("1");
        } else {
            targetCurrencyValue = BigDecimal.valueOf(
                    currencyService.getCurrencyByCode(inputData.getTargetCurrency()));
        }

        return String.format("%.2f", new BigDecimal(inputData.getAmount())
                .multiply(myCurrencyValue)
                .divide(targetCurrencyValue, new MathContext(10))
        );
    }
}
