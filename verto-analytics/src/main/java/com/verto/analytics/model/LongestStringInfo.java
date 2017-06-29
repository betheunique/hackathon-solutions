package com.verto.analytics.model;

import com.verto.analytics.util.DefaultResponse;

import java.util.Set;

/**
 * @author abhishekrai
 * @since 21/06/2017
 */
public class LongestStringInfo extends DefaultResponse {

    private Set<String> longestStringSet;

    public Set<String> getLongestStringSet() {
        return longestStringSet;
    }

    public void setLongestStringSet(Set<String> longestStringSet) {
        this.longestStringSet = longestStringSet;
    }

    @Override
    public String toString() {
        return "LongestStringInfo{" +
                "longestStringSet=" + longestStringSet +
                '}';
    }
}
