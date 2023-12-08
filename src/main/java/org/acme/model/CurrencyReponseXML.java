package org.acme.model;


import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CurrencyReponseXML {

    private String code;
    private String nameOfCurrency;
    private double exchangeRate;

    public CurrencyReponseXML() {
    }

    public CurrencyReponseXML(String code, String nameOfCurrency, double exchangeRate) {
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
}
