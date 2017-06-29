package com.verto.analytics.service;

import com.verto.analytics.constants.Constants;
import com.verto.analytics.data.AnalyticsData;
import com.verto.analytics.dto.StringSetRequest;
import com.verto.analytics.model.CommonStringInfo;
import com.verto.analytics.model.DataInfo;
import com.verto.analytics.model.LongestChainInfo;
import com.verto.analytics.model.LongestStringInfo;
import com.verto.analytics.model.SearchedSetInfo;
import com.verto.analytics.model.StatisticsInfo;
import com.verto.analytics.model.StringExactlyInInfo;
import com.verto.analytics.util.CalculateAllPaths;
import com.verto.analytics.util.DefaultResponse;
import com.verto.analytics.util.DiGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author abhishekrai
 * @since 21/06/2017
 */
@Service
public class StringSetServiceImpl implements StringSetService{

    private final Logger logger = LoggerFactory.getLogger(StringSetServiceImpl.class);


    private AnalyticsData analyticsData;

    @Autowired
    public void setAnalyticsData(AnalyticsData analyticsData) {
        this.analyticsData = analyticsData;
    }

    /**
     * Method uploads new string set into our storage.
     * @param stringSetRequest Its is string set object having string set to be uploaded to storage.
     * @return {@code DefaultResponse}
     * Method calculate all the initial information about the stringSet, Max string length, minimum string length, median , avg.
     */
    public DefaultResponse addData(StringSetRequest stringSetRequest) {

        logger.info("Request coming as :" + stringSetRequest);
        DefaultResponse defaultResponse = new DefaultResponse();
        DataInfo dataInfo = new DataInfo();
//        TreeMap<Integer, String> lengthVsString = new TreeMap<>();
        List<Integer> stringLengths = new ArrayList<>();

        String stringSet;
        if (Optional.ofNullable(stringSetRequest.getStringSet()).isPresent()) {
            stringSet = stringSetRequest.getStringSet();
        } else {
            defaultResponse.setStatus(Constants.FAILURE);
            defaultResponse.setMessage("String set is empty");
            return defaultResponse;
        }


        Set<String> stringArray = new TreeSet<>(Arrays.asList(stringSet.split(" ")));
        logger.info("string set :" + stringArray);

        if(Arrays.asList(stringSet.split(" ")).size() != stringArray.size()) {
            defaultResponse.setStatus(Constants.FAILURE);
            defaultResponse.setMessage("String set contains duplicate");
            return defaultResponse;
        }

        if(analyticsData.getStorage().containsKey(stringSet)) {
            defaultResponse.setStatus(Constants.FAILURE);
            defaultResponse.setMessage("Storage contains the specified set");
            return defaultResponse;
        }

        // Total length of all the string length
        int totalLength = stringArray.stream().mapToInt(String::length).sum();

        dataInfo.setId(stringSet);
        dataInfo.setStringSet(stringArray);
        stringArray.forEach((individualString) -> {
//            lengthVsString.put(individualString.length(), individualString);
            stringLengths.add(individualString.length());
            addLongestString(individualString);
            addCommonString(individualString);
        });

        dataInfo.setMaxStringLength(stringLengths.get(stringLengths.size() -1));
        dataInfo.setMinStringLength(stringLengths.get(0));
        dataInfo.setAvgStringLength(totalLength / stringLengths.size());

        logger.info("String length size :" + (stringLengths.size() % 2));
        if(stringLengths.size() % 2 == 0) {
            int median;
            if(stringLengths.size() == 2)
                median = (stringLengths.get(0) + stringLengths.get(1) ) / 2;
            else
                median = (stringLengths.get(stringLengths.size() / 2) + stringLengths.get((stringLengths.size() / 2) + 1)) / 2;
            dataInfo.setMedianStringLength(median);
        } else {
            dataInfo.setMedianStringLength(stringLengths.get(stringLengths.size() / 2));
        }

        analyticsData.getStorage().put(stringSet, dataInfo);
        logger.info("Data info :" + analyticsData.getStorage().get(stringSet));
        defaultResponse.setStatus(Constants.SUCCESS);
        defaultResponse.setMessage("Successfully uploaded string set to server");
        return defaultResponse;
    }

    /**
     * All the Details about All stringSet stored with max stringlength, min stringlength, avg, median in each set.
     * @return DefaultResponse
     */
    public DefaultResponse getDataDetails() {
        List<DataInfo> dataInfoList = new LinkedList<>();

        if(!analyticsData.getStorage().keySet().isEmpty()) {
            StatisticsInfo statisticsInfo = new StatisticsInfo();
            analyticsData.getStorage().keySet().forEach( dataInfoId ->
                dataInfoList.add(analyticsData.getStorage().get(dataInfoId)));
            statisticsInfo.setDataStatistics(dataInfoList);
            statisticsInfo.setStatus(Constants.SUCCESS);
            statisticsInfo.setMessage("Success");
            return statisticsInfo;
        } else {
            DefaultResponse defaultResponse = new DefaultResponse();
            defaultResponse.setStatus(Constants.FAILURE);
            defaultResponse.setMessage("No record found");
            return defaultResponse;
        }
    }


    /**
     * Return list of string set having incoming string.
     * @param searchString string to be searched.
     * @return {@code SearchedSetInfo}
     */
    public DefaultResponse getSearchResult(final String searchString) {
        List<String> searchedStringSet = new ArrayList<>();
        if(!analyticsData.getStorage().keySet().isEmpty()) {
            SearchedSetInfo searchedSetInfo =  new SearchedSetInfo();
            analyticsData.getStorage().keySet().forEach((dataInfoId) -> {
                if (dataInfoId.contains(searchString))
                    searchedStringSet.add(dataInfoId);
            });
            searchedSetInfo.setSearchedStringSetList(searchedStringSet);
            searchedSetInfo.setStatus(Constants.SUCCESS);
            searchedSetInfo.setMessage("Success");
            return searchedSetInfo;
        } else {
            DefaultResponse defaultResponse = new DefaultResponse();
            defaultResponse.setStatus(Constants.FAILURE);
            defaultResponse.setMessage("No record found");
            return defaultResponse;

        }
    }

    /**
     * Common string among the storage.
     * @return {@code CommonStringInfo}
     */
    public DefaultResponse getCommonString() {

        if(Optional.ofNullable(analyticsData.getOccurrenceVsStringSet().lastEntry()).isPresent()) {
            CommonStringInfo commonStringInfo = new CommonStringInfo();
            commonStringInfo.setCommonStringSet(analyticsData.getOccurrenceVsStringSet().lastEntry().getValue());
            commonStringInfo.setStatus(Constants.SUCCESS);
            commonStringInfo.setMessage("Success");
            return commonStringInfo;
        } else {
            DefaultResponse defaultResponse = new DefaultResponse();
            defaultResponse.setStatus(Constants.FAILURE);
            defaultResponse.setMessage("No record found");
            return defaultResponse;
        }
    }

    /**
     * method returns longest String or String list in storage
     * @return {@code LongestStringInfo}
     */
    public DefaultResponse getLongestString() {

        if(Optional.ofNullable(analyticsData.getLongestStringMap().lastEntry()).isPresent()) {
            LongestStringInfo longestStringInfo = new LongestStringInfo();
            longestStringInfo.setLongestStringSet(analyticsData.getLongestStringMap().lastEntry().getValue());
            longestStringInfo.setStatus(Constants.SUCCESS);
            longestStringInfo.setMessage("Success");
            return longestStringInfo;
        }
        else {
            DefaultResponse defaultResponse = new DefaultResponse();
            defaultResponse.setStatus(Constants.FAILURE);
            defaultResponse.setMessage("No record Found");
            return defaultResponse;
        }
    }

    /**
     * Method return common string occurring in number of set
     * @param specificNumberOfSet no of set string to be occurred in.
     * @return DefaultResponse
     */
    public DefaultResponse getExactlyInString(int specificNumberOfSet) {

        if(Optional.ofNullable(analyticsData.getOccurrenceVsStringSet().get(specificNumberOfSet)).isPresent()) {
            StringExactlyInInfo stringExactlyInInfo = new StringExactlyInInfo();
            stringExactlyInInfo.setStringExactlyInSpecificSet(analyticsData.getOccurrenceVsStringSet().get(specificNumberOfSet));
            stringExactlyInInfo.setStatus(Constants.SUCCESS);
            stringExactlyInInfo.setMessage("Success");
            return stringExactlyInInfo;
        } else {
            DefaultResponse defaultResponse = new DefaultResponse();
            defaultResponse.setStatus(Constants.FAILURE);
            defaultResponse.setMessage("No record found with specific number");
            return defaultResponse;
        }
    }

    /**
     *
     * @param stringToBeDeleted stringSet to be removed from storage
     * @return DefaultResponse
     */
    public DefaultResponse remove(String stringToBeDeleted) {

        DefaultResponse defaultResponse = new DefaultResponse();
        if(!analyticsData.getStorage().containsKey(stringToBeDeleted)) {
            defaultResponse.setStatus(Constants.FAILURE);
            defaultResponse.setMessage("String doesn't exists in storage");
            return defaultResponse;
        }

        analyticsData.getStorage().get(stringToBeDeleted).getStringSet().forEach((individualString) -> {
            if(analyticsData.getStringHashCodeVsOccurrence().get(individualString.hashCode()) == 1) {
                analyticsData.getLongestStringMap().get(individualString.length()).remove(individualString);
                analyticsData.getOccurrenceVsStringSet().get(1).remove(individualString);
                analyticsData.getStringHashCodeVsOccurrence().remove(individualString.hashCode());
            } else {
                int newOccurrenceValue = (analyticsData.getStringHashCodeVsOccurrence().get(individualString.hashCode())) - 1;
                analyticsData.getOccurrenceVsStringSet().get(analyticsData.getStringHashCodeVsOccurrence()
                        .get(individualString.hashCode())).remove(individualString);
                if(Optional.ofNullable(analyticsData.getOccurrenceVsStringSet().get(newOccurrenceValue)).isPresent())
                    analyticsData.getOccurrenceVsStringSet().get(newOccurrenceValue).add(individualString);
                else {
                    TreeSet<String> newSet = new TreeSet<>();
                    newSet.add(individualString);
                    analyticsData.getOccurrenceVsStringSet().put(newOccurrenceValue, newSet);
                }
                analyticsData.getStringHashCodeVsOccurrence().put(individualString.hashCode(), newOccurrenceValue);
            }
        });
        analyticsData.getStorage().remove(stringToBeDeleted);
        if(Optional.ofNullable(analyticsData.getStorage().get(stringToBeDeleted)).isPresent()) {
            defaultResponse.setStatus(Constants.FAILURE);
            defaultResponse.setMessage("Something went wrong");
            return defaultResponse;
        }
        else {
            defaultResponse.setStatus(Constants.SUCCESS);
            defaultResponse.setMessage(stringToBeDeleted + "deleted successfully");
            return defaultResponse;
        }
    }

    /**
     *
     * @param individualString add longestString for each StringSet into storage
     */
    private void addLongestString(String individualString) {

        if(Optional.ofNullable(analyticsData.getLongestStringMap().get(individualString.length())).isPresent()) {
            analyticsData.getLongestStringMap().get(individualString.length()).add(individualString);
        } else {
            logger.info("Observing null value in longest string for Set");
            TreeSet<String> resultSet =  new TreeSet<>();
            resultSet.add(individualString);
            analyticsData.getLongestStringMap().put(individualString.length(), resultSet);
        }
    }

    private void addCommonString(String individualString) {
        logger.info("Checking hash code for individual string :" + individualString.hashCode());
        logger.info("Checking individual string :" + individualString);

        if(Optional.ofNullable(analyticsData.getStringHashCodeVsOccurrence().get(individualString.hashCode())).isPresent()) {
            int newOccurrenceValue = analyticsData.getStringHashCodeVsOccurrence().get(individualString.hashCode()) + 1;
            logger.info("new occurrence :" + newOccurrenceValue);
            if(Optional.ofNullable(analyticsData.getOccurrenceVsStringSet().get(newOccurrenceValue)).isPresent()) {
                analyticsData.getOccurrenceVsStringSet().get(newOccurrenceValue).add(individualString);
                analyticsData.getOccurrenceVsStringSet().get(analyticsData.getStringHashCodeVsOccurrence()
                             .get(individualString.hashCode())).remove(individualString);
            } else {
                logger.info("observing null value in common string Set");
                TreeSet<String> resultSet =  new TreeSet<>();
                resultSet.add(individualString);
                analyticsData.getOccurrenceVsStringSet().put(newOccurrenceValue, resultSet);
                analyticsData.getOccurrenceVsStringSet().get(analyticsData.getStringHashCodeVsOccurrence()
                             .get(individualString.hashCode())).remove(individualString);
            }
            analyticsData.getStringHashCodeVsOccurrence().put(individualString.hashCode(), newOccurrenceValue);
        } else {
            logger.info("Observing null value for Integer");
            analyticsData.getStringHashCodeVsOccurrence().put(individualString.hashCode(), 1);
            if(Optional.ofNullable(analyticsData.getOccurrenceVsStringSet().get(1)).isPresent()) {
                analyticsData.getOccurrenceVsStringSet().get(1).add(individualString);
            } else {
                logger.info("observing null value in common string Set");
                TreeSet<String> resultSet =  new TreeSet<>();
                resultSet.add(individualString);
                analyticsData.getOccurrenceVsStringSet().put(1, resultSet);
            }
        }
    }

    /**
     * method responsible for calculating intersection for last two stringSet and upload to storage
     * @return {@code DefaultResponse}
     */
    public DefaultResponse createIntersection() {

        LinkedList<String> reverseStorageKey =  new LinkedList<>(analyticsData.getStorage().keySet());
        DefaultResponse defaultResponse = new DefaultResponse();
        Set<String> firstSet;
        Set<String> secondSet;
        Collections.reverse(reverseStorageKey);
        if(reverseStorageKey.size() > 0)
             firstSet = new HashSet<>(analyticsData.getStorage().get(reverseStorageKey.get(0)).getStringSet());
        else {
            logger.info("No stringSet in storage");
            defaultResponse.setStatus(Constants.FAILURE);
            defaultResponse.setMessage("No String set in storage");
            return defaultResponse;
        }
        if(reverseStorageKey.size() > 1)
            secondSet = new HashSet<>(analyticsData.getStorage().get(reverseStorageKey.get(1)).getStringSet());
        else {
            logger.info("Only one string set in storage");
            defaultResponse.setStatus(Constants.FAILURE);
            defaultResponse.setMessage("Only one string set in storage");
            return defaultResponse;
        }

        firstSet.retainAll(secondSet);
        if(!firstSet.isEmpty()) {
            StringSetRequest stringSetRequest = new StringSetRequest();
            stringSetRequest.setStringSet(firstSet.stream().collect(Collectors.joining(" ")));
            defaultResponse = addData(stringSetRequest);
        }
        return defaultResponse;
    }

    /**
     * Calculate longest string chain form the storage.
     *
     * It calculate {@code DiGraph} for individual stringSet and calculate {@code CalculateAllPaths} from graph add it to storage.
     *
     * Based on start and Ending character of longest chain among string set it combined two longest chain.
     *
     * Time complexity for the algorithm O(n*n)
     *
     * Space complexity O(120*n) n number of string list
     *
     * @return {@code LongestChainInfo}
     */
    public DefaultResponse longestStringChain() {

        LongestChainInfo longestChainInfo = new LongestChainInfo();
        DefaultResponse defaultResponse = new DefaultResponse();

        Map<Character, Integer> endWithCharVsStringLength = new HashMap<>(); // string ending with char vs string length
        Map<Character, Integer> startWithCharVsStringLength = new HashMap<>(); // string start with char vs string length
        Map<Character, LinkedList<String>> startWithCharVsStringList = new HashMap<>(); // start with char vs list of string
        Map<Character, LinkedList<String>> endWithCharVsStringList = new HashMap<>(); // end with char vs list of string
        TreeMap<Integer, Character> endStringLengthVsCharacter =  new TreeMap<>(); // string length vs char

        analyticsData.getStorage().values().forEach((individualDataInfo) -> {
            DiGraph diGraph = new DiGraph ();
            createDiGraph(diGraph, individualDataInfo);
            addAllDiGraphPath(diGraph, individualDataInfo,
                    endWithCharVsStringLength,
                    startWithCharVsStringLength,
                    startWithCharVsStringList,
                    endWithCharVsStringList,
                    endStringLengthVsCharacter);
        });

        int longestStringSetLength;
        Character longestEndCharacter;
         if(endStringLengthVsCharacter.size() > 0) {
             longestStringSetLength = endStringLengthVsCharacter.lastKey();
             longestEndCharacter = endStringLengthVsCharacter.lastEntry().getValue();
         } else {
             defaultResponse.setStatus(Constants.FAILURE);
             defaultResponse.setMessage("Storage is Empty or no path found only single word from any string set can present path");
             return defaultResponse;
         }

        for(Character character : endWithCharVsStringLength.keySet()) {
            if(startWithCharVsStringLength.containsKey(character)) {
                int combinedLength = endWithCharVsStringLength.get(character) + startWithCharVsStringLength.get(character);
                if(combinedLength >= longestStringSetLength + 1){
                    longestStringSetLength = combinedLength;
                    longestEndCharacter = character;
                }
            }
        }

        if(longestStringSetLength > endStringLengthVsCharacter.lastKey()) {
            longestChainInfo.setLongestChain(endWithCharVsStringList.get(longestEndCharacter).get(0) + "-" + startWithCharVsStringList.get(longestEndCharacter).get(0));
            longestChainInfo.setStatus(Constants.SUCCESS);
            longestChainInfo.setMessage("Combined Two String Set");
        }
        else {
            longestChainInfo.setLongestChain(endWithCharVsStringList.get(endStringLengthVsCharacter.lastEntry().getValue()).get(0));
            longestChainInfo.setStatus(Constants.SUCCESS);
            longestChainInfo.setMessage("Only single Set String is Largest can be combined with any of word from all the string set");
        }
        return longestChainInfo;
    }

    private void createDiGraph(DiGraph diGraph, DataInfo individualDataInfo) {
        Map<Character, HashSet<String>> localEndWithChar = new HashMap<>();
        individualDataInfo.getStringSet().forEach((individualString) -> {
            diGraph.addVertex(individualString);
            if (Optional.ofNullable(localEndWithChar.get(individualString.charAt(0)))
                    .isPresent()) {
                localEndWithChar.get(individualString.charAt(0)).add(individualString);
            } else {
                HashSet<String> stringHashSet = new HashSet<>();
                stringHashSet.add(individualString);
                localEndWithChar.put(individualString.charAt(0), stringHashSet);
            }
        });

        individualDataInfo.getStringSet().forEach((individualString) ->
                diGraph.addEdge(individualString, localEndWithChar.get(individualString.charAt(individualString.length() -1))));
    }

    private void addAllDiGraphPath(DiGraph diGraph, DataInfo individualDataInfo,
                                   Map<Character, Integer> endWithCharVsStringLength,
                                   Map<Character, Integer> startWithCharVsStringLength,
                                   Map<Character, LinkedList<String>> startWithCharVsStringList,
                                   Map<Character, LinkedList<String>> endWithCharVsStringList,
                                   TreeMap<Integer, Character> lengthVsCharacter) {

        CalculateAllPaths calculateAllPaths = new CalculateAllPaths(diGraph);

        String[] wordArray = individualDataInfo.getStringSet().stream().toArray(String[]::new);

        for(int i = 0 ; i < wordArray.length; i++) {
            for(int j = 0; j < wordArray.length; j++) {
                calculateAllPaths.addAllPaths(wordArray[i], wordArray[j],
                        endWithCharVsStringLength,
                        startWithCharVsStringLength,
                        startWithCharVsStringList,
                        endWithCharVsStringList,
                        lengthVsCharacter);
            }
        }

    }

}
