package com.adcash.trading.dao;

import com.adcash.trading.model.TradingCompanies;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

/**
 * @author abhishekrai
 * @since 20/06/2017
 */
@Repository
public interface TradingRepository extends CrudRepository<TradingCompanies, Long>{

    @Override
    List<TradingCompanies> findAll();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("from TradingCompanies")
    List<TradingCompanies> selectAll();

    @Override
    @SuppressWarnings("unchecked")
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    TradingCompanies save(TradingCompanies tradingCompanies);

}
