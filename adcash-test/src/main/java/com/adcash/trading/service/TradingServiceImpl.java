package com.adcash.trading.service;

import com.adcash.trading.cache.TradingCompaniesCache;
import com.adcash.trading.dao.TradingRepository;
import com.adcash.trading.model.TradingCompanies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author abhishekrai
 * @since 20/06/2017
 */
@Service
@Transactional
public class TradingServiceImpl implements TradingService {

    private static Logger logger = LoggerFactory.getLogger(TradingServiceImpl.class);

    @Autowired
    private TradingRepository tradingRepository;

    @Autowired
    private TradingCompaniesCache tradingCompaniesCache;


    public String getTradingCompany(String countryCode, String category, int baseBid) {

        List<TradingCompanies> tcList = new ArrayList<>(tradingCompaniesCache.getTradingCompaniesList());
        StringBuilder sb = new StringBuilder();
        for (Iterator<TradingCompanies> iterator = tcList.iterator(); iterator.hasNext();){
            TradingCompanies tc = iterator.next();
            if(!tc.getCountries().contains(countryCode) || !tc.getCategory().contains(category)) {
                sb.append("{" );
                sb.append(tc.getCompanyId());
                sb.append(", failed},");
                iterator.remove();
            }
            else {
                sb.append("{" );
                sb.append(tc.getCompanyId());
                sb.append(", success},");
            }
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        logger.info("BaseTargeting :" + sb.toString());
        if(CollectionUtils.isEmpty(tcList)) {
            return "No Companies Passed from Targeting";
        }
        return checkBudgetAndUpdate(baseBid);
    }

    /**
     * method checks budget, max bid and then update accordingly.
     * Every company details are fetched and logs are maintained.
     * @param bid
     * @return
     *
     */
    @Transactional
    private String checkBudgetAndUpdate(int bid) {
        List<TradingCompanies> tradingCompaniesList = tradingRepository.selectAll();
        List<TradingCompanies> budgetCheckCompaniesList = tradingCompaniesList;
        StringBuilder sb = new StringBuilder();
        try {
            // Budget check for all the company
            for (Iterator<TradingCompanies> iterator = budgetCheckCompaniesList.iterator(); iterator.hasNext();) {
                TradingCompanies tc = iterator.next();
                if (!(Double.valueOf(tc.getBudget().replaceAll("[^0-9.]", "")) * 100 > bid)) {
                    sb.append("{" );
                    sb.append(tc.getCompanyId());
                    sb.append(", failed},");
                    iterator.remove();
                } else {
                    sb.append("{");
                    sb.append(tc.getCompanyId());
                    sb.append(", success},");
                }
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            logger.info("BudgetCheck :" + sb.toString());

            if(CollectionUtils.isEmpty(budgetCheckCompaniesList)) {
                return "No Companies Passed from Budget";
            }

            StringBuilder sbBid = new StringBuilder();
            List<TradingCompanies> bidCheckComapniesList = tradingCompaniesList;
            // Max Bid check for all the company
            // TODO if log for all the companies are not imp than list can be propagated.
            for (Iterator<TradingCompanies> tcIterator = bidCheckComapniesList.iterator(); tcIterator.hasNext();) {
                TradingCompanies tc = tcIterator.next();
                if (!(Double.valueOf(tc.getBid().replaceAll("[^0-9.]", "")) > bid)) {
                    sbBid.append("{");
                    sbBid.append(tc.getCompanyId());
                    sbBid.append(", failed},");
                    tcIterator.remove();
                } else {
                    sbBid.append("{");
                    sbBid.append(tc.getCompanyId());
                    sbBid.append(", success},");
                }
            }
            sbBid.deleteCharAt(sbBid.lastIndexOf(","));
            logger.info("BaseBid: :" + sbBid.toString());

            if(CollectionUtils.isEmpty(bidCheckComapniesList)) {
                return "No Companies Passed from BaseBid check";
            }

            TradingCompanies maxBidTradingCompany = Collections.max(bidCheckComapniesList);

            logger.info("Winner = " + maxBidTradingCompany.getCompanyId());

            String newBudget = ((Double.valueOf(maxBidTradingCompany.getBudget().replaceAll("[^0-9.]", "")) * 100 - bid) / 100) + "$";

            maxBidTradingCompany.setBudget(newBudget);

            tradingRepository.save(maxBidTradingCompany);
            return maxBidTradingCompany.getCompanyId();
        } catch (Exception e) {
            logger.info("Exception occurred :" + e);
            return e.getMessage();
        }

    }
}
