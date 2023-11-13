package org.acme.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Currency {
    @Id
    private String code;
    private String nameOfCurrency;
    private double exchangeRate;

    public Currency() {

    }

    public Currency(String code, String nameOfCurrency, double exchangeRate) {
        this.code = code;
        this.nameOfCurrency = nameOfCurrency;
        this.exchangeRate = exchangeRate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameOfCurrency() {
        return nameOfCurrency;
    }

    public void setNameOfCurrency(String nameOfCurrency) {
        this.nameOfCurrency = nameOfCurrency;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "code='" + code + '\'' +
                ", nameOfCurrency='" + nameOfCurrency + '\'' +
                ", exchangeRate=" + exchangeRate +
                '}';
    }
}
