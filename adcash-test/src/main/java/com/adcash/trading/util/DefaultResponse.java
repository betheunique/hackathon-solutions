package com.adcash.trading.util;

/**
 * @author abhishekrai
 * @since 20/06/2017
 */
public class DefaultResponse {
    private String message;

    public DefaultResponse() {

    }

    public DefaultResponse(final  String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DefaultResponse{" +
                ", message='" + message + '\'' +
                '}';
    }
}
