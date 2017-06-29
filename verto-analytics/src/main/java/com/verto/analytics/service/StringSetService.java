package com.verto.analytics.service;

import com.verto.analytics.dto.StringSetRequest;
import com.verto.analytics.util.DefaultResponse;

/**
 * @author abhishekrai
 * @since 21/06/2017
 */
public interface StringSetService {
    DefaultResponse addData(StringSetRequest stringSetRequest);
    DefaultResponse getDataDetails();
    DefaultResponse getSearchResult(String searchString);
    DefaultResponse getCommonString();
    DefaultResponse getLongestString();
    DefaultResponse getExactlyInString(int specificNumberOfSet);
    DefaultResponse remove(String stringToBeDeleted);
    DefaultResponse longestStringChain();
    DefaultResponse createIntersection();
}
