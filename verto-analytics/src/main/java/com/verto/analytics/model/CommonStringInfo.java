package com.verto.analytics.model;

import com.verto.analytics.util.DefaultResponse;

import java.util.Set;

/**
 * @author abhishekrai
 * @since 21/06/2017
 */
public class CommonStringInfo extends DefaultResponse {

    private Set<String> commonStringSet;

    public Set<String> getCommonStringSet() {
        return commonStringSet;
    }

    public void setCommonStringSet(Set<String> commonStringSet) {
        this.commonStringSet = commonStringSet;
    }
}
