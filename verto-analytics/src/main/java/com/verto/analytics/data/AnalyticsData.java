package com.verto.analytics.data;

import com.verto.analytics.model.DataInfo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author abhishekrai
 * @since 21/06/2017
 */
@Component
public class AnalyticsData {

    /**
     * Main Storage for All the String Set and its Details {@code DataInfo}.
     */
    private LinkedHashMap<String, DataInfo> storage = new LinkedHashMap<>();

    /**
     * Each Word hashCode vs its occurrence.
     */
    private Map<Integer, Integer> stringHashCodeVsOccurrence = new HashMap<>();

    /**
     * String length vs no of string with same length.
     */

    private TreeMap<Integer, TreeSet<String>> longestStringMap = new TreeMap<>();

    /**
     * Word occurrence vs its String Set value. {@code TreeSet} maintain ordering.
     * return all similar occurred word in alphabetical order.
     */
    private TreeMap<Integer, TreeSet<String>> occurrenceVsStringSet = new TreeMap<>();

    public LinkedHashMap<String, DataInfo> getStorage() {
        return storage;
    }

    public void setStorage(LinkedHashMap<String, DataInfo> storage) {
        this.storage = storage;
    }

    public TreeMap<Integer, TreeSet<String>> getLongestStringMap() {
        return longestStringMap;
    }

    public void setLongestStringMap(TreeMap<Integer, TreeSet<String>> longestStringMap) {
        this.longestStringMap = longestStringMap;
    }

    public Map<Integer, Integer> getStringHashCodeVsOccurrence() {
        return stringHashCodeVsOccurrence;
    }

    public void setStringHashCodeVsOccurrence(Map<Integer, Integer> stringHashCodeVsOccurrence) {
        this.stringHashCodeVsOccurrence = stringHashCodeVsOccurrence;
    }

    public TreeMap<Integer, TreeSet<String>> getOccurrenceVsStringSet() {
        return occurrenceVsStringSet;
    }

    public void setOccurrenceVsStringSet(TreeMap<Integer, TreeSet<String>> occurrenceVsStringSet) {
        this.occurrenceVsStringSet = occurrenceVsStringSet;
    }
}
