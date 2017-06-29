package com.verto.analytics.dto;

/**
 * @author abhishekrai
 * @since 26/06/2017
 */
public class StringSetRequest {

    private String stringSet;

    public String getStringSet() {
        return stringSet;
    }

    public void setStringSet(String stringSet) {
        this.stringSet = stringSet;
    }

    @Override
    public String toString() {
        return "StringSetRequest{" +
                "stringSet='" + stringSet + '\'' +
                '}';
    }
}
