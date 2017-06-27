package com.adcash.trading.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author abhishekrai
 * @since 20/06/2017
 */
@Entity
@Table(name = "t_trading_companies")
public class TradingCompanies implements Serializable, Comparable<TradingCompanies> {

    /**
     *
     */
    private static final long serialVersionUID = -5069346764754011130L;

    @Id
    @Column(name = "CompanyID")
    private String companyId;

    @Column(name = "Countries")
    private String countries;

    @Column(name = "Budget")
    private String budget;

    @Column(name = "Bid")
    private String bid;

    @Column(name = "Category")
    private String category;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int compareTo(TradingCompanies tc) {
        try {
            return Double.valueOf(this.bid.replaceAll("[^0-9]", "")).compareTo(Double.valueOf(tc.getBid().replaceAll("[^0-9]", "")));
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    @Override
    public String toString() {
        return "TradingCompanies{" +
                "companyId='" + companyId + '\'' +
                ", countries='" + countries + '\'' +
                ", budget='" + budget + '\'' +
                ", bid='" + bid + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
