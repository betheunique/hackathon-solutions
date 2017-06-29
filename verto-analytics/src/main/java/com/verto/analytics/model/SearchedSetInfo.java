package com.verto.analytics.model;

import com.verto.analytics.util.DefaultResponse;

import java.util.List;

/**
 * @author abhishekrai
 * @since 21/06/2017
 */
public class SearchedSetInfo extends DefaultResponse {

    private List<String> searchedStringSetList;

    public List<String> getSearchedStringSetList() {
        return searchedStringSetList;
    }

    public void setSearchedStringSetList(List<String> searchedStringSetList) {
        this.searchedStringSetList = searchedStringSetList;
    }

    @Override
    public String toString() {
        return "SearchedSetInfo{" +
                "searchedStringSetList=" + searchedStringSetList +
                '}';
    }
}
