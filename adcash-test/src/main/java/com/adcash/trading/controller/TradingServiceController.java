package com.adcash.trading.controller;

import com.adcash.trading.service.TradingService;
import com.adcash.trading.util.DefaultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author abhishekrai
 * @since 20/06/2017
 */
@RestController
public class TradingServiceController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TradingService tradingServiceImpl;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultResponse getCompanyId(@RequestParam("countryCode") final String countryCode,
                                        @RequestParam("category") final String category,
                                        @RequestParam("baseBid") final int baseBid) {
        DefaultResponse defaultResponse = new DefaultResponse();
        long startTime = System.currentTimeMillis();
        logger.info("Request coming as countryCode = " + countryCode + "\t category = " + category + "\t baseBid = " + baseBid);
        defaultResponse.setMessage(tradingServiceImpl.getTradingCompany(countryCode, category, baseBid));
        logger.info("Time Taken to execute getCompanyId API :" + (System.currentTimeMillis() - startTime));
        return defaultResponse;
    }
}
