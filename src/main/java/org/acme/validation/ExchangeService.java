package org.acme.validation;

import org.acme.model.InputData;
import org.acme.service.CurrencyService;
import org.acme.validation.Validation;

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
                    currencyService.getCurrencyValueByCode(inputData.getMyCurrency()));
        }

        if(inputData.getTargetCurrency().equals("PLN")) {
            targetCurrencyValue = new BigDecimal("1");
        } else {
            targetCurrencyValue = BigDecimal.valueOf(
                    currencyService.getCurrencyValueByCode(inputData.getTargetCurrency()));
        }

        return String.format("%.2f", new BigDecimal(inputData.getAmount())
                .multiply(myCurrencyValue)
                .divide(targetCurrencyValue, new MathContext(10))
        );
    }
}
