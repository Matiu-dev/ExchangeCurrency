package org.acme.model;

public class RequestResponse {
    private String message;

    public RequestResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
