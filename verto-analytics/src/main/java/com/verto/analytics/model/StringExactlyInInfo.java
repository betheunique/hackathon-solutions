package com.verto.analytics.model;

import com.verto.analytics.util.DefaultResponse;

import java.util.Set;

/**
 * @author abhishekrai
 * @since 21/06/2017
 */
public class StringExactlyInInfo extends DefaultResponse {

    private Set<String> stringExactlyInSpecificSet;

    public Set<String> getStringExactlyInSpecificSet() {
        return stringExactlyInSpecificSet;
    }

    public void setStringExactlyInSpecificSet(Set<String> stringExactlyInSpecificSet) {
        this.stringExactlyInSpecificSet = stringExactlyInSpecificSet;
    }

    @Override
    public String toString() {
        return "StringExactlyInInfo{" +
                "stringExactlyInSpecificSet=" + stringExactlyInSpecificSet +
                '}';
    }
}
