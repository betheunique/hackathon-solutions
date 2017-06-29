package com.verto.analytics.util;

/**
 * @author abhishekrai
 * @since 24/06/2017
 */
public class DefaultResponse {
    private Integer status;
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
