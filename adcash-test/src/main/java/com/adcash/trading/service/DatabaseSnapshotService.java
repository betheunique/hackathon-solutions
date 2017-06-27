package com.adcash.trading.service;

import com.adcash.trading.model.TradingCompanies;

import java.util.List;

/**
 * @author abhishekrai
 * @since 20/06/2017
 */
public interface DatabaseSnapshotService {

    List<TradingCompanies> getDBSnapshot();
}
