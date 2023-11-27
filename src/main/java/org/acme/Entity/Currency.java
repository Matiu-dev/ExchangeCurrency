package org.acme.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;


@Entity
public class Currency {
    @Id
    private String code;
    private String nameOfCurrency;
    private double exchangeRate;

    @ManyToOne
    @JoinColumn
    private CurrencyTable currencyTable;

    public Currency(String code, String nameOfCurrency, double exchangeRate, CurrencyTable currencyTable) {
        this.code = code;
        this.nameOfCurrency = nameOfCurrency;
        this.exchangeRate = exchangeRate;
        this.currencyTable = currencyTable;
    }

    public Currency() {

    }

    public Currency(String code, String nameOfCurrency, double exchangeRate) {
        this.code = code;
        this.nameOfCurrency = nameOfCurrency;
        this.exchangeRate = exchangeRate;
    }

    public CurrencyTable getCurrencyTable() {
        return currencyTable;
    }

    public void setCurrencyTable(CurrencyTable currencyTable) {
        this.currencyTable = currencyTable;
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
