package org.acme.model;

public class InputData {
    private String amount;
    private String myCurrency;
    private String targetCurrency;

    public InputData(String amount, String myCurrency, String targetCurrency) {
        this.amount = amount;
        this.myCurrency = myCurrency;
        this.targetCurrency = targetCurrency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMyCurrency() {
        return myCurrency;
    }

    public void setMyCurrency(String myCurrency) {
        this.myCurrency = myCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    @Override
    public String toString() {
        return "InputData{" +
                "amount='" + amount + '\'' +
                ", myCurrency='" + myCurrency + '\'' +
                ", targetCurrency='" + targetCurrency + '\'' +
                '}';
    }
}
