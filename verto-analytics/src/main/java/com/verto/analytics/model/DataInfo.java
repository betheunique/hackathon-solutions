package com.verto.analytics.model;

import java.util.Set;

/**
 * @author abhishekrai
 * @since 21/06/2017
 */
public class DataInfo {

    private String id;
    private Set<String> stringSet;
    private String longestString;
    private Integer maxStringLength;
    private Integer minStringLength;
    private Integer avgStringLength;
    private Integer medianStringLength;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getStringSet() {
        return stringSet;
    }

    public void setStringSet(Set<String> stringSet) {
        this.stringSet = stringSet;
    }

    public Integer getMaxStringLength() {
        return maxStringLength;
    }

    public void setMaxStringLength(Integer maxStringLength) {
        this.maxStringLength = maxStringLength;
    }

    public Integer getMinStringLength() {
        return minStringLength;
    }

    public void setMinStringLength(Integer minStringLength) {
        this.minStringLength = minStringLength;
    }

    public Integer getAvgStringLength() {
        return avgStringLength;
    }

    public void setAvgStringLength(Integer avgStringLength) {
        this.avgStringLength = avgStringLength;
    }

    public Integer getMedianStringLength() {
        return medianStringLength;
    }

    public void setMedianStringLength(Integer medianStringLength) {
        this.medianStringLength = medianStringLength;
    }

    public String getLongestString() {
        return longestString;
    }

    public void setLongestString(String longestString) {
        this.longestString = longestString;
    }

    @Override
    public String toString() {
        return "DataInfo{" +
                "id='" + id + '\'' +
                ", stringSet=" + stringSet +
                ", longestString='" + longestString + '\'' +
                ", maxStringLength=" + maxStringLength +
                ", minStringLength=" + minStringLength +
                ", avgStringLength=" + avgStringLength +
                ", medianStringLength=" + medianStringLength +
                '}';
    }
}
