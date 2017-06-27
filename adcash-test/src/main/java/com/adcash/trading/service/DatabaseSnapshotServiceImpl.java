package com.adcash.trading.service;

import com.adcash.trading.dao.TradingRepository;
import com.adcash.trading.model.TradingCompanies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author abhishekrai
 * @since 20/06/2017
 */
@Service
public class DatabaseSnapshotServiceImpl implements DatabaseSnapshotService {

    @Autowired
    private TradingRepository tradingRepository;


    public List<TradingCompanies> getDBSnapshot() {
        return tradingRepository.findAll();
    }

}
