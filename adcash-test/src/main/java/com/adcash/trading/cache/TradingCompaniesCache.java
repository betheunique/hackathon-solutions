package com.adcash.trading.cache;

import com.adcash.trading.dao.TradingRepository;
import com.adcash.trading.model.TradingCompanies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author abhishekrai
 * @since 20/06/2017
 * initially caches all companies details.
 */
@Component
public class TradingCompaniesCache {

    private static Logger logger = LoggerFactory.getLogger(TradingCompaniesCache.class);

    @Autowired
    private TradingRepository tradingRepository;

    // Maintain cache for all companies.
    private List<TradingCompanies> tradingCompaniesList;


    @PostConstruct
    public void getAllCompaniesDetails() {
        logger.info("caching all the companies details");
        tradingCompaniesList = tradingRepository.findAll();
    }

    public List<TradingCompanies> getTradingCompaniesList() {
        return tradingCompaniesList;
    }

}
