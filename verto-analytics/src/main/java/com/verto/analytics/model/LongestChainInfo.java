package com.verto.analytics.model;

import com.verto.analytics.util.DefaultResponse;

/**
 * @author abhishekrai
 * @since 24/06/2017
 */
public class LongestChainInfo extends DefaultResponse {
    private String longestChain;

    public String getLongestChain() {
        return longestChain;
    }

    public void setLongestChain(String longestChain) {
        this.longestChain = longestChain;
    }

    @Override
    public String toString() {
        return "LongestChainInfo{" +
                "longestChain='" + longestChain + '\'' +
                '}';
    }
}
