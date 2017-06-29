package com.verto.analytics.controller;

import com.verto.analytics.dto.StringSetRequest;
import com.verto.analytics.service.StringSetService;
import com.verto.analytics.util.DefaultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author abhishekrai
 * @since 21/06/2017
 */
@RestController
public class StringSetController {

    private final Logger logger = LoggerFactory.getLogger(StringSetController.class);

    private StringSetService stringSetService;

    @Autowired
    public void setStringSetService(StringSetService stringSetService) {
        this.stringSetService = stringSetService;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public DefaultResponse upload(@RequestBody StringSetRequest stringSetRequest) {
        logger.info("Request coming as :" + stringSetRequest);
        return stringSetService.addData(stringSetRequest);
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET, produces = "application/json")
    public DefaultResponse statistics() {
        return stringSetService.getDataDetails();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public DefaultResponse search(@RequestParam("searchString") final String searchString) {
        logger.info("Search string coming as :" + searchString);
        return stringSetService.getSearchResult(searchString);
    }

    @RequestMapping(value = "/most_common", method = RequestMethod.GET, produces = "application/json")
    public DefaultResponse mostCommon() {
        return stringSetService.getCommonString();
    }

    @RequestMapping(value = "/longest", method = RequestMethod.GET, produces = "application/json")
    public DefaultResponse longest() {
        DefaultResponse longestStringInfo = stringSetService.getLongestString();
        logger.info("Result coming as :" + longestStringInfo);
        return longestStringInfo;
    }

    @RequestMapping(value = "/exactly_in", method = RequestMethod.GET, produces = "application/json")
    public DefaultResponse exactlyIn(@RequestParam("specificNumberOfSet") final int specificNumberOfSet) {
        logger.info("Search string coming as :" + specificNumberOfSet);
        return stringSetService.getExactlyInString(specificNumberOfSet);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = "application/json")
    public DefaultResponse delete(@RequestParam("stringToBeDeleted") final String stringToBeDeleted) {
        logger.info("Search string coming as :" + stringToBeDeleted);
        return stringSetService.remove(stringToBeDeleted);
    }

    @RequestMapping(value = "/create_intersection", method = RequestMethod.GET, produces = "application/json")
    public DefaultResponse createIntersection() {
        return stringSetService.createIntersection();
    }

    @RequestMapping(value = "/longest_chain", method = RequestMethod.GET, produces = "application/json")
    public DefaultResponse longestChain() {
        return stringSetService.longestStringChain();
    }

}
