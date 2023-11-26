package org.acme.Exception;

import jakarta.ws.rs.NotAuthorizedException;

public class CustomNotAuthorizedException extends NotAuthorizedException {

    private String additionalMessage;

    public CustomNotAuthorizedException(String message, String additionalMessage) {
        super(message);
        this.additionalMessage = additionalMessage;
    }

    public String getAdditionalMessage() {
        return additionalMessage;
    }
}
