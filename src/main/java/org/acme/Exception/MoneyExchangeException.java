package org.acme.Exception;

import java.io.Serializable;

public class MoneyExchangeException extends Exception implements Serializable {

    private static final long serialVersionUID = 1L;

    public MoneyExchangeException() {
        super();
    }
    public MoneyExchangeException(String msg) {
        super(msg);
    }
    public MoneyExchangeException(String msg, Exception e)  {
        super(msg, e);
    }
}
